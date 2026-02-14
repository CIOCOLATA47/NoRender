package me.cioco.norender.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice, CallbackInfo ci) {
        if (NoRenderCfg.noWeather) ci.cancel();
    }

    @Inject(method = "hasBlindnessOrDarkness", at = @At("HEAD"), cancellable = true)
    private void handleVisionEffects(Camera camera, CallbackInfoReturnable<Boolean> cir) {
        if (NoRenderCfg.noDarkness) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
