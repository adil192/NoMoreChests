package com.adilhanney.nomorechests.mixin;

import com.adilhanney.nomorechests.NoMoreChests;
import com.adilhanney.nomorechests.block.entity.InfiniteChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.client.render.TexturedRenderLayers.*;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {
  // TODO: Create this chest atlas texture
  @Unique
  private static final SpriteIdentifier INFINITE_CHEST = new SpriteIdentifier(CHEST_ATLAS_TEXTURE,
      Identifier.of(NoMoreChests.modId, "entity/chest/infinite_chest"));

  @Inject(at = @At("HEAD"), method = "getChestTextureId(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", cancellable = true)
  private static void modifyChestTextureId(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> cir) {
    if (blockEntity instanceof InfiniteChestBlockEntity) {
      cir.setReturnValue(INFINITE_CHEST);
    }
  }
}
