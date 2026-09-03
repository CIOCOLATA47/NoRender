package me.cioco.norender.mixin;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Inject(method = "addWeatherPass", at = @At("HEAD"), cancellable = true)
    private void onAddWeatherPass(FrameGraphBuilder frame, GpuBufferSlice fog, CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noWeather) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "addSkyPass",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/CameraEntityRenderState;doesMobEffectBlockSky:Z")
    )
    private boolean bypassSkyBlocking(CameraEntityRenderState state) {
        if (!NoRenderUtil.shouldCancel()) {
            return state.doesMobEffectBlockSky;
        }
        if (NoRenderCfg.noBlindness || NoRenderCfg.noDarkness) {
            return false;
        }
        return state.doesMobEffectBlockSky;
    }
}