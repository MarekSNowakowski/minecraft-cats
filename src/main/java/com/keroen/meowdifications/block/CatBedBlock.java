package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatBedBlock extends HorizontalDirectionalBlock implements CatRestingSpot {
	public static final BooleanProperty OCCUPIED = BooleanProperty.create("occupied");
	private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 6.0, 15.0);

	public CatBedBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(OCCUPIED, false)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(OCCUPIED, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, OCCUPIED);
	}
}
