package com.keroen.meowdifications.block;

import com.keroen.meowdifications.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CatnipCropBlock extends CropBlock {
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
		Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
		Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
	};

	public CatnipCropBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.CATNIP;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE_BY_AGE[this.getAge(state)];
	}
}
