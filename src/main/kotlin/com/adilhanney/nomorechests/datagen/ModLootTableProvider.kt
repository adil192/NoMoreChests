package com.adilhanney.nomorechests.datagen

import com.adilhanney.nomorechests.block.ModBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class ModLootTableProvider(
  dataOutput: FabricDataOutput,
  registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricBlockLootTableProvider(dataOutput, registryLookup) {
  override fun generate() {
    addDrop(ModBlocks.infiniteChest)
  }
}
