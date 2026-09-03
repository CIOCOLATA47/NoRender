package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class MixinParticleManager {

    @Inject(method = "createParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleOptions options, double x, double y, double z, double xa, double ya, double za, CallbackInfoReturnable<Particle> cir) {
        if (!NoRenderUtil.isGameReady()) return;

        var type = options.getType();

        if (NoRenderCfg.noPortalOverlay && type == ParticleTypes.PORTAL) cir.setReturnValue(null);
        if (NoRenderCfg.noWeather && type == ParticleTypes.RAIN) cir.setReturnValue(null);
        if (NoRenderCfg.noWeather && type == ParticleTypes.SPLASH) cir.setReturnValue(null);
        if (NoRenderCfg.noExplosions && (type == ParticleTypes.EXPLOSION || type == ParticleTypes.EXPLOSION_EMITTER || type == ParticleTypes.POOF)) cir.setReturnValue(null);
        if (NoRenderCfg.noFireworks && (type == ParticleTypes.FIREWORK || type == ParticleTypes.FLASH)) cir.setReturnValue(null);
        if (NoRenderCfg.noCampfireSmoke && (type == ParticleTypes.CAMPFIRE_COSY_SMOKE || type == ParticleTypes.CAMPFIRE_SIGNAL_SMOKE)) cir.setReturnValue(null);
        if (NoRenderCfg.noHeartParticles && type == ParticleTypes.HEART) cir.setReturnValue(null);
        if (NoRenderCfg.noBlockBreakParticles && type == ParticleTypes.BLOCK) cir.setReturnValue(null);
        if (NoRenderCfg.noEatParticles && (type == ParticleTypes.ITEM || type == ParticleTypes.ITEM_SLIME || type == ParticleTypes.ITEM_COBWEB || type == ParticleTypes.ITEM_SNOWBALL)) cir.setReturnValue(null);
        if (NoRenderCfg.noTotemParticles && type == ParticleTypes.TOTEM_OF_UNDYING) cir.setReturnValue(null);
        if (NoRenderCfg.noPotionParticles && (type == ParticleTypes.ENTITY_EFFECT || type == ParticleTypes.INSTANT_EFFECT || type == ParticleTypes.EFFECT)) cir.setReturnValue(null);
        if (NoRenderCfg.noDamageParticles && type == ParticleTypes.DAMAGE_INDICATOR) cir.setReturnValue(null);
        if (NoRenderCfg.noSweepParticles && type == ParticleTypes.SWEEP_ATTACK) cir.setReturnValue(null);
        if (NoRenderCfg.noCritParticles && (type == ParticleTypes.CRIT || type == ParticleTypes.ENCHANTED_HIT)) cir.setReturnValue(null);
        if (NoRenderCfg.noFlameParticles && (type == ParticleTypes.FLAME || type == ParticleTypes.SOUL_FIRE_FLAME || type == ParticleTypes.SMALL_FLAME || type == ParticleTypes.LAVA || type == ParticleTypes.COPPER_FIRE_FLAME)) cir.setReturnValue(null);
        if (NoRenderCfg.noSmokeParticles && (type == ParticleTypes.SMOKE || type == ParticleTypes.LARGE_SMOKE || type == ParticleTypes.WHITE_SMOKE)) cir.setReturnValue(null);
        if (NoRenderCfg.noBubbleParticles && (type == ParticleTypes.BUBBLE || type == ParticleTypes.BUBBLE_COLUMN_UP || type == ParticleTypes.BUBBLE_POP || type == ParticleTypes.CURRENT_DOWN)) cir.setReturnValue(null);
        if (NoRenderCfg.noCloudParticles && type == ParticleTypes.CLOUD) cir.setReturnValue(null);
        if (NoRenderCfg.noTrialSpawnerDetection && type == ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER) cir.setReturnValue(null);
        if (NoRenderCfg.noOminousSpawning && type == ParticleTypes.OMINOUS_SPAWNING) cir.setReturnValue(null);
        if (NoRenderCfg.noTrialSpawnerFlame && type == ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS) cir.setReturnValue(null);
        if (NoRenderCfg.noInfestedParticles && type == ParticleTypes.INFESTED) cir.setReturnValue(null);
        if (NoRenderCfg.noWindExplosion && (type == ParticleTypes.GUST || type == ParticleTypes.GUST_EMITTER_LARGE || type == ParticleTypes.GUST_EMITTER_SMALL || type == ParticleTypes.SMALL_GUST)) cir.setReturnValue(null);
        if (NoRenderCfg.noCobwebParticles && type == ParticleTypes.ITEM_COBWEB) cir.setReturnValue(null);
        if (NoRenderCfg.noSonicBoom && type == ParticleTypes.SONIC_BOOM) cir.setReturnValue(null);
        if (NoRenderCfg.noVibration && type == ParticleTypes.VIBRATION) cir.setReturnValue(null);
        if (NoRenderCfg.noSculkCharge && (type == ParticleTypes.SCULK_CHARGE || type == ParticleTypes.SCULK_CHARGE_POP)) cir.setReturnValue(null);
        if (NoRenderCfg.noShriekParticle && type == ParticleTypes.SHRIEK) cir.setReturnValue(null);
        if (NoRenderCfg.noVaultParticles && type == ParticleTypes.VAULT_CONNECTION) cir.setReturnValue(null);
        if (NoRenderCfg.noOmenEffect && (type == ParticleTypes.RAID_OMEN || type == ParticleTypes.TRIAL_OMEN)) cir.setReturnValue(null);
        if (NoRenderCfg.noAsh && (type == ParticleTypes.ASH || type == ParticleTypes.WHITE_ASH)) cir.setReturnValue(null);
        if (NoRenderCfg.noSoulParticles && (type == ParticleTypes.SOUL || type == ParticleTypes.SOUL_FIRE_FLAME)) cir.setReturnValue(null);
        if (NoRenderCfg.noDragonBreath && type == ParticleTypes.DRAGON_BREATH) cir.setReturnValue(null);
        if (NoRenderCfg.noDripParticles && (type == ParticleTypes.DRIPPING_WATER || type == ParticleTypes.DRIPPING_LAVA || type == ParticleTypes.DRIPPING_DRIPSTONE_WATER || type == ParticleTypes.DRIPPING_DRIPSTONE_LAVA || type == ParticleTypes.FALLING_WATER || type == ParticleTypes.FALLING_LAVA || type == ParticleTypes.LANDING_LAVA)) cir.setReturnValue(null);
        if (NoRenderCfg.noDripParticles && (type == ParticleTypes.DRIPPING_HONEY || type == ParticleTypes.FALLING_HONEY || type == ParticleTypes.LANDING_HONEY || type == ParticleTypes.FALLING_NECTAR)) cir.setReturnValue(null);
        if (NoRenderCfg.noDripParticles && (type == ParticleTypes.DRIPPING_OBSIDIAN_TEAR || type == ParticleTypes.FALLING_OBSIDIAN_TEAR || type == ParticleTypes.LANDING_OBSIDIAN_TEAR)) cir.setReturnValue(null);
        if (NoRenderCfg.noFallingDust && type == ParticleTypes.FALLING_DUST) cir.setReturnValue(null);
        if (NoRenderCfg.noCrimsonSpore && type == ParticleTypes.CRIMSON_SPORE) cir.setReturnValue(null);
        if (NoRenderCfg.noWarpedSpore && type == ParticleTypes.WARPED_SPORE) cir.setReturnValue(null);
        if (NoRenderCfg.noSporeBlossom && (type == ParticleTypes.SPORE_BLOSSOM_AIR || type == ParticleTypes.FALLING_SPORE_BLOSSOM)) cir.setReturnValue(null);
        if (NoRenderCfg.noCherryLeaves && type == ParticleTypes.CHERRY_LEAVES) cir.setReturnValue(null);
        if (NoRenderCfg.noPaleOakLeaves && type == ParticleTypes.PALE_OAK_LEAVES) cir.setReturnValue(null);
        if (NoRenderCfg.noGlowSquidInk && type == ParticleTypes.GLOW_SQUID_INK) cir.setReturnValue(null);
        if (NoRenderCfg.noSquidInk && type == ParticleTypes.SQUID_INK) cir.setReturnValue(null);
        if (NoRenderCfg.noSnowflake && type == ParticleTypes.SNOWFLAKE) cir.setReturnValue(null);
        if (NoRenderCfg.noSculkSoul && type == ParticleTypes.SCULK_SOUL) cir.setReturnValue(null);
        if (NoRenderCfg.noBreezeWind && (type == ParticleTypes.GUST || type == ParticleTypes.SMALL_GUST)) cir.setReturnValue(null);
        if (NoRenderCfg.noElectricSpark && type == ParticleTypes.ELECTRIC_SPARK) cir.setReturnValue(null);
        if (NoRenderCfg.noFallingDripstone && (type == ParticleTypes.FALLING_DRIPSTONE_WATER || type == ParticleTypes.FALLING_DRIPSTONE_LAVA)) cir.setReturnValue(null);
        if (NoRenderCfg.noRainSplash && type == ParticleTypes.RAIN) cir.setReturnValue(null);
        if (NoRenderCfg.noTintedLeaves && type == ParticleTypes.TINTED_LEAVES) cir.setReturnValue(null);
        if (NoRenderCfg.noDust && (type == ParticleTypes.DUST || type == ParticleTypes.DUST_COLOR_TRANSITION)) cir.setReturnValue(null);
        if (NoRenderCfg.noDustPlume && type == ParticleTypes.DUST_PLUME) cir.setReturnValue(null);
        if (NoRenderCfg.noDustPillar && type == ParticleTypes.DUST_PILLAR) cir.setReturnValue(null);
        if (NoRenderCfg.noBlockMarker && type == ParticleTypes.BLOCK_MARKER) cir.setReturnValue(null);
        if (NoRenderCfg.noBlockCrumble && type == ParticleTypes.BLOCK_CRUMBLE) cir.setReturnValue(null);
        if (NoRenderCfg.noComposter && type == ParticleTypes.COMPOSTER) cir.setReturnValue(null);
        if (NoRenderCfg.noElderGuardian && type == ParticleTypes.ELDER_GUARDIAN) cir.setReturnValue(null);
        if (NoRenderCfg.noAllay && type == ParticleTypes.NOTE) cir.setReturnValue(null);
        if (NoRenderCfg.noFirefly && type == ParticleTypes.FIREFLY) cir.setReturnValue(null);
        if (NoRenderCfg.noGlowParticle && type == ParticleTypes.GLOW) cir.setReturnValue(null);
        if (NoRenderCfg.noWax && (type == ParticleTypes.WAX_ON || type == ParticleTypes.WAX_OFF)) cir.setReturnValue(null);
        if (NoRenderCfg.noScrape && type == ParticleTypes.SCRAPE) cir.setReturnValue(null);
        if (NoRenderCfg.noEggCrack && type == ParticleTypes.EGG_CRACK) cir.setReturnValue(null);
        if (NoRenderCfg.noSulfur && (type == ParticleTypes.SULFUR_BUBBLES || type == ParticleTypes.SULFUR_CUBE_GOO)) cir.setReturnValue(null);
        if (NoRenderCfg.noSulfurCubes && type == ParticleTypes.SULFUR_CUBE_GOO) cir.setReturnValue(null);
        if (NoRenderCfg.noNoxiousGas && (type == ParticleTypes.NOXIOUS_GAS || type == ParticleTypes.NOXIOUS_GAS_CLOUD)) cir.setReturnValue(null);
        if (NoRenderCfg.noGeyser && (type == ParticleTypes.GEYSER || type == ParticleTypes.GEYSER_BASE || type == ParticleTypes.GEYSER_POOF || type == ParticleTypes.GEYSER_PLUME)) cir.setReturnValue(null);
        if (NoRenderCfg.noTrail && type == ParticleTypes.TRAIL) cir.setReturnValue(null);
        if (NoRenderCfg.noPauseMobGrowth && type == ParticleTypes.PAUSE_MOB_GROWTH) cir.setReturnValue(null);
        if (NoRenderCfg.noResetMobGrowth && type == ParticleTypes.RESET_MOB_GROWTH) cir.setReturnValue(null);
        if (NoRenderCfg.noNautilus && type == ParticleTypes.NAUTILUS) cir.setReturnValue(null);
        if (NoRenderCfg.noDolphin && type == ParticleTypes.DOLPHIN) cir.setReturnValue(null);
        if (NoRenderCfg.noUnderwater && type == ParticleTypes.UNDERWATER) cir.setReturnValue(null);
        if (NoRenderCfg.noReversePortal && type == ParticleTypes.REVERSE_PORTAL) cir.setReturnValue(null);
        if (NoRenderCfg.noCopperFireFlame && type == ParticleTypes.COPPER_FIRE_FLAME) cir.setReturnValue(null);
        if (NoRenderCfg.noEndRod && type == ParticleTypes.END_ROD) cir.setReturnValue(null);
        if (NoRenderCfg.noMycelium && type == ParticleTypes.MYCELIUM) cir.setReturnValue(null);
        if (NoRenderCfg.noSpit && type == ParticleTypes.SPIT) cir.setReturnValue(null);
        if (NoRenderCfg.noSneeze && type == ParticleTypes.SNEEZE) cir.setReturnValue(null);
        if (NoRenderCfg.noWitchParticle && type == ParticleTypes.WITCH) cir.setReturnValue(null);
        if (NoRenderCfg.noFishing && type == ParticleTypes.FISHING) cir.setReturnValue(null);
        if (NoRenderCfg.noAngryVillager && type == ParticleTypes.ANGRY_VILLAGER) cir.setReturnValue(null);
        if (NoRenderCfg.noHappyVillager && type == ParticleTypes.HAPPY_VILLAGER) cir.setReturnValue(null);
        if (NoRenderCfg.noTrialSpawnerEjection && type == ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER) cir.setReturnValue(null);
        if (NoRenderCfg.noOminousItemSpawn && type == ParticleTypes.OMINOUS_SPAWNING) cir.setReturnValue(null);
        if (NoRenderCfg.noItemSlime && type == ParticleTypes.ITEM_SLIME) cir.setReturnValue(null);
        if (NoRenderCfg.noItemCobweb && type == ParticleTypes.ITEM_COBWEB) cir.setReturnValue(null);
        if (NoRenderCfg.noItemSnowball && type == ParticleTypes.ITEM_SNOWBALL) cir.setReturnValue(null);
    }
}