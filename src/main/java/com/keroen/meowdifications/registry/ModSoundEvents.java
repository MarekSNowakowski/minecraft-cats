package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ModSoundEvents {
	public static final SoundEvent SCRATCH = register("block.scratcher.scratch");
	public static final SoundEvent LITTER_DIG = register("block.litter_box.dig");
	public static final SoundEvent PURR_CONTENT = register("entity.cat.purr_content");
	public static final SoundEvent TOY_SQUEAK = register("item.toy.squeak");
	public static final SoundEvent FEATHER_SWISH = register("item.feather_wand.swish");

	private static SoundEvent register(String path) {
		Identifier id = Meowdifications.id(path);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered sound events for {}", Meowdifications.MOD_ID);
	}

	private ModSoundEvents() {}
}
