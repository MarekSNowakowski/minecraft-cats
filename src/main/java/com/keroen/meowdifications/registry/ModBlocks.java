package com.keroen.meowdifications.registry;

import com.keroen.meowdifications.Meowdifications;
import com.keroen.meowdifications.block.CardboardScratcherBlock;
import com.keroen.meowdifications.block.CatBedBlock;
import com.keroen.meowdifications.block.LitterBoxBlock;
import com.keroen.meowdifications.block.ScratchingPostBlock;
import com.keroen.meowdifications.block.WallPerchBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {
	public static final Block CAT_BED = registerBlock(
		"cat_bed",
		new CatBedBlock(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.WOOL).noOcclusion())
	);

	public static final Block WALL_PERCH = registerBlock(
		"wall_perch",
		new WallPerchBlock(BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion())
	);

	public static final Block SCRATCHING_POST = registerBlock(
		"scratching_post",
		new ScratchingPostBlock(BlockBehaviour.Properties.of().strength(1.2f).sound(SoundType.WOOD).noOcclusion())
	);

	public static final Block CARDBOARD_SCRATCHER = registerBlock(
		"cardboard_scratcher",
		new CardboardScratcherBlock(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOD).noOcclusion())
	);

	public static final Block LITTER_BOX = registerBlock(
		"litter_box",
		new LitterBoxBlock(BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion())
	);

	public static final Block CATNIP_CROP = registerBlockOnly(
		"catnip_crop",
		new com.keroen.meowdifications.block.CatnipCropBlock(
			BlockBehaviour.Properties.of().noCollision().ticksRandomly().instabreak().sound(SoundType.CROP)
		)
	);

	private static <T extends Block> T registerBlockOnly(String name, T block) {
		Identifier id = Meowdifications.id(name);
		Registry.register(BuiltInRegistries.BLOCK, id, block);
		return block;
	}

	private static <T extends Block> T registerBlock(String name, T block) {
		Identifier id = Meowdifications.id(name);
		Registry.register(BuiltInRegistries.BLOCK, id, block);
		Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
		return block;
	}

	public static void initialize() {
		Meowdifications.LOGGER.info("Registered blocks for {}", Meowdifications.MOD_ID);
	}

	private ModBlocks() {}
}
