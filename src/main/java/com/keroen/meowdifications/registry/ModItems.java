package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.item.CatnipMouseItem;
import com.keroen.meowdifications.item.FeatherWandItem;
import com.keroen.meowdifications.item.YarnBallItem;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class ModItems {
	public static final Item FEATHER_WAND = register(
		"feather_wand",
		FeatherWandItem::new,
		new Item.Properties().stacksTo(1)
	);

	public static final Item YARN_BALL = register(
		"yarn_ball",
		YarnBallItem::new,
		new Item.Properties().stacksTo(16)
	);

	public static final Item CATNIP = register(
		"catnip",
		props -> new BlockItem(ModBlocks.CATNIP_CROP, props),
		new Item.Properties()
	);

	public static final Item CATNIP_MOUSE = register(
		"catnip_mouse",
		CatnipMouseItem::new,
		new Item.Properties().stacksTo(16)
	);

	public static final Item CAT_LITTER = register(
		"cat_litter",
		Item::new,
		new Item.Properties()
	);

	public static final Item LITTER_SCOOP = register(
		"litter_scoop",
		Item::new,
		new Item.Properties().stacksTo(1)
	);

	public static final Item ORGANIC_COMPOST = register(
		"organic_compost",
		Item::new,
		new Item.Properties()
	);

	public static <T extends Item> T register(
		String name,
		Function<Item.Properties, T> factory,
		Item.Properties properties
	) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Meowdifications.id(name));
		T item = factory.apply(properties.setId(key));
		return Registry.register(BuiltInRegistries.ITEM, key, item);
	}

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered items for {}", Meowdifications.MOD_ID);
	}

	private ModItems() {}
}
