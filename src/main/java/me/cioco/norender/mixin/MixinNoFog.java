package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class MixinNoFog {

    @Inject(method = "updateBuffer", at = @At("HEAD"))
    private void norender$killFog(FogData fog, CallbackInfo ci) {
        if (!NoRenderCfg.noFog) return;

        fog.environmentalStart  = 1.0E30F;
        fog.environmentalEnd    = 1.0E31F;
        fog.renderDistanceStart = 1.0E30F;
        fog.renderDistanceEnd   = 1.0E31F;
        fog.skyEnd              = 1.0E30F;
        fog.cloudEnd            = 1.0E30F;
    }
}