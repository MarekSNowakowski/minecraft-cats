package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.CatGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class NibbleCatGrassGoal extends MoveToBlockGoal {
	private final Cat cat;
	private int nibbleTicks;
	private int cooldownTicks;

	public NibbleCatGrassGoal(Cat cat, double speedModifier, int searchRange, int verticalSearchRange) {
		super(cat, speedModifier, searchRange, verticalSearchRange);
		this.cat = cat;
		this.nibbleTicks = 0;
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
		if (this.cat.isOrderedToSit() || this.nibbleTicks >= 40) {
			return false;
		}
		BlockState state = this.cat.level().getBlockState(this.blockPos);
		return state.getBlock() instanceof CatGrassBlock && (CatGrassBlock.canNibble(state) || this.nibbleTicks >= 30) && super.canContinueToUse();
	}

	@Override
	public void start() {
		super.start();
		this.nibbleTicks = 0;
	}

	@Override
	public void stop() {
		super.stop();
		this.nibbleTicks = 0;
		this.cooldownTicks = 800 + this.cat.getRandom().nextInt(800);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isReachedTarget()) {
			this.nibbleTicks++;
			this.cat.getLookControl().setLookAt(
				this.blockPos.getX() + 0.5,
				this.blockPos.getY() + 0.3,
				this.blockPos.getZ() + 0.5,
				30.0f,
				30.0f
			);

			if (this.nibbleTicks == 15) {
				this.cat.level().playSound(null, this.cat.blockPosition(), SoundEvents.GRASS_STEP, SoundSource.NEUTRAL, 0.5f, 1.3f);
			}

			if (this.nibbleTicks == 30) {
				BlockState state = this.cat.level().getBlockState(this.blockPos);
				if (state.getBlock() instanceof CatGrassBlock grass) {
					grass.nibbleGrass(this.cat.level(), this.blockPos, state, this.cat);
				}
			}
		}
	}

	@Override
	protected boolean isValidTarget(LevelReader level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.getBlock() instanceof CatGrassBlock && CatGrassBlock.canNibble(state);
	}
}
