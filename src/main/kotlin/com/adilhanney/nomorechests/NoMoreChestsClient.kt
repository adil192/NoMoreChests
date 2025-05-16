package com.adilhanney.nomorechests

import com.adilhanney.nomorechests.block.entity.ModBlockEntityType
import com.adilhanney.nomorechests.screen.ModScreenHandlers
import com.adilhanney.nomorechests.screen.custom.InfiniteInventoryScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer

@Environment(EnvType.CLIENT)
object NoMoreChestsClient : ClientModInitializer {
	override fun onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntityType.infiniteChest, ::ChestBlockEntityRenderer)
		HandledScreens.register(ModScreenHandlers.infiniteInventoryScreenHandler, ::InfiniteInventoryScreen)
	}
}
