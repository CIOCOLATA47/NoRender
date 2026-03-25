package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinInGameHud {

    @Inject(method = "extractSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void handleSpyglassRender(GuiGraphicsExtractor graphics, float scale, CallbackInfo ci) {
        if (NoRenderCfg.noSpyglassOverlay) ci.cancel();
    }

    @Inject(method = "extractPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void handlePortalRender(GuiGraphicsExtractor graphics, float alpha, CallbackInfo ci) {
        if (NoRenderCfg.noPortalOverlay) ci.cancel();
    }

    @Inject(method = "extractVignette", at = @At("HEAD"), cancellable = true)
    private void handleVignetteRender(GuiGraphicsExtractor graphics, Entity camera, CallbackInfo ci) {
        if (NoRenderCfg.noVignette) ci.cancel();
    }

    @Inject(method = "extractConfusionOverlay", at = @At("HEAD"), cancellable = true)
    private void handleNauseaRender(GuiGraphicsExtractor graphics, float strength, CallbackInfo ci) {
        if (NoRenderCfg.noNausea) ci.cancel();
    }

    @Inject(method = "extractScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    private void onRenderScoreboardSidebar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (NoRenderCfg.noScoreboard) ci.cancel();
    }

    @Inject(method = "extractBossOverlay", at = @At("HEAD"), cancellable = true)
    private void handleBossBarRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (NoRenderCfg.noBossBar) ci.cancel();
    }

    @Inject(method = "extractEffects", at = @At("HEAD"), cancellable = true)
    private void handleStatusEffectRender(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (NoRenderCfg.noPotionIcons || NoRenderCfg.noOmenEffect) ci.cancel();
    }

    @Inject(method = "extractTextureOverlay", at = @At("HEAD"), cancellable = true)
    private void handleTextureOverlay(GuiGraphicsExtractor extractor, Identifier texture, float alpha, CallbackInfo ci) {
        String path = texture.getPath();
        if (NoRenderCfg.noPumpkinOverlay && path.contains("pumpkin")) ci.cancel();
        if (NoRenderCfg.noPowderedSnowOverlay && path.contains("powder_snow")) ci.cancel();
    }
}