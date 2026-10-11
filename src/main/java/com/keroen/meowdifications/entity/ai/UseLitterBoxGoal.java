package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.LitterBoxBlock;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class UseLitterBoxGoal extends MoveToBlockGoal {
	private final Cat cat;
	private int usingTicks;
	private int cooldownTicks;

	public UseLitterBoxGoal(Cat cat, double speedModifier, int searchRange, int verticalSearchRange) {
		super(cat, speedModifier, searchRange, verticalSearchRange);
		this.cat = cat;
		this.usingTicks = 0;
		this.cooldownTicks = 300 + cat.getRandom().nextInt(300);
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
		return !this.cat.isOrderedToSit() && this.usingTicks < 50 && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.usingTicks = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.usingTicks = 0;
		this.cooldownTicks = 1200 + this.cat.getRandom().nextInt(1200); // 1 to 2 minutes cooldown
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.usingTicks++;
			this.cat.getLookControl().setLookAt(
				this.blockPos.getX() + 0.5,
				this.blockPos.getY() + 0.2,
				this.blockPos.getZ() + 0.5,
				30.0f,
				30.0f
			);

			// Digging sound feedback
			if (this.usingTicks % 15 == 0) {
				this.cat.level().playSound(
					null,
					this.blockPos,
					ModSoundEvents.LITTER_DIG,
					SoundSource.BLOCKS,
					0.8f,
					1.0f + this.cat.getRandom().nextFloat() * 0.2f
				);
			}

			// Transition to dirty state upon finishing
			if (this.usingTicks == 49) {
				BlockState state = this.cat.level().getBlockState(this.blockPos);
				if (state.getBlock() instanceof LitterBoxBlock box) {
					box.makeDirty(this.cat.level(), this.blockPos, state);
				}
			}
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.getBlock() instanceof LitterBoxBlock box && box.isCleanAndFilled(state);
	}
}
