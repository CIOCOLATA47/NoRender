package me.cioco.norender.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.blockentity.state.ConduitRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConduitRenderer.class)
public class NoConduitEyeMixin {

    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void cancelConduitEye(ConduitRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
        if (NoRenderCfg.noConduitEye) {
            ci.cancel();
        }
    }
}