package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.PetBowlBlock;
import com.keroen.meowdifications.block.state.BowlContent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class EatFromBowlGoal extends MoveToBlockGoal {
	private final Cat cat;
	private int feedingTicks;
	private int cooldownTicks;

	public EatFromBowlGoal(Cat cat, double speedModifier, int searchRange, int verticalSearchRange) {
		super(cat, speedModifier, searchRange, verticalSearchRange);
		this.cat = cat;
		this.feedingTicks = 0;
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
		if (this.cat.isOrderedToSit() || this.feedingTicks >= 50) {
			return false;
		}
		BlockState state = this.cat.level().getBlockState(this.blockPos);
		return state.getBlock() instanceof PetBowlBlock && (PetBowlBlock.hasFoodOrDrink(state) || this.feedingTicks >= 40) && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.feedingTicks = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.feedingTicks = 0;
		this.cooldownTicks = 600 + this.cat.getRandom().nextInt(600); // 30-60s cooldown
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.feedingTicks++;
			this.cat.getLookControl().setLookAt(
				this.blockPos.getX() + 0.5,
				this.blockPos.getY() + 0.1,
				this.blockPos.getZ() + 0.5,
				30.0f,
				30.0f
			);

			// Sound feedback every 10 ticks while eating/drinking
			if (this.feedingTicks % 10 == 0 && this.feedingTicks < 40) {
				BlockState state = this.cat.level().getBlockState(this.blockPos);
				if (state.getBlock() instanceof PetBowlBlock) {
					BowlContent content = state.getValue(PetBowlBlock.CONTENT);
					if (content.isDrink()) {
						this.cat.level().playSound(null, this.cat.blockPosition(), SoundEvents.GENERIC_DRINK.value(), SoundSource.NEUTRAL, 0.4f, 1.2f);
					} else if (content.isFood()) {
						this.cat.level().playSound(null, this.cat.blockPosition(), SoundEvents.GENERIC_EAT.value(), SoundSource.NEUTRAL, 0.4f, 1.1f);
					}
				}
			}

			// Finished eating/drinking
			if (this.feedingTicks == 40) {
				BlockState state = this.cat.level().getBlockState(this.blockPos);
				if (state.getBlock() instanceof PetBowlBlock bowl) {
					bowl.consumeContent(this.cat.level(), this.blockPos, state, this.cat);
				}
			}
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.getBlock() instanceof PetBowlBlock && PetBowlBlock.hasFoodOrDrink(state);
	}
}
