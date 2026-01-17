package me.cioco.norender.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static boolean noWeather = false;
    public static boolean noTotemAnimation = false;
    public static boolean noDroppedItems = false;
    public static boolean noItemFrames = false;
    public static boolean noArmorStands = false;

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

    public static void saveConfiguration() {
        try {
            Path configPath = getConfigPath();
            Files.createDirectories(configPath.getParent());

            try (OutputStream output = Files.newOutputStream(configPath)) {
                Properties props = new Properties();

                props.setProperty("noPortalOverlay", String.valueOf(noPortalOverlay));
                props.setProperty("noSpyglassOverlay", String.valueOf(noSpyglassOverlay));
                props.setProperty("noNausea", String.valueOf(noNausea));
                props.setProperty("noPumpkinOverlay", String.valueOf(noPumpkinOverlay));
                props.setProperty("noPowderedSnowOverlay", String.valueOf(noPowderedSnowOverlay));
                props.setProperty("noFireOverlay", String.valueOf(noFireOverlay));
                props.setProperty("noLiquidOverlay", String.valueOf(noLiquidOverlay));
                props.setProperty("noInWallOverlay", String.valueOf(noInWallOverlay));
                props.setProperty("noVignette", String.valueOf(noVignette));
                props.setProperty("noPotionIcons", String.valueOf(noPotionIcons));
                props.setProperty("noDarkness", String.valueOf(noDarkness));

                props.setProperty("noWeather", String.valueOf(noWeather));
                props.setProperty("noTotemAnimation", String.valueOf(noTotemAnimation));
                props.setProperty("noDroppedItems", String.valueOf(noDroppedItems));
                props.setProperty("noItemFrames", String.valueOf(noItemFrames));
                props.setProperty("noArmorStands", String.valueOf(noArmorStands));

                props.setProperty("noExplosions", String.valueOf(noExplosions));
                props.setProperty("noFireworks", String.valueOf(noFireworks));
                props.setProperty("noCampfireSmoke", String.valueOf(noCampfireSmoke));
                props.setProperty("noHeartParticles", String.valueOf(noHeartParticles));
                props.setProperty("noBlockBreakParticles", String.valueOf(noBlockBreakParticles));
                props.setProperty("noEatParticles", String.valueOf(noEatParticles));
                props.setProperty("noTotemParticles", String.valueOf(noTotemParticles));
                props.setProperty("noPotionParticles", String.valueOf(noPotionParticles));
                props.setProperty("noSonicBoom", String.valueOf(noSonicBoom));
                props.setProperty("noVibration", String.valueOf(noVibration));
                props.setProperty("noDamageParticles", String.valueOf(noDamageParticles));
                props.setProperty("noSweepParticles", String.valueOf(noSweepParticles));
                props.setProperty("noFallingDust", String.valueOf(noFallingDust));

                props.store(output, "NoRender Config");
            }
        } catch (IOException e) {
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

            noPortalOverlay = getBoolean(props, "noPortalOverlay", false);
            noSpyglassOverlay = getBoolean(props, "noSpyglassOverlay", false);
            noNausea = getBoolean(props, "noNausea", false);
            noPumpkinOverlay = getBoolean(props, "noPumpkinOverlay", false);
            noPowderedSnowOverlay = getBoolean(props, "noPowderedSnowOverlay", false);
            noFireOverlay = getBoolean(props, "noFireOverlay", false);
            noLiquidOverlay = getBoolean(props, "noLiquidOverlay", false);
            noInWallOverlay = getBoolean(props, "noInWallOverlay", false);
            noVignette = getBoolean(props, "noVignette", false);
            noPotionIcons = getBoolean(props, "noPotionIcons", false);
            noDarkness = getBoolean(props, "noDarkness", false);

            noWeather = getBoolean(props, "noWeather", false);
            noTotemAnimation = getBoolean(props, "noTotemAnimation", false);
            noDroppedItems = getBoolean(props, "noDroppedItems", false);
            noItemFrames = getBoolean(props, "noItemFrames", false);
            noArmorStands = getBoolean(props, "noArmorStands", false);

            noExplosions = getBoolean(props, "noExplosions", false);
            noFireworks = getBoolean(props, "noFireworks", false);
            noCampfireSmoke = getBoolean(props, "noCampfireSmoke", false);
            noHeartParticles = getBoolean(props, "noHeartParticles", false);
            noBlockBreakParticles = getBoolean(props, "noBlockBreakParticles", false);
            noEatParticles = getBoolean(props, "noEatParticles", false);
            noTotemParticles = getBoolean(props, "noTotemParticles", false);
            noPotionParticles = getBoolean(props, "noPotionParticles", false);
            noSonicBoom = getBoolean(props, "noSonicBoom", false);
            noVibration = getBoolean(props, "noVibration", false);
            noDamageParticles = getBoolean(props, "noDamageParticles", false);
            noSweepParticles = getBoolean(props, "noSweepParticles", false);
            noFallingDust = getBoolean(props, "noFallingDust", false);

        } catch (Exception e) {
            LOGGER.error("Failed to load NoRender config", e);
        }
    }

    private static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        return Boolean.parseBoolean(props.getProperty(key, String.valueOf(defaultValue)));
    }

    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
    }
}