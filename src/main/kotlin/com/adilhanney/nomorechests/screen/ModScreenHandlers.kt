package com.adilhanney.nomorechests.screen

import com.adilhanney.nomorechests.NoMoreChests
import com.adilhanney.nomorechests.screen.custom.InfiniteInventoryScreenHandler
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureSet
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object ModScreenHandlers {
  val infiniteInventoryScreenHandler = Registry.register(
    Registries.SCREEN_HANDLER,
    Identifier.of(NoMoreChests.modId, "infinite_inventory_screen_handler"),
    ScreenHandlerType(
      { syncId, playerInventory -> InfiniteInventoryScreenHandler(syncId, playerInventory.player) },
      FeatureSet.empty(),
    )
  )!!
}
