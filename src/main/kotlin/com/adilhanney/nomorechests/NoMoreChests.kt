package com.adilhanney.nomorechests

import com.adilhanney.nomorechests.screen.ModScreenHandlers
import com.adilhanney.nomorechests.screen.custom.InfiniteInventoryScreen
import com.adilhanney.nomorechests.screen.custom.InfiniteInventoryScreenHandler
import net.fabricmc.api.ModInitializer
import net.minecraft.client.gui.screen.ingame.HandledScreens
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NoMoreChests : ModInitializer {
	const val modId = "nomorechests"
	val logger: Logger = LoggerFactory.getLogger(modId)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		HandledScreens.register(ModScreenHandlers.infiniteInventoryScreenHandler, ::InfiniteInventoryScreen)
	}
}
