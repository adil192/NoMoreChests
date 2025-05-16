package com.adilhanney.nomorechests.screen.custom

import com.adilhanney.nomorechests.data.InfiniteChestInventory
import com.adilhanney.nomorechests.screen.ModScreenHandlers
import com.adilhanney.nomorechests.screen.custom.util.UncappedSlot
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler

@Environment(EnvType.CLIENT)
class InfiniteInventoryScreenHandler(syncId: Int, val player: PlayerEntity) :
  ScreenHandler(ModScreenHandlers.infiniteInventoryScreenHandler, syncId) {
  val playerInventory = player.inventory!!
  val displayInventory = InfiniteInventoryScreen.DISPLAY_INVENTORY
  val infiniteInventory = InfiniteChestInventory.of(player)

  init {
    for (i in 0..CreativeInventoryScreen.ROWS_COUNT - 1) {
      for (j in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
        addSlot(UncappedSlot(displayInventory, i * 9 + j, 9 + j * 18, 18 + i * 18))
      }
    }

    for (i in 0..CreativeInventoryScreen.COLUMNS_COUNT - 1) {
      addSlot(UncappedSlot(playerInventory, i, 9 + i * 18, 112))
    }
  }

  override fun quickMove(player: PlayerEntity, slotIndex: Int): ItemStack {
    if (slotIndex < 0 || slotIndex >= slots.size) return ItemStack.EMPTY

    val slot = slots[slotIndex]
    val stack = slot.stack
    if (!stack.isEmpty) return ItemStack.EMPTY
    val originalStack = stack.copy()

    if (slotIndex < displayInventory.size()) {
      // TODO: Override insertItem to only take one stack (e.g. 64) max so we don't fill up the player's inventory
      if (!insertItem(stack, displayInventory.size(), slots.size, true)) return ItemStack.EMPTY
    } else {
      // TODO: Override insertItem to update infiniteInventory as well
      if (!insertItem(stack, 0, displayInventory.size(), false)) return ItemStack.EMPTY
    }

    slot.markDirty()
    return originalStack
  }

  override fun canUse(player: PlayerEntity): Boolean = player == this.player
}
