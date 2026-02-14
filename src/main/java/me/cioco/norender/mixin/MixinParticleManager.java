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
        var type = parameters.getType();

        if (NoRenderCfg.noTrialSpawnerDetection && type == ParticleTypes.TRIAL_SPAWNER_DETECTION) cir.setReturnValue(null);
        if (NoRenderCfg.noOminousSpawning && type == ParticleTypes.OMINOUS_SPAWNING) cir.setReturnValue(null);
        if (NoRenderCfg.noTrialSpawnerFlame && type == ParticleTypes.TRIAL_SPAWNER_DETECTION_OMINOUS) cir.setReturnValue(null);
        if (NoRenderCfg.noWindExplosion && (type == ParticleTypes.GUST || type == ParticleTypes.GUST_EMITTER_LARGE || type == ParticleTypes.GUST_EMITTER_SMALL)) cir.setReturnValue(null);
        if (NoRenderCfg.noInfestedParticles && type == ParticleTypes.INFESTED) cir.setReturnValue(null);
        if (NoRenderCfg.noCobwebParticles && type == ParticleTypes.SMALL_FLAME) cir.setReturnValue(null);
        if (NoRenderCfg.noExplosions && (type == ParticleTypes.EXPLOSION || type == ParticleTypes.EXPLOSION_EMITTER)) cir.setReturnValue(null);
        if (NoRenderCfg.noFireworks && (type == ParticleTypes.FIREWORK || type == ParticleTypes.FLASH)) cir.setReturnValue(null);
        if (NoRenderCfg.noCampfireSmoke && (type == ParticleTypes.CAMPFIRE_COSY_SMOKE || type == ParticleTypes.CAMPFIRE_SIGNAL_SMOKE)) cir.setReturnValue(null);
        if (NoRenderCfg.noHeartParticles && type == ParticleTypes.HEART) cir.setReturnValue(null);
        if (NoRenderCfg.noTotemParticles && type == ParticleTypes.TOTEM_OF_UNDYING) cir.setReturnValue(null);
        if (NoRenderCfg.noPotionParticles && (type == ParticleTypes.ENTITY_EFFECT || type == ParticleTypes.INSTANT_EFFECT)) cir.setReturnValue(null);
        if (NoRenderCfg.noDamageParticles && type == ParticleTypes.DAMAGE_INDICATOR) cir.setReturnValue(null);
        if (NoRenderCfg.noSweepParticles && type == ParticleTypes.SWEEP_ATTACK) cir.setReturnValue(null);
        if (NoRenderCfg.noCritParticles && (type == ParticleTypes.CRIT || type == ParticleTypes.ENCHANTED_HIT)) cir.setReturnValue(null);
        if (NoRenderCfg.noFlameParticles && (type == ParticleTypes.FLAME || type == ParticleTypes.SOUL_FIRE_FLAME)) cir.setReturnValue(null);
        if (NoRenderCfg.noSmokeParticles && (type == ParticleTypes.SMOKE || type == ParticleTypes.LARGE_SMOKE)) cir.setReturnValue(null);
        if (NoRenderCfg.noBubbleParticles && (type == ParticleTypes.BUBBLE || type == ParticleTypes.BUBBLE_COLUMN_UP)) cir.setReturnValue(null);
        if (NoRenderCfg.noCloudParticles && type == ParticleTypes.CLOUD) cir.setReturnValue(null);
        if (NoRenderCfg.noAsh && (type == ParticleTypes.ASH || type == ParticleTypes.WHITE_ASH)) cir.setReturnValue(null);
        if (NoRenderCfg.noSoulParticles && (type == ParticleTypes.SOUL || type == ParticleTypes.SOUL_FIRE_FLAME)) cir.setReturnValue(null);
        if (NoRenderCfg.noDragonBreath && type == ParticleTypes.DRAGON_BREATH) cir.setReturnValue(null);
        if (NoRenderCfg.noDripParticles && (type == ParticleTypes.DRIPPING_WATER || type == ParticleTypes.DRIPPING_LAVA || type == ParticleTypes.DRIPPING_DRIPSTONE_WATER || type == ParticleTypes.DRIPPING_DRIPSTONE_LAVA)) cir.setReturnValue(null);
        if (NoRenderCfg.noFallingDust && type == ParticleTypes.FALLING_DUST) cir.setReturnValue(null);
        if (NoRenderCfg.noSonicBoom && type == ParticleTypes.SONIC_BOOM) cir.setReturnValue(null);
        if (NoRenderCfg.noVibration && type == ParticleTypes.VIBRATION) cir.setReturnValue(null);
        if (NoRenderCfg.noSculkCharge && type == ParticleTypes.SCULK_CHARGE_POP) cir.setReturnValue(null);
        if (NoRenderCfg.noShriekParticle && type == ParticleTypes.SHRIEK) cir.setReturnValue(null);
        if (NoRenderCfg.noWeather && type == ParticleTypes.RAIN) cir.setReturnValue(null);
    }
}