package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.block.CatBedBlock;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class CatKneadGoal extends Goal {
	private final Cat cat;
	private int kneadTicks;
	private int cooldownTicks;

	public CatKneadGoal(Cat cat) {
		this.cat = cat;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.cooldownTicks = 300 + cat.getRandom().nextInt(300);
	}

	@Override
	public boolean canUse() {
		if (this.cooldownTicks > 0) {
			this.cooldownTicks--;
			return false;
		}
		if (this.cat.isOrderedToSit() || !this.cat.onGround()) {
			return false;
		}

		BlockPos pos = this.cat.blockPosition();
		BlockState standingOn = this.cat.level().getBlockState(pos.below());
		BlockState inBlock = this.cat.level().getBlockState(pos);

		return isKneadableSurface(standingOn) || isKneadableSurface(inBlock);
	}

	@Override
	public boolean canContinueToUse() {
		return !this.cat.isOrderedToSit() && this.kneadTicks < 70;
	}

	@Override
	public void start() {
		this.kneadTicks = 0;
		this.cat.getNavigation().stop();
	}

	@Override
	public void stop() {
		this.kneadTicks = 0;
		this.cooldownTicks = 800 + this.cat.getRandom().nextInt(800);
	}

	@Override
	public void tick() {
		this.kneadTicks++;

		// Rhythmic kneading: alternate paw sounds every 10 ticks
		if (this.kneadTicks % 10 == 0) {
			this.cat.level().playSound(
				null,
				this.cat.blockPosition(),
				SoundEvents.WOOL_STEP,
				SoundSource.NEUTRAL,
				0.6f,
				1.2f + (this.kneadTicks % 20 == 0 ? 0.1f : -0.1f)
			);
		}

		// Soft contentment purr every 25 ticks
		if (this.kneadTicks % 25 == 0) {
			this.cat.level().playSound(
				null,
				this.cat.blockPosition(),
				ModSoundEvents.PURR_CONTENT,
				SoundSource.NEUTRAL,
				0.6f,
				1.0f
			);

			if (this.cat.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(
					ParticleTypes.HEART,
					this.cat.getX(),
					this.cat.getY() + 0.35,
					this.cat.getZ(),
					1,
					0.1,
					0.1,
					0.1,
					0.01
				);
			}
		}
	}

	private static boolean isKneadableSurface(BlockState state) {
		return state.getBlock() instanceof CatBedBlock
			|| state.is(BlockTags.WOOL)
			|| state.is(BlockTags.WOOL_CARPETS)
			|| state.is(BlockTags.BEDS);
	}
}
