@file:Suppress("UnstableApiUsage")

package com.adilhanney.nomorechests.data

import com.adilhanney.nomorechests.block.entity.InfiniteChestBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.RegistryByteBuf
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.network.ServerPlayerEntity
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent

abstract class InfiniteChestInventory(
  open val owner: PlayerEntity
) : C2SSelfMessagingComponent, AutoSyncedComponent {
  companion object {
    fun of(owner: PlayerEntity): InfiniteChestInventory = ModComponents.infiniteChestInventory.get(owner)
  }

  /** Used to determine [net.minecraft.block.entity.ViewerCountManager.isPlayerViewing] */
  var activeBlockEntity: InfiniteChestBlockEntity? = null
}

class InfiniteChestInventoryImpl(
  override val owner: PlayerEntity,
) : InfiniteChestInventory(owner) {
  companion object {
    private const val TABS_TAG = "tabs"
  }

  /**
   * A map of tab names to their contents, in the order they should be displayed.
   *
   * Consumers should write a sync packet (https://ladysnake.org/wiki/cardinal-components-api/synchronization)
   * if they modify this map.
   */
  private val tabData = MutableOrderedMap<String, InfiniteChestTab>()

  override fun readFromNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    val tabsNbt = nbt.getCompound(TABS_TAG)
    tabData.clear()
    for (tabName in tabsNbt.keys) {
      val tabNbt = tabsNbt.getCompound(tabName)
      val tab = InfiniteChestTab(this)
      tab.readFromNbt(tabNbt, registryLookup)
      tabData[tabName] = tab
    }
  }

  override fun writeToNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    val tabsNbt = NbtCompound()
    for (tabName in tabData.keys) {
      val tabNbt = NbtCompound()
      tabData[tabName]!!.writeToNbt(tabNbt, registryLookup)
      tabsNbt.put(tabName, tabNbt)
    }
    nbt.put(TABS_TAG, tabsNbt)
  }

  override fun handleC2SMessage(buf: RegistryByteBuf) {
    TODO("Not yet implemented")
  }

  /** Only sync inventory information with its owner */
  override fun shouldSyncWith(player: ServerPlayerEntity) = player == owner
}
