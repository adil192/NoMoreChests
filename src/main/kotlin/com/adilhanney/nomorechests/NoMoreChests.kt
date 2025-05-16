package com.adilhanney.nomorechests

import com.adilhanney.nomorechests.block.ModBlocks
import com.adilhanney.nomorechests.block.entity.ModBlockEntityType
import com.adilhanney.nomorechests.screen.ModScreenHandlers
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NoMoreChests : ModInitializer {
	const val modId = "nomorechests"
	val logger: Logger = LoggerFactory.getLogger(modId)

	override fun onInitialize() {
		ModBlocks.registerModBlocks()
		ModBlockEntityType.registerBlockEntities()
		ModScreenHandlers.init()
	}
}
