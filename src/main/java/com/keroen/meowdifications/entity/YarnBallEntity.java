package com.keroen.meowdifications.entity;

import com.keroen.meowdifications.registry.ModEntities;
import com.keroen.meowdifications.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class YarnBallEntity extends Entity implements ItemSupplier {
	private int age;

	public YarnBallEntity(EntityType<? extends YarnBallEntity> entityType, Level level) {
		super(entityType, level);
		this.age = 0;
	}

	public YarnBallEntity(Level level, double x, double y, double z) {
		this(ModEntities.YARN_BALL, level);
		this.setPos(x, y, z);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public void tick() {
		super.tick();
		this.age++;

		if (!this.isNoGravity()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
		}

		this.move(MoverType.SELF, this.getDeltaMovement());

		double horizontalFriction = this.onGround() ? 0.88 : 0.98;
		this.setDeltaMovement(this.getDeltaMovement().multiply(horizontalFriction, 0.98, horizontalFriction));

		// Despawn after 5 minutes if untouched
		if (!this.level().isClientSide() && this.age > 6000) {
			this.discard();
		}
	}

	public void bat(Vec3 impulse) {
		this.setDeltaMovement(impulse);
		this.level().playSound(
			null,
			this.getX(),
			this.getY(),
			this.getZ(),
			SoundEvents.WOOL_HIT,
			SoundSource.NEUTRAL,
			0.8f,
			1.2f
		);

		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
				ParticleTypes.HAPPY_VILLAGER,
				this.getX(),
				this.getY() + 0.2,
				this.getZ(),
				3,
				0.1,
				0.1,
				0.1,
				0.02
			);
		}
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
		if (!this.level().isClientSide()) {
			ItemStack stack = new ItemStack(ModItems.YARN_BALL);
			if (!player.getInventory().add(stack)) {
				if (this.level() instanceof ServerLevel serverLevel) {
					this.spawnAtLocation(serverLevel, stack);
				}
			}
			this.discard();
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (!this.isRemoved()) {
			this.spawnAtLocation(level, new ItemStack(ModItems.YARN_BALL));
			this.discard();
			return true;
		}
		return false;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ModItems.YARN_BALL);
	}

	@Override
	protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
		this.age = input.getIntOr("Age", 0);
	}

	@Override
	protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {
		output.putInt("Age", this.age);
	}
}
