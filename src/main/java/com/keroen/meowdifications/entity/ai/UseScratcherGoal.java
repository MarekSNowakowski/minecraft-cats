package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.CardboardScratcherBlock;
import com.keroen.meowdifications.block.ScratchingPostBlock;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class UseScratcherGoal extends MoveToBlockGoal {
	private final Cat cat;
	private int scratchingTicks;
	private int cooldownTicks;

	public UseScratcherGoal(Cat cat, double speedModifier, int searchRange, int verticalSearchRange) {
		super(cat, speedModifier, searchRange, verticalSearchRange);
		this.cat = cat;
		this.scratchingTicks = 0;
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
		return !this.cat.isOrderedToSit() && this.scratchingTicks < 60 && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.scratchingTicks = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.scratchingTicks = 0;
		this.cooldownTicks = 600 + this.cat.getRandom().nextInt(600); // 30s to 60s cooldown
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.scratchingTicks++;
			this.cat.getLookControl().setLookAt(
				this.blockPos.getX() + 0.5,
				this.blockPos.getY() + 0.5,
				this.blockPos.getZ() + 0.5,
				30.0f,
				30.0f
			);

			// Claw and scratch every 10 ticks
			if (this.scratchingTicks % 10 == 0) {
				BlockState state = this.cat.level().getBlockState(this.blockPos);
				if (state.getBlock() instanceof ScratchingPostBlock post) {
					post.performScratch(this.cat.level(), this.blockPos, state);
				} else if (state.getBlock() instanceof CardboardScratcherBlock cardboard) {
					cardboard.performScratch(this.cat.level(), this.blockPos, state);
				}
			}

			// Soft purr of satisfaction when finished
			if (this.scratchingTicks == 59) {
				this.cat.level().playSound(
					null,
					this.cat.blockPosition(),
					ModSoundEvents.PURR_CONTENT,
					SoundSource.NEUTRAL,
					0.7f,
					1.0f
				);
			}
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.getBlock() instanceof ScratchingPostBlock || state.getBlock() instanceof CardboardScratcherBlock;
	}
}
