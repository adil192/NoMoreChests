package com.adilhanney.nomorechests.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.RegistryWrapper
import org.ladysnake.cca.api.v3.component.Component

class InfiniteChestTab(
  val parent: InfiniteChestInventory,
) : Component {
  override fun readFromNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    TODO("Not yet implemented")
  }

  override fun writeToNbt(tag: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
    TODO("Not yet implemented")
  }
}
