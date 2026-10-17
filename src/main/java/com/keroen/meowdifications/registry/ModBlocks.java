package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.block.CardboardScratcherBlock;
import com.keroen.meowdifications.block.CatBedBlock;
import com.keroen.meowdifications.block.CatCondoBlock;
import com.keroen.meowdifications.block.CatGrassBlock;
import com.keroen.meowdifications.block.CatHammockBlock;
import com.keroen.meowdifications.block.CatnipCropBlock;
import com.keroen.meowdifications.block.CatTreeBridgeBlock;
import com.keroen.meowdifications.block.CatTreeShelfBlock;
import com.keroen.meowdifications.block.DanglingToyBlock;
import com.keroen.meowdifications.block.LitterBoxBlock;
import com.keroen.meowdifications.block.PetBowlBlock;
import com.keroen.meowdifications.block.ScratchingPoleBlock;
import com.keroen.meowdifications.block.ScratchingPostBlock;
import com.keroen.meowdifications.block.WallCornerScratcherBlock;
import com.keroen.meowdifications.block.WallPerchBlock;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {
	public static final Block CAT_BED = registerBlock(
		"cat_bed",
		CatBedBlock::new,
		BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.WOOL).noOcclusion()
	);

	public static final Block WALL_PERCH = registerBlock(
		"wall_perch",
		WallPerchBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block SCRATCHING_POST = registerBlock(
		"scratching_post",
		ScratchingPostBlock::new,
		BlockBehaviour.Properties.of().strength(1.2f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CARDBOARD_SCRATCHER = registerBlock(
		"cardboard_scratcher",
		CardboardScratcherBlock::new,
		BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block LITTER_BOX = registerBlock(
		"litter_box",
		LitterBoxBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CATNIP_CROP = registerBlockOnly(
		"catnip_crop",
		CatnipCropBlock::new,
		BlockBehaviour.Properties.of().noCollision().randomTicks().instabreak().sound(SoundType.CROP)
	);

	// Modular Cat Tree Components
	public static final Block SCRATCHING_POLE = registerBlock(
		"scratching_pole",
		ScratchingPoleBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CAT_TREE_SHELF = registerBlock(
		"cat_tree_shelf",
		CatTreeShelfBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CAT_CONDO = registerBlock(
		"cat_condo",
		CatCondoBlock::new,
		BlockBehaviour.Properties.of().strength(1.2f).sound(SoundType.WOOD).noOcclusion()
	);

	// Color variants for Cat Beds, Shelves, and Condos (all 16 DyeColors)
	public static final Map<DyeColor, Block> CAT_BEDS = registerDyedBlocks(
		"cat_bed",
		CatBedBlock::new,
		() -> BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.WOOL).noOcclusion()
	);

	public static final Map<DyeColor, Block> CAT_TREE_SHELVES = registerDyedBlocks(
		"cat_tree_shelf",
		CatTreeShelfBlock::new,
		() -> BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Map<DyeColor, Block> CAT_CONDOS = registerDyedBlocks(
		"cat_condo",
		CatCondoBlock::new,
		() -> BlockBehaviour.Properties.of().strength(1.2f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CAT_TREE_BRIDGE = registerBlock(
		"cat_tree_bridge",
		CatTreeBridgeBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block CAT_HAMMOCK = registerBlock(
		"cat_hammock",
		CatHammockBlock::new,
		BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.WOOL).noOcclusion()
	);

	public static final Block DANGLING_TOY = registerBlock(
		"dangling_toy",
		DanglingToyBlock::new,
		BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOL).noOcclusion()
	);

	public static final Block WALL_CORNER_SCRATCHER = registerBlock(
		"wall_corner_scratcher",
		WallCornerScratcherBlock::new,
		BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion()
	);

	public static final Block PET_BOWL = registerBlock(
		"pet_bowl",
		PetBowlBlock::new,
		BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.STONE).noOcclusion()
	);

	public static final Block CAT_GRASS = registerBlock(
		"cat_grass",
		CatGrassBlock::new,
		BlockBehaviour.Properties.of().strength(0.4f).sound(SoundType.GRASS).noOcclusion().randomTicks()
	);

	public static final Map<DyeColor, Block> CAT_HAMMOCKS = registerDyedBlocks(
		"cat_hammock",
		CatHammockBlock::new,
		() -> BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.WOOL).noOcclusion()
	);

	private static <T extends Block> Map<DyeColor, Block> registerDyedBlocks(
		String baseName,
		Function<BlockBehaviour.Properties, T> factory,
		Supplier<BlockBehaviour.Properties> propertiesSupplier
	) {
		Map<DyeColor, Block> map = new EnumMap<>(DyeColor.class);
		for (DyeColor color : DyeColor.values()) {
			String name = color.getName() + "_" + baseName;
			Block block = registerBlock(name, factory, propertiesSupplier.get());
			map.put(color, block);
		}
		return map;
	}

	private static <T extends Block> T registerBlockOnly(
		String name,
		Function<BlockBehaviour.Properties, T> factory,
		BlockBehaviour.Properties properties
	) {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Meowdifications.id(name));
		T block = factory.apply(properties.setId(blockKey));
		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	private static <T extends Block> T registerBlock(
		String name,
		Function<BlockBehaviour.Properties, T> factory,
		BlockBehaviour.Properties properties
	) {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Meowdifications.id(name));
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Meowdifications.id(name));
		T block = factory.apply(properties.setId(blockKey));
		Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
		Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
		return block;
	}

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered blocks for {}", Meowdifications.MOD_ID);
	}

	private ModBlocks() {}
}
