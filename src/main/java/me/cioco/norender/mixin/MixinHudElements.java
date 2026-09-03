package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class MixinHudElements {

    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    private void onCrosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noCrosshair) ci.cancel();
    }

    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void onHotbar(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noHotbar) ci.cancel();
    }

    @Inject(method = "extractChat", at = @At("HEAD"), cancellable = true)
    private void onChat(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noChat) ci.cancel();
    }

    @Inject(method = "extractTitle", at = @At("HEAD"), cancellable = true)
    private void onTitle(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noTitleAndActionbar) ci.cancel();
    }

    @Inject(method = "extractTabList", at = @At("HEAD"), cancellable = true)
    private void onPlayerList(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noPlayerList) ci.cancel();
    }

    @Inject(method = "extractOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void onAdvancementToast(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noAdvancementToast) ci.cancel();
    }
}