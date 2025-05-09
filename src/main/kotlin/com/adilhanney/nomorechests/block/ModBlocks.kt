package com.adilhanney.nomorechests.block

import com.adilhanney.nomorechests.NoMoreChests
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.enums.NoteBlockInstrument
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModBlocks {
  val infiniteChest = register(
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

  private fun <CustomBlock: Block> register(idPath: String, block: CustomBlock): CustomBlock {
    val id = Identifier.of(NoMoreChests.modId, idPath)
    return Registry.register(Registries.BLOCK, id, block)
  }
}
