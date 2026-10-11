package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.entity.YarnBallEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {
	public static final ResourceKey<EntityType<?>> YARN_BALL_KEY = ResourceKey.create(
		Registries.ENTITY_TYPE,
		Meowdifications.id("yarn_ball")
	);

	public static final EntityType<YarnBallEntity> YARN_BALL = Registry.register(
		BuiltInRegistries.ENTITY_TYPE,
		YARN_BALL_KEY,
		EntityType.Builder.<YarnBallEntity>of(YarnBallEntity::new, MobCategory.MISC)
			.sized(0.35f, 0.35f)
			.clientTrackingRange(8)
			.updateInterval(2)
			.build(YARN_BALL_KEY)
	);

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered entity types for {}", Meowdifications.MOD_ID);
	}

	private ModEntities() {}
}
