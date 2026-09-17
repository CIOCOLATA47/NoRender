package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.LavaFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFogEnvironment.class)
public class MixinLavaFogEnvironment {
    @Inject(method = "setupFog", at = @At("TAIL"))
    private void norender$noLavaFog(FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (NoRenderCfg.noFog) {
            fog.environmentalStart = 1.0E30F;
            fog.environmentalEnd   = 1.0E31F;
            fog.skyEnd             = 1.0E30F;
            fog.cloudEnd           = 1.0E30F;
        }
    }
}