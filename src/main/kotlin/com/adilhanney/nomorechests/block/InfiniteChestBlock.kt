package com.adilhanney.nomorechests.block

import com.adilhanney.nomorechests.block.entity.InfiniteChestBlockEntity
import com.adilhanney.nomorechests.block.entity.ModBlockEntityType
import com.adilhanney.nomorechests.data.InfiniteChestInventory
import com.mojang.serialization.MapCodec
import net.minecraft.block.*
import net.minecraft.block.DoubleBlockProperties.PropertySource
import net.minecraft.block.entity.*
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess


class InfiniteChestBlock(settings: Settings) :
  AbstractChestBlock<InfiniteChestBlockEntity>(
    settings,
    { ModBlockEntityType.infiniteChest }
  ), Waterloggable {
  companion object {
    val codec = createCodec(::InfiniteChestBlock)!!
    val facing = HorizontalFacingBlock.FACING!!
    val waterlogged = Properties.WATERLOGGED!!
    protected val shape = createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0)!!
    // TODO: Add text
    private val containerName = Text.translatable("nomorechests.container.infinite_chest")
  }

  override fun getCodec(): MapCodec<InfiniteChestBlock> = InfiniteChestBlock.codec

  init {
    defaultState = stateManager.defaultState
      .with(facing, Direction.NORTH)
      .with(waterlogged, false)
  }

  override fun getBlockEntitySource(state: BlockState, world: World, pos: BlockPos, ignoreBlocked: Boolean): PropertySource<ChestBlockEntity?> {
    return object : PropertySource<ChestBlockEntity?> {
      override fun <T> apply(retriever: DoubleBlockProperties.PropertyRetriever<in ChestBlockEntity?, T>) = retriever.fallback
    }
  }

  protected override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

  protected override fun getRenderType(state: BlockState) = BlockRenderType.ENTITYBLOCK_ANIMATED

  override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
    val fluidState = ctx.world.getFluidState(ctx.blockPos)
    return defaultState
      .with(facing, ctx.horizontalPlayerFacing.opposite)
      .with(waterlogged, fluidState.isOf(Fluids.WATER))
  }

  protected override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hit: BlockHitResult): ActionResult {
    val blockEntity = world.getBlockEntity(pos)
    if (blockEntity is InfiniteChestBlockEntity) {
      val blockAbovePos = pos.up()
      if (world.getBlockState(blockAbovePos).isSolidBlock(world, blockAbovePos)) {
        // Chest is blocked by a solid block above it
        return ActionResult.success(world.isClient)
      } else if (world.isClient) {
        return ActionResult.success(true)
      } else {
        val infiniteChestInventory = InfiniteChestInventory.of(player)
        infiniteChestInventory.activeBlockEntity = blockEntity
//        player.openHandledScreen() // TODO: Create UI
        return ActionResult.CONSUME;
      }
    } else {
      return ActionResult.success(world.isClient)
    }
  }

  override fun createBlockEntity(pos: BlockPos, state: BlockState) = InfiniteChestBlockEntity(pos, state)

  override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? =
    if (world.isClient) validateTicker(type, ModBlockEntityType.infiniteChest, InfiniteChestBlockEntity::clientTick)
    else null

  protected override fun rotate(state: BlockState, rotation: BlockRotation) = state.with(facing, rotation.rotate(state.get(facing)))!!

  protected override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state.get(facing)))!!

  protected override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) { builder.add(facing, waterlogged) }

  protected override fun getFluidState(state: BlockState): FluidState =
    if (state.get(waterlogged)) Fluids.WATER.getStill(false)
    else super.getFluidState(state)

  protected override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
    if (state.get(waterlogged)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
  }

  protected override fun canPathfindThrough(state: BlockState, type: NavigationType) = false
}
