package com.adilhanney.nomorechests.screen.custom

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen
import net.minecraft.client.gui.screen.ingame.InventoryScreen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
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

    /**
     * A fake inventory for the displayed items, not to be confused with:
     * - The player inventory (aka the survival inventory)
     * - The infinite chest inventory
     */
    val DISPLAY_INVENTORY = SimpleInventory(CreativeInventoryScreen.ROWS_COUNT * CreativeInventoryScreen.COLUMNS_COUNT)

    private var selectedTab: ItemGroup
      get() = CreativeInventoryScreen.selectedTab
      set(value) { CreativeInventoryScreen.selectedTab = value }
  }

  private val player = playerInventory.player as ClientPlayerEntity
  private val creativeInventoryScreen = CustomCreativeInventoryScreen(player)

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

  protected override fun drawForeground(context: DrawContext?, mouseX: Int, mouseY: Int) {
    if (selectedTab.shouldRenderName()) {
      context!!.drawText(textRenderer, selectedTab.displayName, 8, 6, 4210752, false)
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

  @Environment(EnvType.CLIENT)
  private class CustomCreativeInventoryScreen(val player: ClientPlayerEntity) :
    CreativeInventoryScreen(player, player.networkHandler.enabledFeatures, true) {
    // Method overridden since it replaces the screen when in survival mode
    override fun init() {
      x = (width - backgroundWidth) / 2
      y = (height - backgroundHeight) / 2
      searchBox = TextFieldWidget(textRenderer, x + 82, y + 6, 80, 9, Text.translatable("itemGroup.search"))
      setSelectedTab(selectedTab)
    }
    // Method overridden since it replaces the screen when in survival mode
    override fun handledScreenTick() {}
  }
}
