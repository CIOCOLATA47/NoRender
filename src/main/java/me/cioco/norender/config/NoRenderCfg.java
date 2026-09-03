package me.cioco.norender.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class NoRenderCfg {

    public static final String CONFIG_FILE = "norender-config.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(NoRenderCfg.class);

    public static boolean noPortalOverlay = false;
    public static boolean noSpyglassOverlay = false;
    public static boolean noNausea = false;
    public static boolean noPumpkinOverlay = false;
    public static boolean noPowderedSnowOverlay = false;
    public static boolean noFireOverlay = false;
    public static boolean noLiquidOverlay = false;
    public static boolean noInWallOverlay = false;
    public static boolean noVignette = false;
    public static boolean noPotionIcons = false;
    public static boolean noDarkness = false;
    public static boolean noGrassAndFlowers = false;
    public static boolean noFog = false;
    public static boolean noGlow = false;
    public static boolean noBlindness = false;
    public static boolean noWeather = false;
    public static boolean noTotemAnimation = false;
    public static boolean noDroppedItems = false;
    public static boolean noItemFrames = false;
    public static boolean noArmorStands = false;
    public static boolean noExperienceOrbs = false;
    public static boolean noArmor = false;
    public static boolean noExplosions = false;
    public static boolean noFireworks = false;
    public static boolean noCampfireSmoke = false;
    public static boolean noHeartParticles = false;
    public static boolean noBlockBreakParticles = false;
    public static boolean noEatParticles = false;
    public static boolean noTotemParticles = false;
    public static boolean noPotionParticles = false;
    public static boolean noSonicBoom = false;
    public static boolean noVibration = false;
    public static boolean noDamageParticles = false;
    public static boolean noSweepParticles = false;
    public static boolean noFallingDust = false;
    public static boolean noCloudParticles = false;
    public static boolean noDripParticles = false;
    public static boolean noBubbleParticles = false;
    public static boolean noFlameParticles = false;
    public static boolean noSmokeParticles = false;
    public static boolean noCritParticles = false;
    public static boolean noSculkCharge = false;
    public static boolean noShriekParticle = false;
    public static boolean noTrialSpawnerDetection = false;
    public static boolean noOminousSpawning = false;
    public static boolean noTrialSpawnerFlame = false;
    public static boolean noInfestedParticles = false;
    public static boolean noWindExplosion = false;
    public static boolean noCobwebParticles = false;
    public static boolean noAsh = false;
    public static boolean noSoulParticles = false;
    public static boolean noDragonBreath = false;
    public static boolean noBossBar = false;
    public static boolean noScoreboard = false;
    public static boolean noVaultParticles = false;
    public static boolean noOmenEffect = false;
    public static boolean noSignText = false;
    public static boolean noBeaconBeam = false;
    public static boolean noConduitEye = false;
    public static boolean noCrosshair = false;
    public static boolean noHotbar = false;
    public static boolean noChat = false;
    public static boolean noTitleAndActionbar = false;
    public static boolean noPlayerList = false;
    public static boolean noAdvancementToast = false;
    public static boolean noClouds = false;
    public static boolean noSky = false;
    public static boolean noRainSplash = false;
    public static boolean noSporeBlossom = false;
    public static boolean noCherryLeaves = false;
    public static boolean noGlowSquidInk = false;
    public static boolean noSnowflake = false;
    public static boolean noSculkSoul = false;
    public static boolean noBreezeWind = false;
    public static boolean noElectricSpark = false;
    public static boolean noFallingDripstone = false;
    public static boolean noMinecarts = false;
    public static boolean noBoats = false;
    public static boolean noPaintings = false;
    public static boolean noLeashKnots = false;
    public static boolean noFallingBlocks = false;
    public static boolean noProjectiles = false;
    public static boolean noBlockBreakCrack = false;
    public static boolean noEntityHitboxOutline = false;
    public static boolean noTintedLeaves = false;
    public static boolean noDust = false;
    public static boolean noBlockMarker = false;
    public static boolean noBlockCrumble = false;
    public static boolean noComposter = false;
    public static boolean noElderGuardian = false;
    public static boolean noAllay = false;
    public static boolean noFirefly = false;
    public static boolean noGlowParticle = false;
    public static boolean noWax = false;
    public static boolean noScrape = false;
    public static boolean noCopperFireFlame = false;
    public static boolean noEggCrack = false;
    public static boolean noSulfur = false;
    public static boolean noNoxiousGas = false;
    public static boolean noGeyser = false;
    public static boolean noTrail = false;
    public static boolean noPauseMobGrowth = false;
    public static boolean noResetMobGrowth = false;
    public static boolean noNautilus = false;
    public static boolean noDolphin = false;
    public static boolean noUnderwater = false;
    public static boolean noReversePortal = false;
    public static boolean noEndRod = false;
    public static boolean noMycelium = false;
    public static boolean noSpit = false;
    public static boolean noSneeze = false;
    public static boolean noWitchParticle = false;
    public static boolean noFishing = false;
    public static boolean noAngryVillager = false;
    public static boolean noHappyVillager = false;
    public static boolean noCrimsonSpore = false;
    public static boolean noWarpedSpore = false;
    public static boolean noSquidInk = false;
    public static boolean noPaleOakLeaves = false;
    public static boolean noTrialSpawnerEjection = false;
    public static boolean noOminousItemSpawn = false;
    public static boolean noItemSlime = false;
    public static boolean noItemCobweb = false;
    public static boolean noItemSnowball = false;
    public static boolean noDustPlume = false;
    public static boolean noDustPillar = false;
    public static boolean noSulfurCubes = false;

    public static void saveConfiguration() {
        try {
            Path configPath = getConfigPath();
            Files.createDirectories(configPath.getParent());

            Properties props = new Properties();
            for (Field field : NoRenderCfg.class.getDeclaredFields()) {
                if (isValidConfigField(field)) {
                    props.setProperty(field.getName(), String.valueOf(field.getBoolean(null)));
                }
            }

            try (OutputStream output = Files.newOutputStream(configPath)) {
                props.store(output, "NoRender Config");
            }
        } catch (IOException | IllegalAccessException e) {
            LOGGER.error("Failed to save NoRender config", e);
        }
    }

    public static void loadConfiguration() {
        Path configPath = getConfigPath();
        if (!Files.exists(configPath)) {
            saveConfiguration();
            return;
        }

        try (InputStream input = Files.newInputStream(configPath)) {
            Properties props = new Properties();
            props.load(input);

            for (Field field : NoRenderCfg.class.getDeclaredFields()) {
                if (isValidConfigField(field)) {
                    String value = props.getProperty(field.getName());
                    if (value != null) {
                        field.setBoolean(null, Boolean.parseBoolean(value));
                    }
                }
            }
        } catch (IOException | IllegalAccessException e) {
            LOGGER.error("Failed to load NoRender config", e);
        }
    }

    private static boolean isValidConfigField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers)
                && Modifier.isStatic(modifiers)
                && field.getType() == boolean.class;
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
    }
}