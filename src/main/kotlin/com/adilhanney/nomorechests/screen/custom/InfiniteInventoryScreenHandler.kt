package com.adilhanney.nomorechests.screen.custom

import com.adilhanney.nomorechests.screen.ModScreenHandlers
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class InfiniteInventoryScreenHandler(syncId: Int, val player: PlayerEntity) :
  ScreenHandler(ModScreenHandlers.infiniteInventoryScreenHandler, syncId) {
  init {
    val playerInventory = player.inventory

    for (i in 0..CreativeInventoryScreen.ROWS_COUNT - 1) {
      for (j in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
        addSlot(Slot(InfiniteInventoryScreen.DISPLAY_INVENTORY, i * 9 + j, 9 + j * 18, 18 + i * 18))
      }
    }

    for (i in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
      addSlot(Slot(playerInventory, i, 9 + i * 18, 112))
    }
  }

  override fun quickMove(player: PlayerEntity, slot: Int): ItemStack? {
    TODO("Not yet implemented")
  }

  override fun canUse(player: PlayerEntity): Boolean = player == this.player
}
