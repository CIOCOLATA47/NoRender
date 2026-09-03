package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinLevelRendererWorld {

    @Inject(method = "addSkyPass", at = @At("HEAD"), cancellable = true)
    private void onSky(CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noSky) {
            ci.cancel();
        }
    }

    @Inject(method = "addCloudsPass", at = @At("HEAD"), cancellable = true)
    private void onClouds(CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noClouds) {
            ci.cancel();
        }
    }

    @Inject(method = "addWeatherPass", at = @At("HEAD"), cancellable = true)
    private void onWeather(CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noWeather) {
            ci.cancel();
        }
    }
}