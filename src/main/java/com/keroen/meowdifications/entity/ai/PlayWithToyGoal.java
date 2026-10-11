package com.keroen.meowdifications.entity.ai;

import com.keroen.meowdifications.entity.YarnBallEntity;
import com.keroen.meowdifications.registry.ModItems;
import com.keroen.meowdifications.registry.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class PlayWithToyGoal extends Goal {
	private final Cat cat;
	private Entity targetToy;
	private int playState;
	private int stateTicks;
	private int cooldownTicks;

	private static final int STATE_STALK = 0;
	private static final int STATE_WIGGLE = 1;
	private static final int STATE_POUNCE = 2;

	public PlayWithToyGoal(Cat cat) {
		this.cat = cat;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.cooldownTicks = 40 + cat.getRandom().nextInt(40);
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

		// Check for nearby YarnBallEntity
		List<YarnBallEntity> balls = this.cat.level().getEntitiesOfClass(
			YarnBallEntity.class,
			this.cat.getBoundingBox().inflate(10.0),
			Entity::isAlive
		);
		if (!balls.isEmpty()) {
			this.targetToy = balls.get(0);
			return true;
		}

		// Check for nearby Player holding Feather Wand
		List<Player> players = this.cat.level().getEntitiesOfClass(
			Player.class,
			this.cat.getBoundingBox().inflate(8.0),
			p -> p.isHolding(ModItems.FEATHER_WAND)
		);
		if (!players.isEmpty()) {
			this.targetToy = players.get(0);
			return true;
		}

		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return this.targetToy != null && this.targetToy.isAlive() && !this.cat.isOrderedToSit() && this.stateTicks < 120;
	}

	@Override
	public void start() {
		this.playState = STATE_STALK;
		this.stateTicks = 0;
		if (this.targetToy != null) {
			this.cat.getNavigation().moveTo(this.targetToy, 0.9);
		}
	}

	@Override
	public void stop() {
		this.targetToy = null;
		this.stateTicks = 0;
		this.cooldownTicks = 60 + this.cat.getRandom().nextInt(60);
		this.cat.getNavigation().stop();
	}

	@Override
	public void tick() {
		if (this.targetToy == null) return;
		this.stateTicks++;

		this.cat.getLookControl().setLookAt(this.targetToy, 30.0f, 30.0f);
		double distSq = this.cat.distanceToSqr(this.targetToy);

		switch (this.playState) {
			case STATE_STALK -> {
				this.cat.getNavigation().moveTo(this.targetToy, 0.85);
				if (distSq < 3.0) {
					this.playState = STATE_WIGGLE;
					this.stateTicks = 0;
					this.cat.getNavigation().stop();
				}
			}
			case STATE_WIGGLE -> {
				if (this.stateTicks >= 12) {
					this.playState = STATE_POUNCE;
					this.stateTicks = 0;
				}
			}
			case STATE_POUNCE -> {
				Vec3 dir = this.targetToy.position().subtract(this.cat.position()).normalize();
				this.cat.setDeltaMovement(dir.x * 0.45, 0.28, dir.z * 0.45);

				if (this.targetToy instanceof YarnBallEntity yarnBall) {
					Vec3 batImpulse = dir.scale(0.55 + this.cat.getRandom().nextDouble() * 0.3).add(0, 0.15, 0);
					yarnBall.bat(batImpulse);
				}

				this.cat.level().playSound(
					null,
					this.cat.blockPosition(),
					ModSoundEvents.PURR_CONTENT,
					SoundSource.NEUTRAL,
					0.7f,
					1.2f
				);

				this.playState = STATE_STALK;
				this.stateTicks = 0;
				this.cooldownTicks = 20;
			}
		}
	}
}
