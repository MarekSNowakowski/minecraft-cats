package com.keroen.meowdifications.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface CatScratcher {
	/**
	 * Called when a cat scratches this block. Emits scratching sounds and particles.
	 */
	void performScratch(Level level, BlockPos pos, BlockState state);
}
