package com.keroen.meowdifications.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public interface CatRestingSpot {
	/**
	 * Checks if this resting spot is currently occupied by another cat.
	 */
	default boolean isOccupied(LevelReader level, BlockPos pos, BlockState state, @Nullable Cat checkingCat) {
		if (state.hasProperty(CatBedBlock.OCCUPIED) && state.getValue(CatBedBlock.OCCUPIED)) {
			return true;
		}
		if (level instanceof Level lvl) {
			List<Cat> cats = lvl.getEntitiesOfClass(Cat.class, new AABB(pos).inflate(0.15));
			return cats.stream().anyMatch(c -> c != checkingCat);
		}
		return false;
	}

	default boolean isOccupied(LevelReader level, BlockPos pos, BlockState state) {
		return isOccupied(level, pos, state, null);
	}

	/**
	 * Marks this resting spot as occupied or free.
	 */
	default void setOccupied(Level level, BlockPos pos, BlockState state, boolean occupied) {
		if (state.hasProperty(CatBedBlock.OCCUPIED) && state.getValue(CatBedBlock.OCCUPIED) != occupied) {
			level.setBlock(pos, state.setValue(CatBedBlock.OCCUPIED, occupied), Block.UPDATE_ALL);
		}
	}
}
