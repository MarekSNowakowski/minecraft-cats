package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.block.CardboardScratcherBlock;
import com.keroen.meowdifications.block.CatBedBlock;
import com.keroen.meowdifications.block.CatnipCropBlock;
import com.keroen.meowdifications.block.LitterBoxBlock;
import com.keroen.meowdifications.block.ScratchingPostBlock;
import com.keroen.meowdifications.block.WallPerchBlock;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
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
