package com.adilhanney.nomorechests.block.entity

import com.adilhanney.nomorechests.block.ModBlocks
import com.adilhanney.nomorechests.data.InfiniteChestInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.ChestLidAnimator
import net.minecraft.block.entity.LidOpenable
import net.minecraft.block.entity.ViewerCountManager
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class InfiniteChestBlockEntity(pos: BlockPos, state: BlockState) :
  BlockEntity(ModBlockEntityType.infiniteChest, pos, state),
  LidOpenable {
  private val lidAnimator = ChestLidAnimator()
  private val stateManager = object : ViewerCountManager() {
    protected override fun onContainerOpen(world: World, pos: BlockPos, state: BlockState) {
      world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f)
    }

    protected override fun onContainerClose(world: World, pos: BlockPos, state: BlockState) {
      world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f)
    }

    protected override fun onViewerCountUpdate(world: World, pos: BlockPos, state: BlockState, oldViewerCount: Int, newViewerCount: Int) {
      world.addSyncedBlockEvent(this@InfiniteChestBlockEntity.pos, ModBlocks.infiniteChest, 1, newViewerCount)
    }

    protected override fun isPlayerViewing(player: PlayerEntity) =
      InfiniteChestInventory.of(player).activeBlockEntity == this@InfiniteChestBlockEntity
  }

  companion object {
    fun clientTick(world: World, pos: BlockPos, state: BlockState, blockEntity: InfiniteChestBlockEntity) {
      blockEntity.lidAnimator.step()
    }
  }

  override fun onSyncedBlockEvent(type: Int, data: Int): Boolean {
    if (type == 1) {
      lidAnimator.setOpen(data > 0)
      return true
    }
    return super.onSyncedBlockEvent(type, data)
  }

  fun onOpen(player: PlayerEntity) {
    if (removed) return
    if (player.isSpectator) return
    stateManager.openContainer(player, world, pos, cachedState)
  }

  fun onClose(player: PlayerEntity) {
    if (removed) return
    if (player.isSpectator) return
    stateManager.closeContainer(player, world, pos, cachedState)
  }

  override fun getAnimationProgress(tickDelta: Float) = lidAnimator.getProgress(tickDelta)
}
