package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatGrassBlock extends Block {
	public static final IntegerProperty NIBBLES = IntegerProperty.create("nibbles", 0, 3);
	private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 7.0, 12.0);

	public CatGrassBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(NIBBLES, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NIBBLES);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (stack.is(Items.BONE_MEAL) && state.getValue(NIBBLES) > 0) {
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
			level.setBlock(pos, state.setValue(NIBBLES, 0), Block.UPDATE_ALL);
			level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, 6, 0.2, 0.1, 0.2, 0.05);
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(NIBBLES) > 0;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
		int current = state.getValue(NIBBLES);
		if (current > 0 && random.nextInt(6) == 0) {
			level.setBlock(pos, state.setValue(NIBBLES, current - 1), Block.UPDATE_ALL);
		}
	}

	public static boolean canNibble(BlockState state) {
		return state.hasProperty(NIBBLES) && state.getValue(NIBBLES) < 3;
	}

	public void nibbleGrass(Level level, BlockPos pos, BlockState state, Cat cat) {
		int current = state.getValue(NIBBLES);
		if (current >= 3) {
			return;
		}

		level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 0.8f, 1.3f);
		cat.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.HEART,
				cat.getX(),
				cat.getY() + 0.35,
				cat.getZ(),
				2,
				0.15,
				0.1,
				0.15,
				0.02
			);
		}

		level.setBlock(pos, state.setValue(NIBBLES, current + 1), Block.UPDATE_ALL);
	}
}
