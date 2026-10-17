package com.keroen.meowdifications.block;

import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScratchingPoleBlock extends RotatedPillarBlock implements CatScratcher {
	private static final VoxelShape SHAPE_Y = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
	private static final VoxelShape SHAPE_X = Block.box(0.0, 5.0, 5.0, 16.0, 11.0, 11.0);
	private static final VoxelShape SHAPE_Z = Block.box(5.0, 5.0, 0.0, 11.0, 11.0, 16.0);

	public ScratchingPoleBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Direction.Axis axis = state.getValue(AXIS);
		return switch (axis) {
			case X -> SHAPE_X;
			case Z -> SHAPE_Z;
			default -> SHAPE_Y;
		};
	}

	@Override
	public void performScratch(Level level, BlockPos pos, BlockState state) {
		level.playSound(
			null,
			pos,
			ModSoundEvents.SCRATCH,
			SoundSource.BLOCKS,
			1.0f,
			0.9f + level.getRandom().nextFloat() * 0.2f
		);

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.CRIT,
				pos.getX() + 0.5,
				pos.getY() + 0.5,
				pos.getZ() + 0.5,
				5,
				0.2,
				0.2,
				0.2,
				0.05
			);
		}
	}
}
