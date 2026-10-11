package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallPerchBlock extends HorizontalDirectionalBlock {
	private static final VoxelShape SHAPE_NORTH = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 12.0);
	private static final VoxelShape SHAPE_SOUTH = Block.box(0.0, 12.0, 4.0, 16.0, 16.0, 16.0);
	private static final VoxelShape SHAPE_WEST = Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 16.0);
	private static final VoxelShape SHAPE_EAST = Block.box(4.0, 12.0, 0.0, 16.0, 16.0, 16.0);

	public WallPerchBlock(BlockBehaviour.Properties properties) {
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
		Direction clickedFace = context.getClickedFace();
		Direction facing = clickedFace.getAxis().isHorizontal() ? clickedFace : context.getHorizontalDirection().getOpposite();
		return this.defaultBlockState().setValue(FACING, facing);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
