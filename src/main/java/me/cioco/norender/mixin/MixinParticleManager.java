package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class MixinParticleManager {

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {

        if (NoRenderCfg.noExplosions && (parameters == ParticleTypes.EXPLOSION || parameters == ParticleTypes.EXPLOSION_EMITTER)) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noFireworks && (parameters == ParticleTypes.FIREWORK || parameters == ParticleTypes.FLASH)) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noCampfireSmoke && (parameters == ParticleTypes.CAMPFIRE_COSY_SMOKE || parameters == ParticleTypes.CAMPFIRE_SIGNAL_SMOKE)) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noHeartParticles && parameters == ParticleTypes.HEART) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noTotemParticles && parameters == ParticleTypes.TOTEM_OF_UNDYING) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noPotionParticles && (parameters == ParticleTypes.ENTITY_EFFECT || parameters == ParticleTypes.INSTANT_EFFECT)) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noSonicBoom && parameters == ParticleTypes.SONIC_BOOM) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noVibration && (parameters == ParticleTypes.VIBRATION || parameters == ParticleTypes.SCULK_CHARGE)) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noDamageParticles && parameters == ParticleTypes.DAMAGE_INDICATOR) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noSweepParticles && parameters == ParticleTypes.SWEEP_ATTACK) {
            cir.setReturnValue(null);
        }

        if (NoRenderCfg.noFallingDust && parameters == ParticleTypes.FALLING_DUST) {
            cir.setReturnValue(null);
        }
    }
}