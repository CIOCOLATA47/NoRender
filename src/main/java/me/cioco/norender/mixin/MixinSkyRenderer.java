package me.cioco.norender.mixin;

import com.mojang.renderpearl.api.commands.RenderPass;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer {

    @Inject(method = "renderDarkDisc", at = @At("HEAD"), cancellable = true)
    private void norender$noDarkDisc(RenderPass renderPass, CallbackInfo ci) {
        if (NoRenderCfg.noFog) ci.cancel();
    }
}