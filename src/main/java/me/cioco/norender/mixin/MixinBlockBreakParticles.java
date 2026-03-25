package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.TerrainParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class MixinBlockBreakParticles {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void cancelTerrainParticles(Particle p, CallbackInfo ci) {
        if (NoRenderCfg.noBlockBreakParticles && p instanceof TerrainParticle) {
            ci.cancel();
        }
    }
}