package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatTreeShelfBlock extends HorizontalDirectionalBlock implements CatRestingSpot {
	public enum ShelfAttachment implements StringRepresentable {
		PLATFORM("platform"),
		WALL("wall");

		private final String name;

		ShelfAttachment(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}

	public static final EnumProperty<ShelfAttachment> ATTACHMENT = EnumProperty.create("attachment", ShelfAttachment.class);

	private static final VoxelShape SHAPE_PLATFORM = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
	private static final VoxelShape SHAPE_WALL_NORTH = Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 12.0);
	private static final VoxelShape SHAPE_WALL_SOUTH = Block.box(0.0, 12.0, 4.0, 16.0, 16.0, 16.0);
	private static final VoxelShape SHAPE_WALL_WEST = Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 16.0);
	private static final VoxelShape SHAPE_WALL_EAST = Block.box(4.0, 12.0, 0.0, 16.0, 16.0, 16.0);

	public CatTreeShelfBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(ATTACHMENT, ShelfAttachment.PLATFORM)
		);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (state.getValue(ATTACHMENT) == ShelfAttachment.PLATFORM) {
			return SHAPE_PLATFORM;
		}
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_WALL_SOUTH;
			case WEST -> SHAPE_WALL_WEST;
			case EAST -> SHAPE_WALL_EAST;
			default -> SHAPE_WALL_NORTH;
		};
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction clickedFace = context.getClickedFace();
		if (clickedFace == Direction.UP || clickedFace == Direction.DOWN) {
			return this.defaultBlockState()
				.setValue(ATTACHMENT, ShelfAttachment.PLATFORM)
				.setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return this.defaultBlockState()
				.setValue(ATTACHMENT, ShelfAttachment.WALL)
				.setValue(FACING, clickedFace);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, ATTACHMENT);
	}
}
