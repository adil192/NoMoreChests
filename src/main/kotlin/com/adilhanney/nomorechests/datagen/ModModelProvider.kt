package com.adilhanney.nomorechests.datagen

import com.adilhanney.nomorechests.block.ModBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Blocks
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator

class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
  override fun generateBlockStateModels(generator: BlockStateModelGenerator) {
    generator.registerBuiltinWithParticle(ModBlocks.infiniteChest, Blocks.OAK_PLANKS.asItem())
  }

  override fun generateItemModels(generator: ItemModelGenerator) {
  }
}
