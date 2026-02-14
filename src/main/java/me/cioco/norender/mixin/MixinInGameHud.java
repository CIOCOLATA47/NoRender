package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void handleSpyglassRender(DrawContext context, float scale, CallbackInfo ci) {
        if (NoRenderCfg.noSpyglassOverlay) ci.cancel();
    }

    @Inject(method = "renderPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void handlePortalRender(DrawContext context, float nauseaStrength, CallbackInfo ci) {
        if (NoRenderCfg.noPortalOverlay) ci.cancel();
    }

    @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
    private void handleVignetteRender(DrawContext context, net.minecraft.entity.Entity entity, CallbackInfo ci) {
        if (NoRenderCfg.noVignette) ci.cancel();
    }

    @Inject(method = "renderNauseaOverlay", at = @At("HEAD"), cancellable = true)
    private void handleNauseaRender(DrawContext context, float nauseaStrength, CallbackInfo ci) {
        if (NoRenderCfg.noNausea) ci.cancel();
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void handleStatusEffectRender(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (NoRenderCfg.noPotionIcons) ci.cancel();
    }

    @ModifyArgs(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private void silencePumpkinOverlay(Args args) {
        if (NoRenderCfg.noPumpkinOverlay) {
            args.set(2, 0.0f);
        }
    }

    @ModifyArgs(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    private void silencePowderedSnowOverlay(Args args) {
        if (NoRenderCfg.noPowderedSnowOverlay) {
            args.set(2, 0.0f);
        }
    }
}