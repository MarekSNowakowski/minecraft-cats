package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class CatAffectionGoal extends Goal {
	private final Cat cat;
	private Player owner;
	private int affectionTicks;
	private int cooldownTicks;

	public CatAffectionGoal(Cat cat) {
		this.cat = cat;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.cooldownTicks = 200 + cat.getRandom().nextInt(200);
	}

	@Override
	public boolean canUse() {
		if (this.cooldownTicks > 0) {
			this.cooldownTicks--;
			return false;
		}
		if (!this.cat.isTame() || this.cat.isOrderedToSit() || this.cat.isLying()) {
			return false;
		}

		LivingEntity ownerEntity = this.cat.getOwner();
		if (ownerEntity instanceof Player player && player.isAlive() && this.cat.distanceToSqr(player) <= 36.0) {
			this.owner = player;
			return true;
		}
		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return this.owner != null && this.owner.isAlive() && !this.cat.isOrderedToSit() && this.affectionTicks < 50;
	}

	@Override
	public void start() {
		this.affectionTicks = 0;
		if (this.owner != null) {
			this.cat.getNavigation().moveTo(this.owner, 0.95);
		}
	}

	@Override
	public void stop() {
		this.owner = null;
		this.affectionTicks = 0;
		this.cooldownTicks = 800 + this.cat.getRandom().nextInt(800);
		this.cat.getNavigation().stop();
	}

	@Override
	public void tick() {
		if (this.owner == null) return;
		this.cat.getLookControl().setLookAt(this.owner, 30.0f, 30.0f);

		double distSq = this.cat.distanceToSqr(this.owner);
		if (distSq > 1.8) {
			this.cat.getNavigation().moveTo(this.owner, 0.95);
		} else {
			// Close enough to nuzzle
			this.cat.getNavigation().stop();
			this.affectionTicks++;

			// Nuzzling purrs and heart particles
			if (this.affectionTicks % 12 == 0) {
				this.cat.level().playSound(
					null,
					this.cat.blockPosition(),
					ModSoundEvents.PURR_CONTENT,
					SoundSource.NEUTRAL,
					0.7f,
					1.1f + this.cat.getRandom().nextFloat() * 0.1f
				);

				if (this.cat.level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(
						ParticleTypes.HEART,
						this.cat.getX(),
						this.cat.getY() + 0.4,
						this.cat.getZ(),
						1,
						0.15,
						0.15,
						0.15,
						0.02
					);
				}
			}
		}
	}
}
