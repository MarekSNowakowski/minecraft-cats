package com.keroen.meowdifications.client.datagen;

import com.keroen.meowdifications.registry.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelGenerator extends FabricModelProvider {
	public ModModelGenerator(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		// Custom voxel-element block models and states are directly managed in assets
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.YARN_BALL, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.FEATHER_WAND, ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.CATNIP, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.CATNIP_MOUSE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.CAT_LITTER, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.LITTER_SCOOP, ModelTemplates.FLAT_HANDHELD_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.ORGANIC_COMPOST, ModelTemplates.FLAT_ITEM);
	}
}
