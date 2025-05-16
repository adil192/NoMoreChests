@file:Suppress("UnstableApiUsage")

package com.adilhanney.nomorechests.data

import com.adilhanney.nomorechests.block.entity.InfiniteChestBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.network.RegistryByteBuf
import net.minecraft.recipe.RecipeInputProvider
import net.minecraft.recipe.RecipeMatcher
import net.minecraft.registry.RegistryWrapper
import net.minecraft.server.network.ServerPlayerEntity
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent
import org.ladysnake.cca.api.v3.entity.C2SSelfMessagingComponent

class InfiniteChestInventory(
  val owner: PlayerEntity,
) : C2SSelfMessagingComponent, AutoSyncedComponent, Inventory, RecipeInputProvider {
  companion object {
    fun of(owner: PlayerEntity): InfiniteChestInventory = ModComponents.infiniteChestInventory.get(owner)

    const val MAX_STACK_SIZE = 999999
    private const val STACKS_TAG = "stacks"
  }

  /** Used to determine [net.minecraft.block.entity.ViewerCountManager.isPlayerViewing] */
  var activeBlockEntity: InfiniteChestBlockEntity? = null

  private val stacks = mutableListOf<ItemStack>()
  private var listeners: MutableList<InventoryChangedListener>? = null

  override fun readFromNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    val stacksNbt = nbt.getList(STACKS_TAG, NbtElement.COMPOUND_TYPE.toInt())
    stacks.clear()
    for (i in stacksNbt.indices) {
      val stackNbt = stacksNbt.getCompound(i)
      val stack = ItemStack.fromNbtOrEmpty(registryLookup, stackNbt)
      stacks.add(stack)
    }
  }

  override fun writeToNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    val stacksNbt = NbtList()
    for (stack in stacks) {
      if (stack.isEmpty) continue
      stacksNbt.add(stack.encode(registryLookup))
    }
    nbt.put(STACKS_TAG, stacksNbt)
  }

  override fun handleC2SMessage(buf: RegistryByteBuf) {
    // TODO("Not yet implemented")
  }

  /** Only sync inventory information with its owner */
  override fun shouldSyncWith(player: ServerPlayerEntity) = player == owner

  override fun size() = stacks.size
  override fun isEmpty() = stacks.isEmpty()
  override fun canPlayerUse(player: PlayerEntity) = owner == player
  override fun getMaxCountPerStack() = MAX_STACK_SIZE
  override fun getMaxCount(stack: ItemStack) = if (stack.maxCount == 1) 1 else MAX_STACK_SIZE
  override fun getStack(slot: Int): ItemStack = stacks.getOrElse(slot) { ItemStack.EMPTY }

  override fun removeStack(slot: Int, amount: Int): ItemStack {
    val stackOfRemovedItems = Inventories.splitStack(stacks, slot, amount)
    if (!stackOfRemovedItems.isEmpty) markDirty()
    return stackOfRemovedItems
  }

  override fun removeStack(slot: Int): ItemStack? {
    val removedStack = getStack(slot)
    if (removedStack.isEmpty) return ItemStack.EMPTY

    stacks[slot] = ItemStack.EMPTY
    markDirty()
    return removedStack
  }

  override fun setStack(slot: Int, stack: ItemStack) {
    if (slot == stacks.size) stacks.add(stack)
    else stacks[slot] = stack
    markDirty()
  }

  override fun clear() {
    for (i in stacks.indices) stacks[i] = ItemStack.EMPTY
    markDirty()
  }

  override fun provideRecipeInputs(finder: RecipeMatcher) {
    for (stack in stacks) {
      if (stack.isEmpty) continue
      finder.addInput(stack)
    }
  }

  override fun onOpen(player: PlayerEntity) {
    activeBlockEntity?.onOpen(player)
    super.onOpen(player)
  }

  override fun onClose(player: PlayerEntity) {
    activeBlockEntity?.onClose(player)
    activeBlockEntity = null
    super.onClose(player)
  }

  fun addListener(listener: InventoryChangedListener) {
    if (listeners == null) listeners = mutableListOf()
    listeners!!.add(listener)
  }
  fun removeListener(listener: InventoryChangedListener) {
    listeners?.remove(listener)
  }
  override fun markDirty() {
    listeners?.forEach { it.onInventoryChanged(this) }
  }
}
