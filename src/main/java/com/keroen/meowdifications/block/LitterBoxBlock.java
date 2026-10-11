package com.keroen.meowdifications.block;

import com.keroen.meowdifications.block.state.LitterFillType;
import com.keroen.meowdifications.registry.ModItems;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LitterBoxBlock extends HorizontalDirectionalBlock {
	public static final BooleanProperty DIRTY = BooleanProperty.create("dirty");
	public static final EnumProperty<LitterFillType> FILL_TYPE = EnumProperty.create("fill_type", LitterFillType.class);

	private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0);

	public LitterBoxBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(DIRTY, false)
				.setValue(FILL_TYPE, LitterFillType.EMPTY)
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
		builder.add(FACING, DIRTY, FILL_TYPE);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		LitterFillType currentFill = state.getValue(FILL_TYPE);
		boolean isDirty = state.getValue(DIRTY);

		// Filling an empty box with sand or cat litter
		if (currentFill == LitterFillType.EMPTY) {
			if (stack.is(Items.SAND) || stack.is(Items.RED_SAND)) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				level.setBlock(pos, state.setValue(FILL_TYPE, LitterFillType.SAND).setValue(DIRTY, false), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
			if (stack.is(ModItems.CAT_LITTER)) {
				if (!player.getAbilities().instabuild) {
					stack.shrink(1);
				}
				level.setBlock(pos, state.setValue(FILL_TYPE, LitterFillType.LITTER).setValue(DIRTY, false), Block.UPDATE_ALL);
				level.playSound(null, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
		}

		// Cleaning a dirty box with shovel or litter scoop
		if (isDirty) {
			boolean isCleaningTool = stack.is(ItemTags.SHOVELS) || stack.is(ModItems.LITTER_SCOOP);
			if (isCleaningTool) {
				if (!level.isClientSide()) {
					level.setBlock(pos, state.setValue(DIRTY, false), Block.UPDATE_ALL);
					ItemEntity drop = new ItemEntity(
						level,
						pos.getX() + 0.5,
						pos.getY() + 0.3,
						pos.getZ() + 0.5,
						new ItemStack(ModItems.ORGANIC_COMPOST)
					);
					level.addFreshEntity(drop);
				}
				level.playSound(null, pos, ModSoundEvents.LITTER_DIG, SoundSource.BLOCKS, 1.0f, 1.0f);
				return InteractionResult.SUCCESS;
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public boolean isCleanAndFilled(BlockState state) {
		return !state.getValue(DIRTY) && state.getValue(FILL_TYPE) != LitterFillType.EMPTY;
	}

	public void makeDirty(Level level, BlockPos pos, BlockState state) {
		if (!state.getValue(DIRTY)) {
			level.setBlock(pos, state.setValue(DIRTY, true), Block.UPDATE_ALL);
			level.playSound(null, pos, ModSoundEvents.LITTER_DIG, SoundSource.BLOCKS, 1.0f, 1.0f);
		}
	}
}
