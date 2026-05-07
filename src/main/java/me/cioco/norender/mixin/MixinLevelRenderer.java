package me.cioco.norender.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
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
        if (NoRenderCfg.noWeather) {
            ci.cancel();
        }
    }

    @Inject(method = "extractLevel", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/particle/ParticleEngine;extract(Lnet/minecraft/client/renderer/state/level/ParticlesRenderState;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/Camera;F)V"), cancellable = true)
    private void onExtractParticles(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci) {
        if (NoRenderCfg.noVaultParticles) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "addSkyPass",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/CameraEntityRenderState;doesMobEffectBlockSky:Z")
    )
    private boolean bypassSkyBlocking(CameraEntityRenderState state) {
        if (NoRenderCfg.noBlindness || NoRenderCfg.noDarkness) {
            return false;
        }
        return state.doesMobEffectBlockSky;
    }
}