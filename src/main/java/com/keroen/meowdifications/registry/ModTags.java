package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
	public static final class Blocks {
		public static final TagKey<Block> CAT_BEDS = TagKey.create(Registries.BLOCK, Meowdifications.id("cat_beds"));
		public static final TagKey<Block> CAT_SLEEP_SPOTS = TagKey.create(Registries.BLOCK, Meowdifications.id("cat_sleep_spots"));
		public static final TagKey<Block> SCRATCHERS = TagKey.create(Registries.BLOCK, Meowdifications.id("scratchers"));
		public static final TagKey<Block> CAT_TREE_PARTS = TagKey.create(Registries.BLOCK, Meowdifications.id("cat_tree_parts"));

		private Blocks() {}
	}

	public static final class Items {
		public static final TagKey<Item> CAT_BEDS = TagKey.create(Registries.ITEM, Meowdifications.id("cat_beds"));
		public static final TagKey<Item> SCRATCHERS = TagKey.create(Registries.ITEM, Meowdifications.id("scratchers"));
		public static final TagKey<Item> CAT_TREE_PARTS = TagKey.create(Registries.ITEM, Meowdifications.id("cat_tree_parts"));

		private Items() {}
	}

	private ModTags() {}
}
