package com.keroen.meowdifications.block;

import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallCornerScratcherBlock extends HorizontalDirectionalBlock implements CatScratcher {
	private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

	public WallCornerScratcherBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
		builder.add(FACING);
	}

	@Override
	public void performScratch(Level level, BlockPos pos, BlockState state) {
		level.playSound(
			null,
			pos,
			ModSoundEvents.SCRATCH,
			SoundSource.BLOCKS,
			1.0f,
			0.95f + level.getRandom().nextFloat() * 0.2f
		);

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.CRIT,
				pos.getX() + 0.5,
				pos.getY() + 0.5,
				pos.getZ() + 0.5,
				4,
				0.2,
				0.2,
				0.2,
				0.04
			);
		}
	}
}
