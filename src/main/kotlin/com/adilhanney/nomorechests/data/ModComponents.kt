package com.adilhanney.nomorechests.data

import com.adilhanney.nomorechests.NoMoreChests
import net.minecraft.util.Identifier
import org.ladysnake.cca.api.v3.component.ComponentKey
import org.ladysnake.cca.api.v3.component.ComponentRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy

class ModComponents : EntityComponentInitializer {
  companion object {
    val infiniteChestInventory: ComponentKey<InfiniteChestInventory> = ComponentRegistry.getOrCreate(
      Identifier.of(NoMoreChests.modId, "infinitechestinventory"),
      InfiniteChestInventory::class.java,
    )
  }

  override fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
    registry.registerForPlayers(infiniteChestInventory, ::InfiniteChestInventory, RespawnCopyStrategy.ALWAYS_COPY)
  }
}
