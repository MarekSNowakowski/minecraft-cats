package com.keroen.meowdifications.block;

import com.keroen.meowdifications.block.state.BowlContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PetBowlBlock extends HorizontalDirectionalBlock {
	public static final EnumProperty<BowlContent> CONTENT = EnumProperty.create("content", BowlContent.class);
	private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 3.5, 12.0);

	public PetBowlBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(CONTENT, BowlContent.EMPTY)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, CONTENT);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		BowlContent current = state.getValue(CONTENT);

		if (current == BowlContent.EMPTY) {
			// Water Bucket
			if (stack.is(Items.WATER_BUCKET)) {
				if (!player.getAbilities().instabuild) {
					player.setItemInHand(hand, new ItemStack(Items.BUCKET));
				}
				level.setBlock(pos, state.setValue(CONTENT, BowlContent.WATER), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
			// Water Bottle
			if (stack.is(Items.POTION) && PotionContents.createItemStack(Items.POTION, Potions.WATER).getItem() == stack.getItem()) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
					if (stack.isEmpty()) {
						player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
					} else {
						player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
					}
				}
				level.setBlock(pos, state.setValue(CONTENT, BowlContent.WATER), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
			// Milk Bucket
			if (stack.is(Items.MILK_BUCKET)) {
				if (!player.getAbilities().instabuild) {
					player.setItemInHand(hand, new ItemStack(Items.BUCKET));
				}
				level.setBlock(pos, state.setValue(CONTENT, BowlContent.MILK), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
			// Fish
			if (stack.is(Items.COD) || stack.is(Items.SALMON) || stack.is(Items.COOKED_COD) || stack.is(Items.COOKED_SALMON) || stack.is(Items.TROPICAL_FISH)) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				level.setBlock(pos, state.setValue(CONTENT, BowlContent.FISH), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.2f);
				return InteractionResult.SUCCESS;
			}
			// Meat / Poultry
			if (stack.is(Items.CHICKEN) || stack.is(Items.COOKED_CHICKEN) || stack.is(Items.RABBIT) || stack.is(Items.COOKED_RABBIT) || stack.is(Items.BEEF) || stack.is(Items.PORKCHOP)) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				level.setBlock(pos, state.setValue(CONTENT, BowlContent.MEAT), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.2f);
				return InteractionResult.SUCCESS;
			}
		} else if (player.isShiftKeyDown() && stack.isEmpty()) {
			// Sneak-click with empty hand to clean / empty bowl
			if (current == BowlContent.FISH) {
				popResource(level, pos, new ItemStack(Items.COD));
			} else if (current == BowlContent.MEAT) {
				popResource(level, pos, new ItemStack(Items.CHICKEN));
			}
			level.setBlock(pos, state.setValue(CONTENT, BowlContent.EMPTY), Block.UPDATE_ALL);
			level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8f, 1.0f);
			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	public static boolean hasFoodOrDrink(BlockState state) {
		return state.hasProperty(CONTENT) && state.getValue(CONTENT) != BowlContent.EMPTY;
	}

	public void consumeContent(Level level, BlockPos pos, BlockState state, Cat cat) {
		BowlContent content = state.getValue(CONTENT);
		if (content == BowlContent.EMPTY) {
			return;
		}

		if (content.isDrink()) {
			level.playSound(null, pos, SoundEvents.GENERIC_DRINK.value(), SoundSource.NEUTRAL, 0.6f, 1.1f);
			if (content == BowlContent.MILK) {
				cat.removeAllEffects();
			}
		} else if (content.isFood()) {
			level.playSound(null, pos, SoundEvents.GENERIC_EAT.value(), SoundSource.NEUTRAL, 0.7f, 1.0f);
			cat.heal(4.0f);
		}

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.HEART,
				cat.getX(),
				cat.getY() + 0.4,
				cat.getZ(),
				3,
				0.2,
				0.1,
				0.2,
				0.02
			);
		}

		level.setBlock(pos, state.setValue(CONTENT, BowlContent.EMPTY), Block.UPDATE_ALL);
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		return switch (state.getValue(CONTENT)) {
			case EMPTY -> 0;
			case WATER -> 1;
			case MILK -> 2;
			case FISH -> 3;
			case MEAT -> 4;
		};
	}
}
