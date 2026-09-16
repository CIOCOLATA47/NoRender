package me.cioco.norender.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public abstract class MixinInGameOverlayRenderer {

    @Inject(method = "submitFire", at = @At("HEAD"), cancellable = true)
    private static void onRenderFire(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, TextureAtlasSprite sprite, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noFireOverlay) ci.cancel();
    }

    @Inject(method = "submitWater", at = @At("HEAD"), cancellable = true)
    private static void onRenderLiquid(PlayerRenderState.WaterOverlay waterOverlay, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noLiquidOverlay) ci.cancel();
    }

    @Inject(method = "submitBlockSprite", at = @At("HEAD"), cancellable = true)
    private static void noInWallOverlay(Identifier atlasLocation, float u0, float v0, float u1, float v1, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int color, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noInWallOverlay) {
            ci.cancel();
        }
    }

    @Inject(method = "renderItemActivationAnimation", at = @At("HEAD"), cancellable = true)
    private void noTotemAnimation(PlayerRenderState playerRenderState, PoseStack poseStack, float partialTicks, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noTotemAnimation) {
            ci.cancel();
        }
    }
}