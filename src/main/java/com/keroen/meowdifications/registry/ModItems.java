package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.item.CatnipMouseItem;
import com.keroen.meowdifications.item.FeatherWandItem;
import com.keroen.meowdifications.item.YarnBallItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public final class ModItems {
	public static final Item FEATHER_WAND = register(
		"feather_wand",
		new FeatherWandItem(new Item.Properties().stacksTo(1))
	);

	public static final Item YARN_BALL = register(
		"yarn_ball",
		new YarnBallItem(new Item.Properties().stacksTo(16))
	);

	public static final Item CATNIP = register(
		"catnip",
		new Item(new Item.Properties())
	);

	public static final Item CATNIP_MOUSE = register(
		"catnip_mouse",
		new CatnipMouseItem(new Item.Properties().stacksTo(16))
	);

	public static final Item CAT_LITTER = register(
		"cat_litter",
		new Item(new Item.Properties())
	);

	public static final Item LITTER_SCOOP = register(
		"litter_scoop",
		new Item(new Item.Properties().stacksTo(1))
	);

	public static final Item ORGANIC_COMPOST = register(
		"organic_compost",
		new Item(new Item.Properties())
	);

	public static <T extends Item> T register(String name, T item) {
		Identifier id = Meowdifications.id(name);
		return Registry.register(BuiltInRegistries.ITEM, id, item);
	}

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered items for {}", Meowdifications.MOD_ID);
	}

	private ModItems() {}
}
