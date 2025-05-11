package com.adilhanney.nomorechests

import com.adilhanney.nomorechests.datagen.ModBlockTagProvider
import com.adilhanney.nomorechests.datagen.ModLootTableProvider
import com.adilhanney.nomorechests.datagen.ModModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object NoMoreChestsDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::ModBlockTagProvider)
		pack.addProvider(::ModLootTableProvider)
		pack.addProvider(::ModModelProvider)
	}
}
