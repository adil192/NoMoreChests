package com.adilhanney.nomorechests.datagen

import com.adilhanney.nomorechests.block.ModBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import java.util.concurrent.CompletableFuture

class ModBlockTagProvider(
  output: FabricDataOutput,
  registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
  override fun configure(wrapperLookup: RegistryWrapper.WrapperLookup?) {
    getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
      .add(ModBlocks.infiniteChest)
  }
}
