package com.adilhanney.nomorechests.screen.custom

import com.adilhanney.nomorechests.screen.ModScreenHandlers
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler

class InfiniteInventoryScreenHandler(syncId: Int, val player: PlayerEntity) :
  ScreenHandler(ModScreenHandlers.infiniteInventoryScreenHandler, syncId) {
  init {
    // TODO: Add slots
  }

  override fun quickMove(player: PlayerEntity, slot: Int): ItemStack? {
    TODO("Not yet implemented")
  }

  override fun canUse(player: PlayerEntity): Boolean = player == this.player
}
