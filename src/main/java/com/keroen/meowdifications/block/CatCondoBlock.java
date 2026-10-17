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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatCondoBlock extends HorizontalDirectionalBlock implements CatRestingSpot, CatScratcher {
	private static final VoxelShape BASE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	private static final VoxelShape ROOF = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);

	// North facing (entrance facing North / -Z)
	private static final VoxelShape SHAPE_NORTH = Shapes.or(
		BASE,
		ROOF,
		Block.box(0.0, 2.0, 14.0, 16.0, 14.0, 16.0), // South back wall
		Block.box(0.0, 2.0, 0.0, 2.0, 14.0, 16.0),   // West side wall
		Block.box(14.0, 2.0, 0.0, 16.0, 14.0, 16.0), // East side wall
		Block.box(0.0, 2.0, 0.0, 4.0, 14.0, 2.0),    // Front left pillar
		Block.box(12.0, 2.0, 0.0, 16.0, 14.0, 2.0),  // Front right pillar
		Block.box(4.0, 11.0, 0.0, 12.0, 14.0, 2.0)   // Front top lintel
	);

	// South facing (entrance facing South / +Z)
	private static final VoxelShape SHAPE_SOUTH = Shapes.or(
		BASE,
		ROOF,
		Block.box(0.0, 2.0, 0.0, 16.0, 14.0, 2.0),   // North back wall
		Block.box(0.0, 2.0, 0.0, 2.0, 14.0, 16.0),   // West side wall
		Block.box(14.0, 2.0, 0.0, 16.0, 14.0, 16.0), // East side wall
		Block.box(0.0, 2.0, 14.0, 4.0, 14.0, 16.0),  // Front left pillar
		Block.box(12.0, 2.0, 14.0, 16.0, 14.0, 16.0),// Front right pillar
		Block.box(4.0, 11.0, 14.0, 12.0, 14.0, 16.0) // Front top lintel
	);

	// West facing (entrance facing West / -X)
	private static final VoxelShape SHAPE_WEST = Shapes.or(
		BASE,
		ROOF,
		Block.box(14.0, 2.0, 0.0, 16.0, 14.0, 16.0), // East back wall
		Block.box(0.0, 2.0, 0.0, 16.0, 14.0, 2.0),   // North side wall
		Block.box(0.0, 2.0, 14.0, 16.0, 14.0, 16.0), // South side wall
		Block.box(0.0, 2.0, 0.0, 2.0, 14.0, 4.0),    // Front left pillar
		Block.box(0.0, 2.0, 12.0, 2.0, 14.0, 16.0),  // Front right pillar
		Block.box(0.0, 11.0, 4.0, 2.0, 14.0, 12.0)   // Front top lintel
	);

	// East facing (entrance facing East / +X)
	private static final VoxelShape SHAPE_EAST = Shapes.or(
		BASE,
		ROOF,
		Block.box(0.0, 2.0, 0.0, 2.0, 14.0, 16.0),   // West back wall
		Block.box(0.0, 2.0, 0.0, 16.0, 14.0, 2.0),   // North side wall
		Block.box(0.0, 2.0, 14.0, 16.0, 14.0, 16.0), // South side wall
		Block.box(14.0, 2.0, 0.0, 16.0, 14.0, 4.0),  // Front left pillar
		Block.box(14.0, 2.0, 12.0, 16.0, 14.0, 16.0),// Front right pillar
		Block.box(14.0, 11.0, 4.0, 16.0, 14.0, 12.0) // Front top lintel
	);

	public CatCondoBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_SOUTH;
			case WEST -> SHAPE_WEST;
			case EAST -> SHAPE_EAST;
			default -> SHAPE_NORTH;
		};
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
			0.95f + level.getRandom().nextFloat() * 0.15f
		);

		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.CRIT,
				pos.getX() + 0.5,
				pos.getY() + 0.5,
				pos.getZ() + 0.5,
				4,
				0.25,
				0.25,
				0.25,
				0.04
			);
		}
	}
}
