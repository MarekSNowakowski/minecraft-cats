package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatTreeBridgeBlock extends Block implements CatRestingSpot {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	private static final VoxelShape SHAPE_X = Block.box(0.0, 10.0, 2.0, 16.0, 14.0, 14.0);
	private static final VoxelShape SHAPE_Z = Block.box(2.0, 10.0, 0.0, 14.0, 14.0, 16.0);

	public CatTreeBridgeBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(AXIS) == Direction.Axis.Z ? SHAPE_Z : SHAPE_X;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction.Axis axis = context.getHorizontalDirection().getAxis();
		return this.defaultBlockState().setValue(AXIS, axis);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}
}
