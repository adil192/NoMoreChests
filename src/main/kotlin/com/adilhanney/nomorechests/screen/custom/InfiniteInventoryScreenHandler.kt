package com.adilhanney.nomorechests.screen.custom

import com.adilhanney.nomorechests.data.InfiniteChestInventory
import com.adilhanney.nomorechests.screen.ModScreenHandlers
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot

class InfiniteInventoryScreenHandler(syncId: Int, val player: PlayerEntity) :
  ScreenHandler(ModScreenHandlers.infiniteInventoryScreenHandler, syncId) {
  val playerInventory = player.inventory!!
  val displayInventory = InfiniteInventoryScreen.DISPLAY_INVENTORY
  val infiniteInventory = InfiniteChestInventory.of(player)

  init {
    for (i in 0..CreativeInventoryScreen.ROWS_COUNT - 1) {
      for (j in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
        addSlot(Slot(displayInventory, i * 9 + j, 9 + j * 18, 18 + i * 18))
      }
    }

    for (i in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
      addSlot(Slot(playerInventory, i, 9 + i * 18, 112))
    }
  }

  override fun quickMove(player: PlayerEntity, slot: Int): ItemStack {
    if (slot < 0 || slot >= slots.size) return ItemStack.EMPTY

    TODO("Not yet implemented")

    if (slot < slots.size - CreativeInventoryScreen.COLUMNS_COUNT) {
      // Quick move from display inventory to player inventory
      return ItemStack.EMPTY
    } else {
      // Quick move from player inventory to display inventory
      return ItemStack.EMPTY
    }
  }

  override fun canUse(player: PlayerEntity): Boolean = player == this.player
}
