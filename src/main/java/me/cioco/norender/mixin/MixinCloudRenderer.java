package me.cioco.norender.mixin;

import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.oit.OitRenderPassProvider;
import net.minecraft.client.renderer.oit.OitStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CloudRenderer.class)
public abstract class MixinCloudRenderer {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void norender$onRender(CloudStatus cloudStatus, RenderPass renderPass, CallbackInfo ci) {
        if (NoRenderCfg.noClouds) ci.cancel();
    }

    @Inject(method = "renderOit", at = @At("HEAD"), cancellable = true)
    private void norender$onRenderOit(CloudStatus cloudStatus, OitStage stage, GpuTextureView mainDepthTextureView, OitRenderPassProvider.Parameters params, CallbackInfo ci) {
        if (NoRenderCfg.noClouds) ci.cancel();
    }
}