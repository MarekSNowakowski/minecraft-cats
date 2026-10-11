package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.CatBedBlock;
import com.keroen.meowdifications.block.WallPerchBlock;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class UseCatBedGoal extends MoveToBlockGoal {
	private final Cat cat;
	private int restingTicks;
	private int maxRestingTicks;
	private int cooldownTicks;

	public UseCatBedGoal(Cat cat, double speedModifier, int searchRange, int verticalSearchRange) {
		super(cat, speedModifier, searchRange, verticalSearchRange);
		this.cat = cat;
		this.restingTicks = 0;
		this.maxRestingTicks = 200;
		this.cooldownTicks = 100 + cat.getRandom().nextInt(200);
	}

	@Override
	public boolean canUse() {
		if (this.cooldownTicks > 0) {
			this.cooldownTicks--;
			return false;
		}
		if (this.cat.isOrderedToSit() || this.cat.isLying()) {
			return false;
		}
		return super.canUse();
	}

	@Override
	public boolean canContinueToUse() {
		return !this.cat.isOrderedToSit() && this.restingTicks < this.maxRestingTicks && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.restingTicks = 0;
		this.maxRestingTicks = 160 + this.cat.getRandom().nextInt(160);
	}

	@Override
	public void stop() {
		super.stop();
		this.releaseBed();
		this.cat.setLying(false);
		this.restingTicks = 0;
		this.cooldownTicks = 800 + this.cat.getRandom().nextInt(800);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.restingTicks++;

			// Mark bed occupied
			BlockState state = this.cat.level().getBlockState(this.blockPos);
			if (state.getBlock() instanceof CatBedBlock) {
				CatBedBlock.setOccupied(this.cat.level(), this.blockPos, state, true);
			}

			// Cat curls up in bed
			this.cat.setLying(true);

			// Gentle purring occasionally
			if (this.restingTicks % 80 == 0) {
				this.cat.level().playSound(
					null,
					this.cat.blockPosition(),
					ModSoundEvents.PURR_CONTENT,
					SoundSource.NEUTRAL,
					0.6f,
					1.0f + this.cat.getRandom().nextFloat() * 0.1f
				);
			}
		}
	}

	private void releaseBed() {
		if (this.blockPos != null) {
			BlockState state = this.cat.level().getBlockState(this.blockPos);
			if (state.getBlock() instanceof CatBedBlock) {
				CatBedBlock.setOccupied(this.cat.level(), this.blockPos, state, false);
			}
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof CatBedBlock) {
			return !CatBedBlock.isOccupied(state);
		}
		return state.getBlock() instanceof WallPerchBlock;
	}
}
