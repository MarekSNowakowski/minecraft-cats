package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModItemGroups {
	public static final CreativeModeTab MEOWDIFICATIONS_TAB = Registry.register(
		BuiltInRegistries.CREATIVE_MODE_TAB,
		Meowdifications.id("meowdifications"),
		FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.FEATHER_WAND))
			.title(Component.translatable("itemGroup.meowdifications"))
			.displayItems((parameters, output) -> {
				output.accept(ModBlocks.CAT_BED);
				output.accept(ModBlocks.WALL_PERCH);
				output.accept(ModBlocks.SCRATCHING_POST);
				output.accept(ModBlocks.CARDBOARD_SCRATCHER);
				output.accept(ModBlocks.LITTER_BOX);
				output.accept(ModItems.FEATHER_WAND);
				output.accept(ModItems.YARN_BALL);
				output.accept(ModItems.CATNIP);
				output.accept(ModItems.CATNIP_MOUSE);
				output.accept(ModItems.CAT_LITTER);
				output.accept(ModItems.LITTER_SCOOP);
				output.accept(ModItems.ORGANIC_COMPOST);
			})
			.build()
	);

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered creative mode tabs for {}", Meowdifications.MOD_ID);
	}

	private ModItemGroups() {}
}
