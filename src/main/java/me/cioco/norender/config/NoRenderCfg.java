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
    public static boolean noEnchantmentGlint = false;

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