package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DanglingToyBlock extends Block {
	private static final VoxelShape SHAPE = Block.box(5.0, 4.0, 5.0, 11.0, 16.0, 11.0);

	public DanglingToyBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		batToy(level, pos);
		return InteractionResult.SUCCESS;
	}

	public void batToy(Level level, BlockPos pos) {
		level.playSound(
			null,
			pos,
			SoundEvents.BELL_BLOCK,
			SoundSource.BLOCKS,
			0.5f,
			1.6f + level.getRandom().nextFloat() * 0.4f
		);

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.NOTE,
				pos.getX() + 0.5,
				pos.getY() + 0.4,
				pos.getZ() + 0.5,
				2,
				0.1,
				0.1,
				0.1,
				level.getRandom().nextDouble()
			);
		}
	}
}
