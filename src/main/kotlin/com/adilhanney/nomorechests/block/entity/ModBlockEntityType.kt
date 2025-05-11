package com.adilhanney.nomorechests.block.entity

import com.adilhanney.nomorechests.NoMoreChests
import com.adilhanney.nomorechests.block.ModBlocks
import com.mojang.datafixers.types.Type
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Util

class ModBlockEntityType<T : BlockEntity>(
  factory: BlockEntityFactory<out T>,
  blocks: MutableSet<Block>,
  type: Type<*>
) : BlockEntityType<T>(factory, blocks, type) {
  companion object {
    val infiniteChest = create("infinite_chest", Builder.create(::InfiniteChestBlockEntity, ModBlocks.infiniteChest))

    private fun <T : BlockEntity> create(id: String, builder: Builder<T>): BlockEntityType<T> {
      val type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id)
      return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build(type))
    }

    fun registerBlockEntities() {
      NoMoreChests.logger.info("Registering mod block entities for ${NoMoreChests.modId}")
    }
  }
}
