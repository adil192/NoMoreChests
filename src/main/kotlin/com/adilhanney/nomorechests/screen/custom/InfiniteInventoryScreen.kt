package com.adilhanney.nomorechests.screen.custom

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.client.gui.screen.ingame.InventoryScreen
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.text.Text

class InfiniteInventoryScreen(
  handler: InfiniteInventoryScreenHandler,
  playerInventory: PlayerInventory,
  title: Text,
) : AbstractInventoryScreen<InfiniteInventoryScreenHandler>(
    handler,
    playerInventory,
    title,
  ) {
  companion object {
    val TITLE = Text.translatable("nomorechests.container.infinite_chest")!!

    private var selectedTab = ItemGroups.getDefaultTab()!!
  }

  private val player = playerInventory.player as ClientPlayerEntity
  private val creativeInventoryScreen = CreativeInventoryScreen(player,
    player.networkHandler?.enabledFeatures, true)

  init {
    player.currentScreenHandler = handler
    backgroundHeight = 136
    backgroundWidth = 195
  }

  protected override fun init() {
    creativeInventoryScreen.init(client, width, height)
    super.init()
  }

  protected override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
    for (itemGroup in getGroupsToDisplay()) {
      if (itemGroup != selectedTab) {
        creativeInventoryScreen.renderTabIcon(context, itemGroup)
      }
    }

    context.drawTexture(selectedTab.texture, x, y, 0, 0, backgroundWidth, backgroundHeight)

    // TODO: Draw scrollbar

    creativeInventoryScreen.renderTabIcon(context, selectedTab)
    if (selectedTab.type == ItemGroup.Type.INVENTORY) {
      InventoryScreen.drawEntity(context, x + 73, y + 6, x + 105, y + 49, 20, 0.0625f,
        mouseX.toFloat(), mouseY.toFloat(), player)
    }
  }

  private fun getGroupsToDisplay(): List<ItemGroup> {
    val groups = ItemGroups.getGroupsToDisplay().toMutableList()

    if (!player.isCreativeLevelTwoOp) {
      // TODO: Only remove op tab if nothing in it
      val operator = Registries.ITEM_GROUP.get(ItemGroups.OPERATOR)
      groups.remove(operator)
    }

    return groups
  }



}
