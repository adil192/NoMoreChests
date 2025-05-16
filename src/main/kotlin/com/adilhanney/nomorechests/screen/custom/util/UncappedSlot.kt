package com.adilhanney.nomorechests.screen.custom.util

import com.adilhanney.nomorechests.data.InfiniteChestInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class UncappedSlot(inventory: Inventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {
  override fun getMaxItemCount() = InfiniteChestInventory.MAX_STACK_SIZE
  override fun getMaxItemCount(stack: ItemStack) = if (stack.isStackable) InfiniteChestInventory.MAX_STACK_SIZE else 1
}
