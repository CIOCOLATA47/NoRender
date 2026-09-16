package me.cioco.norender.mixin;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Inject(method = "addSkyPass", at = @At("HEAD"), cancellable = true)
    private void norender$onAddSkyPass(FrameGraphBuilder frame, CameraRenderState cameraState, GpuBufferSlice skyFog, CallbackInfo ci) {
        if (NoRenderCfg.noSky) {
            ci.cancel();
            return;
        }
        if ((NoRenderCfg.noBlindness || NoRenderCfg.noDarkness) && cameraState.entityRenderState.doesMobEffectBlockSky) {
            ci.cancel();
        }
    }
}