package com.adilhanney.nomorechests.block

import com.adilhanney.nomorechests.NoMoreChests
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModBlocks {
  val infiniteChest = registerBlock(
    "infinite_chest",
    InfiniteChestBlock(
      AbstractBlock.Settings.create()
        // TODO: Change map color when textures are added
        .mapColor(MapColor.STONE_GRAY)
        .instrument(NoteBlockInstrument.BASEDRUM)
        .strength(22.5f, 600f)
        .luminance { _ -> 7 }
    )
  )

  private fun <CustomBlock: Block> registerBlock(idPath: String, block: CustomBlock): CustomBlock {
    val id = Identifier.of(NoMoreChests.modId, idPath)
    registerBlockItem(id, block)
    return Registry.register(Registries.BLOCK, id, block)
  }
  private fun registerBlockItem(id: Identifier, block: Block) {
    Registry.register(Registries.ITEM, id, BlockItem(block, Item.Settings()))
  }

  fun registerModBlocks() {
    NoMoreChests.logger.info("Registering mod blocks for ${NoMoreChests.modId}")
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register { entries -> {
      entries.add(infiniteChest)
    } }
  }
}
