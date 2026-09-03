package me.cioco.norender.gui;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NoRenderGui extends Screen {

    private static final int SPACING_Y = 24;
    private static final int SECTION_MARGIN = 45;
    private static final int SCROLL_TOP = 65;

    private final Screen parent;
    private final List<WidgetEntry> widgetEntries = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxScroll;
    private int contentHeight;
    private Button doneButton;
    private EditBox searchField;
    private String searchQuery = "";

    public NoRenderGui(Screen parent) {
        super(Component.literal("NoRender Options"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.clearWidgets();
        this.widgetEntries.clear();
        scrollOffset = 0;
        searchQuery = "";

        int centerX = width / 2;
        int leftCol = centerX - 155;
        int rightCol = centerX + 5;

        int currentY = 85;

        searchField = new EditBox(font, centerX - 155, 40, 310, 20, Component.literal("Search..."));
        searchField.setMaxLength(64);
        searchField.setHint(Component.literal("Search options...").withStyle(ChatFormatting.GRAY));
        searchField.setValue("");
        searchField.setResponder(text -> {
            searchQuery = text.toLowerCase().trim();
            scrollOffset = 0;
            applySearch();
        });
        addRenderableWidget(searchField);

        addOverlayButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addWorldButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addCommonParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 8) + SECTION_MARGIN;

        addNewEraParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addTechnicalButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        addHudButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addWorldRenderButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 3) + SECTION_MARGIN;

        addMoreParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 11) + SECTION_MARGIN;

        addExtraParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 13) + SECTION_MARGIN;

        addMoreEntityButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addMiscButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 3) + SECTION_MARGIN;

        contentHeight = currentY;
        maxScroll = Math.max(0, contentHeight - (height - 90));

        doneButton = Button.builder(
                Component.literal("SAVE & EXIT").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD),
                _ -> this.onClose()
        ).bounds(centerX - 100, height - 30, 200, 20).build();
        addRenderableWidget(doneButton);

        syncWidgetPositions();
    }

    private void syncWidgetPositions() {
        for (WidgetEntry entry : widgetEntries) {
            entry.widget().setX(entry.originalX());
            int newY = entry.originalY() - scrollOffset;
            entry.widget().setY(newY);
            entry.widget().visible = true;
            entry.widget().active = newY >= SCROLL_TOP && newY < height - 40;
        }
    }

    private void applySearch() {
        if (searchQuery.isEmpty()) {
            syncWidgetPositions();
            contentHeight = widgetEntries.isEmpty() ? 200
                    : widgetEntries.stream().mapToInt(e -> e.originalY() + 20).max().orElse(200) + 40;
        } else {
            int centerX = width / 2;
            int leftCol = centerX - 155;
            int rightCol = centerX + 5;
            int y = 85;
            boolean useLeft = true;

            for (WidgetEntry entry : widgetEntries) {
                String label = entry.widget().getMessage().getString().toLowerCase();
                if (label.contains(searchQuery)) {
                    entry.widget().visible = true;
                    entry.widget().setX(useLeft ? leftCol : rightCol);
                    int newY = y - scrollOffset;
                    entry.widget().setY(newY);
                    entry.widget().active = newY >= SCROLL_TOP && newY < height - 40;
                    if (!useLeft) y += SPACING_Y;
                    useLeft = !useLeft;
                } else {
                    entry.widget().visible = false;
                    entry.widget().active = false;
                }
            }
            if (!useLeft) y += SPACING_Y;
            contentHeight = Math.max(y + 40, height);
        }
        maxScroll = Math.max(0, contentHeight - (height - 90));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        ctx.fillGradient(0, 0, width, height, 0xC0101010, 0xD0101010);

        int cx = width / 2;
        int panelW = 325;
        int panelX = cx - (panelW / 2);

        ctx.centeredText(font,
                Component.literal("NoRender").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD, ChatFormatting.UNDERLINE),
                cx, 15, 0xFFFFFFFF);

        ctx.enableScissor(0, SCROLL_TOP, width, height - 40);

        if (searchQuery.isEmpty()) {
            int y = 85 - scrollOffset;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "Overlays");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "World & Entities");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 8, "Common Particles");
            y += (SPACING_Y * 8) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "Sculk & Trial Chambers");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 5, "Nether, End & Game");
            y += (SPACING_Y * 5) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "HUD Elements");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 3, "World Render");
            y += (SPACING_Y * 3) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 11, "More Particles");
            y += (SPACING_Y * 11) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 13, "Extra Particles");
            y += (SPACING_Y * 13) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "More Entities");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 2, "Misc");
        } else {
            ctx.text(font, Component.literal("§7Results for: §b\"" + searchQuery + "\""), panelX, 68, 0xFFFFFF);
        }

        for (WidgetEntry entry : widgetEntries) {
            AbstractWidget btn = entry.widget();
            if (!btn.visible) continue;
            if (btn.getY() + btn.getHeight() > SCROLL_TOP && btn.getY() < height - 40) {
                btn.extractRenderState(ctx, mouseX, mouseY, delta);
            }
        }

        ctx.disableScissor();

        if (!searchQuery.isEmpty()) {
            boolean anyVisible = widgetEntries.stream().anyMatch(e -> e.widget().visible);
            if (!anyVisible) {
                ctx.centeredText(font,
                        Component.literal("§cNo results for \"" + searchQuery + "\""),
                        cx, height / 2, 0xFFFFFFFF);
            }
        }

        searchField.extractRenderState(ctx, mouseX, mouseY, delta);
        doneButton.extractRenderState(ctx, mouseX, mouseY, delta);
        drawScrollBar(ctx);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (maxScroll > 0) {
            int oldOffset = scrollOffset;
            scrollOffset = (int) Math.clamp(scrollOffset - (verticalAmount * 25), 0, maxScroll);
            int diff = oldOffset - scrollOffset;
            if (diff != 0) {
                for (WidgetEntry entry : widgetEntries) {
                    if (entry.widget().visible) {
                        int newY = entry.widget().getY() + diff;
                        entry.widget().setY(newY);
                        entry.widget().active = newY >= SCROLL_TOP && newY < height - 40;
                    }
                }
            }
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void add(int x, int y, String label, String desc, boolean val, Consumer<Boolean> action) {
        Button btn = Button.builder(getToggleText(label, val), b -> {
            boolean currentOn = b.getMessage().getString().contains("ON");
            action.accept(!currentOn);
            b.setMessage(getToggleText(label, !currentOn));
        }).bounds(x, y, 150, 20).tooltip(Tooltip.create(Component.literal("§e" + desc))).build();
        widgetEntries.add(new WidgetEntry(btn, x, y));
        addRenderableWidget(btn);
    }

    private void renderSectionGroup(GuiGraphicsExtractor ctx, int x, int y, int w, int buttonRows, String title) {
        int headerHeight = 25;
        int contentH = (buttonRows * SPACING_Y) + 10;

        drawStyledPanel(ctx, x, y - 20, w, contentH + headerHeight);

        ctx.text(font, "§b§l» §f" + title, x + 8, y - 12, 0xFFFFFFFF);

        ctx.fill(x + 5, y + 2, x + w - 5, y + 3, 0x8000FFFF);
    }

    private void drawStyledPanel(GuiGraphicsExtractor ctx, int x, int y, int width, int height) {
        ctx.fill(x, y, x + width, y + height, 0x90000000);
        ctx.fill(x, y, x + 2, y + height, 0xFF00FFFF);
        ctx.fill(x + width - 2, y, x + width, y + height, 0xFF00FFFF);
    }

    private void drawScrollBar(GuiGraphicsExtractor ctx) {
        if (maxScroll <= 0) return;
        int trackHeight = height - 80;
        int barHeight = Math.max(20, (trackHeight * trackHeight) / Math.max(1, contentHeight));
        int barY = 40 + (int) ((trackHeight - barHeight) * ((float) scrollOffset / maxScroll));
        ctx.fill(width - 6, barY, width - 2, barY + barHeight, 0xFF00FFFF);
    }

    private Component getToggleText(String label, boolean value) {
        return Component.literal(label + ": ").append(
                value ? Component.literal("ON").withStyle(ChatFormatting.GREEN)
                        : Component.literal("OFF").withStyle(ChatFormatting.RED));
    }

    @Override
    public void onClose() {
        NoRenderCfg.saveConfiguration();
        minecraft.setScreenAndShow(parent);
    }

    private void addOverlayButtons(int left, int right, int y) {
        add(left, y, "Portals", "Hide portal effect", NoRenderCfg.noPortalOverlay, v -> NoRenderCfg.noPortalOverlay = v);
        add(right, y, "Fire", "Hide fire overlay", NoRenderCfg.noFireOverlay, v -> NoRenderCfg.noFireOverlay = v);
        add(left, y + SPACING_Y, "Nausea", "Hide nausea tilt", NoRenderCfg.noNausea, v -> NoRenderCfg.noNausea = v);
        add(right, y + SPACING_Y, "Darkness", "Hide darkness", NoRenderCfg.noDarkness, v -> NoRenderCfg.noDarkness = v);
        add(left, y + SPACING_Y * 2, "Pumpkin", "Hide pumpkin blur", NoRenderCfg.noPumpkinOverlay, v -> NoRenderCfg.noPumpkinOverlay = v);
        add(right, y + SPACING_Y * 2, "Vignette", "Hide dark corners", NoRenderCfg.noVignette, v -> NoRenderCfg.noVignette = v);
        add(left, y + SPACING_Y * 3, "PowderSnow", "Removes powdersnow overlay", NoRenderCfg.noPowderedSnowOverlay, v -> NoRenderCfg.noPowderedSnowOverlay = v);
        add(right, y + SPACING_Y * 3, "Spyglass", "Hide spyglass container", NoRenderCfg.noSpyglassOverlay, v -> NoRenderCfg.noSpyglassOverlay = v);
        add(left, y + SPACING_Y * 4, "Liquid", "Hide water/lava tint", NoRenderCfg.noLiquidOverlay, v -> NoRenderCfg.noLiquidOverlay = v);
        add(right, y + SPACING_Y * 4, "Blindness", "Hide blindness effect", NoRenderCfg.noBlindness, v -> NoRenderCfg.noBlindness = v);
    }

    private void addWorldButtons(int left, int right, int y) {
        add(left, y, "Weather", "Hide rain/snow", NoRenderCfg.noWeather, v -> NoRenderCfg.noWeather = v);
        add(right, y, "In Wall", "Hide inside-block tint", NoRenderCfg.noInWallOverlay, v -> NoRenderCfg.noInWallOverlay = v);
        add(left, y + SPACING_Y, "Items", "Hide dropped items", NoRenderCfg.noDroppedItems, v -> NoRenderCfg.noDroppedItems = v);
        add(right, y + SPACING_Y, "XP Orbs", "Hide XP Orbs", NoRenderCfg.noExperienceOrbs, v -> NoRenderCfg.noExperienceOrbs = v);
        add(left, y + SPACING_Y * 2, "Frames", "Hide item frames", NoRenderCfg.noItemFrames, v -> NoRenderCfg.noItemFrames = v);
        add(right, y + SPACING_Y * 2, "Stands", "Hide armor stands", NoRenderCfg.noArmorStands, v -> NoRenderCfg.noArmorStands = v);
        add(left, y + SPACING_Y * 3, "Armor", "Hide player armor", NoRenderCfg.noArmor, v -> NoRenderCfg.noArmor = v);
        add(right, y + SPACING_Y * 3, "Potion Icons", "Hide HUD effect icons", NoRenderCfg.noPotionIcons, v -> NoRenderCfg.noPotionIcons = v);
        add(left, y + SPACING_Y * 4, "Totem Anim", "Hide big totem icon", NoRenderCfg.noTotemAnimation, v -> NoRenderCfg.noTotemAnimation = v);
        add(right, y + SPACING_Y * 4, "Fog", "Hide all fog", NoRenderCfg.noFog, v -> NoRenderCfg.noFog = v);
        add(left, y + SPACING_Y * 5, "Glow", "Hide entity outlines", NoRenderCfg.noGlow, v -> NoRenderCfg.noGlow = v);
        add(right, y + SPACING_Y * 5, "Grass & Flowers", "Hide grass and flowers", NoRenderCfg.noGrassAndFlowers, v -> {
            NoRenderCfg.noGrassAndFlowers = v;
            minecraft.levelExtractor.allChanged();
        });
    }

    private void addCommonParticleButtons(int left, int right, int y) {
        add(left, y, "Explosions", "Hide blasts", NoRenderCfg.noExplosions, v -> NoRenderCfg.noExplosions = v);
        add(right, y, "Fireworks", "Hide sparks", NoRenderCfg.noFireworks, v -> NoRenderCfg.noFireworks = v);
        add(left, y + SPACING_Y, "Campfire", "Hide smoke", NoRenderCfg.noCampfireSmoke, v -> NoRenderCfg.noCampfireSmoke = v);
        add(right, y + SPACING_Y, "Hearts", "Hide love hearts", NoRenderCfg.noHeartParticles, v -> NoRenderCfg.noHeartParticles = v);
        add(left, y + SPACING_Y * 2, "Eating", "Hide food crumbs", NoRenderCfg.noEatParticles, v -> NoRenderCfg.noEatParticles = v);
        add(right, y + SPACING_Y * 2, "Blocks", "Hide break particles", NoRenderCfg.noBlockBreakParticles, v -> NoRenderCfg.noBlockBreakParticles = v);
        add(left, y + SPACING_Y * 3, "Potion Swap", "Hide swirls", NoRenderCfg.noPotionParticles, v -> NoRenderCfg.noPotionParticles = v);
        add(right, y + SPACING_Y * 3, "Damage", "Hide hit indicators", NoRenderCfg.noDamageParticles, v -> NoRenderCfg.noDamageParticles = v);
        add(left, y + SPACING_Y * 4, "Sweep", "Hide sword sweep", NoRenderCfg.noSweepParticles, v -> NoRenderCfg.noSweepParticles = v);
        add(right, y + SPACING_Y * 4, "Fall Dust", "Hide sand dust", NoRenderCfg.noFallingDust, v -> NoRenderCfg.noFallingDust = v);
        add(left, y + SPACING_Y * 5, "Flame", "Hide torch/fire", NoRenderCfg.noFlameParticles, v -> NoRenderCfg.noFlameParticles = v);
        add(right, y + SPACING_Y * 5, "Smoke", "Hide basic smoke", NoRenderCfg.noSmokeParticles, v -> NoRenderCfg.noSmokeParticles = v);
        add(left, y + SPACING_Y * 6, "Bubbles", "Hide water bubbles", NoRenderCfg.noBubbleParticles, v -> NoRenderCfg.noBubbleParticles = v);
        add(right, y + SPACING_Y * 6, "Crits", "Hide crit sparks", NoRenderCfg.noCritParticles, v -> NoRenderCfg.noCritParticles = v);
        add(left, y + SPACING_Y * 7, "Clouds", "Hide cloud particles", NoRenderCfg.noCloudParticles, v -> NoRenderCfg.noCloudParticles = v);
        add(right, y + SPACING_Y * 7, "Totem Part.", "Hide totem particles", NoRenderCfg.noTotemParticles, v -> NoRenderCfg.noTotemParticles = v);
    }

    private void addNewEraParticleButtons(int left, int right, int y) {
        add(left, y, "Trial Spawner", "Disable particles from Spawners", NoRenderCfg.noTrialSpawnerDetection, v -> NoRenderCfg.noTrialSpawnerDetection = v);
        add(right, y, "Ominous", "Remove ominous visual effects", NoRenderCfg.noOminousSpawning, v -> NoRenderCfg.noOminousSpawning = v);
        add(left, y + SPACING_Y, "Infested", "Hide silverfish effects", NoRenderCfg.noInfestedParticles, v -> NoRenderCfg.noInfestedParticles = v);
        add(right, y + SPACING_Y, "Wind Explosion", "Suppress gust particles", NoRenderCfg.noWindExplosion, v -> NoRenderCfg.noWindExplosion = v);
        add(left, y + SPACING_Y * 2, "Trial Flame", "Disable fire trap flames", NoRenderCfg.noTrialSpawnerFlame, v -> NoRenderCfg.noTrialSpawnerFlame = v);
        add(right, y + SPACING_Y * 2, "Sonic Boom", "Remove Warden shockwaves", NoRenderCfg.noSonicBoom, v -> NoRenderCfg.noSonicBoom = v);
        add(left, y + SPACING_Y * 3, "Sculk Charge", "Hide spreading effects", NoRenderCfg.noSculkCharge, v -> NoRenderCfg.noSculkCharge = v);
        add(right, y + SPACING_Y * 3, "Vibrations", "Remove vibration lines", NoRenderCfg.noVibration, v -> NoRenderCfg.noVibration = v);
        add(left, y + SPACING_Y * 4, "Shrieks", "Suppress Warden shrieks", NoRenderCfg.noShriekParticle, v -> NoRenderCfg.noShriekParticle = v);
        add(right, y + SPACING_Y * 4, "Cobwebs", "Hide trial cobweb visuals", NoRenderCfg.noCobwebParticles, v -> NoRenderCfg.noCobwebParticles = v);
        add(left, y + SPACING_Y * 5, "Vault Part.", "Hide vault effects", NoRenderCfg.noVaultParticles, v -> NoRenderCfg.noVaultParticles = v);
        add(right, y + SPACING_Y * 5, "Omen Effect", "Hide omen visual effect", NoRenderCfg.noOmenEffect, v -> NoRenderCfg.noOmenEffect = v);
    }

    private void addTechnicalButtons(int left, int right, int y) {
        add(left, y, "Nether Ash", "Basalt Deltas ash", NoRenderCfg.noAsh, v -> NoRenderCfg.noAsh = v);
        add(right, y, "Soul Particles", "Soul sand effects", NoRenderCfg.noSoulParticles, v -> NoRenderCfg.noSoulParticles = v);
        add(left, y + SPACING_Y, "Dragon Breath", "Ender Dragon gas", NoRenderCfg.noDragonBreath, v -> NoRenderCfg.noDragonBreath = v);
        add(right, y + SPACING_Y, "Drips", "Water/Lava drips", NoRenderCfg.noDripParticles, v -> NoRenderCfg.noDripParticles = v);
        add(left, y + SPACING_Y * 2, "Boss Bar", "Hide boss health bar", NoRenderCfg.noBossBar, v -> NoRenderCfg.noBossBar = v);
        add(right, y + SPACING_Y * 2, "Scoreboard", "Hide sidebar scoreboard", NoRenderCfg.noScoreboard, v -> NoRenderCfg.noScoreboard = v);
        add(left, y + SPACING_Y * 3, "Sign Text", "Hide text rendered on signs", NoRenderCfg.noSignText, v -> NoRenderCfg.noSignText = v);
        add(right, y + SPACING_Y * 3, "Beacon Beam", "Hide beacon beam column", NoRenderCfg.noBeaconBeam, v -> NoRenderCfg.noBeaconBeam = v);
        add(left, y + SPACING_Y * 4, "Conduit Eye", "Hide conduit effects", NoRenderCfg.noConduitEye, v -> NoRenderCfg.noConduitEye = v);
    }

    private void addHudButtons(int left, int right, int y) {
        add(left, y, "Crosshair", "Hide crosshair", NoRenderCfg.noCrosshair, v -> NoRenderCfg.noCrosshair = v);
        add(right, y, "Hotbar", "Hide hotbar and offhand", NoRenderCfg.noHotbar, v -> NoRenderCfg.noHotbar = v);
        add(left, y + SPACING_Y, "Chat", "Hide chat messages", NoRenderCfg.noChat, v -> NoRenderCfg.noChat = v);
        add(right, y + SPACING_Y, "Title/Actionbar", "Hide title and action bar", NoRenderCfg.noTitleAndActionbar, v -> NoRenderCfg.noTitleAndActionbar = v);
        add(left, y + SPACING_Y * 2, "Player List", "Hide tab list", NoRenderCfg.noPlayerList, v -> NoRenderCfg.noPlayerList = v);
        add(right, y + SPACING_Y * 2, "Advancements", "Hide advancement toasts", NoRenderCfg.noAdvancementToast, v -> NoRenderCfg.noAdvancementToast = v);
    }

    private void addWorldRenderButtons(int left, int right, int y) {
        add(left, y, "Clouds", "Hide cloud layer", NoRenderCfg.noClouds, v -> NoRenderCfg.noClouds = v);
        add(right, y, "Sky", "Hide sky, stars, sun, moon", NoRenderCfg.noSky, v -> NoRenderCfg.noSky = v);
        add(left, y + SPACING_Y, "Rain Splash", "Hide rain splash particles", NoRenderCfg.noRainSplash, v -> NoRenderCfg.noRainSplash = v);
    }

    private void addMoreParticleButtons(int left, int right, int y) {
        add(left, y, "Spore Blossom", "Spore blossom petals", NoRenderCfg.noSporeBlossom, v -> NoRenderCfg.noSporeBlossom = v);
        add(right, y, "Cherry Leaves", "Falling cherry leaves", NoRenderCfg.noCherryLeaves, v -> NoRenderCfg.noCherryLeaves = v);
        add(left, y + SPACING_Y, "Glow Ink", "Glow squid ink clouds", NoRenderCfg.noGlowSquidInk, v -> NoRenderCfg.noGlowSquidInk = v);
        add(right, y + SPACING_Y, "Snowflake", "Snow falling particles", NoRenderCfg.noSnowflake, v -> NoRenderCfg.noSnowflake = v);
        add(left, y + SPACING_Y * 2, "Sculk Soul", "Soul particles from sculk", NoRenderCfg.noSculkSoul, v -> NoRenderCfg.noSculkSoul = v);
        add(right, y + SPACING_Y * 2, "Breeze Wind", "Breeze gust particles", NoRenderCfg.noBreezeWind, v -> NoRenderCfg.noBreezeWind = v);
        add(left, y + SPACING_Y * 3, "Electric Spark", "Lightning rod sparks", NoRenderCfg.noElectricSpark, v -> NoRenderCfg.noElectricSpark = v);
        add(right, y + SPACING_Y * 3, "Falling Dripstone", "Dripstone water/lava", NoRenderCfg.noFallingDripstone, v -> NoRenderCfg.noFallingDripstone = v);
        add(left, y + SPACING_Y * 4, "Tinted Leaves", "Hide tinted leaf particles", NoRenderCfg.noTintedLeaves, v -> NoRenderCfg.noTintedLeaves = v);
        add(right, y + SPACING_Y * 4, "Dust", "Hide dust particles", NoRenderCfg.noDust, v -> NoRenderCfg.noDust = v);
        add(left, y + SPACING_Y * 5, "Block Marker", "Hide block marker", NoRenderCfg.noBlockMarker, v -> NoRenderCfg.noBlockMarker = v);
        add(right, y + SPACING_Y * 5, "Block Crumble", "Hide block crumble", NoRenderCfg.noBlockCrumble, v -> NoRenderCfg.noBlockCrumble = v);
        add(left, y + SPACING_Y * 6, "Composter", "Hide composter particles", NoRenderCfg.noComposter, v -> NoRenderCfg.noComposter = v);
        add(right, y + SPACING_Y * 6, "Elder Guardian", "Hide elder guardian", NoRenderCfg.noElderGuardian, v -> NoRenderCfg.noElderGuardian = v);
        add(left, y + SPACING_Y * 7, "Allay", "Hide allay notes", NoRenderCfg.noAllay, v -> NoRenderCfg.noAllay = v);
        add(right, y + SPACING_Y * 7, "Firefly", "Hide fireflies", NoRenderCfg.noFirefly, v -> NoRenderCfg.noFirefly = v);
        add(left, y + SPACING_Y * 8, "Pale Oak Leaves", "Hide pale oak leaves", NoRenderCfg.noPaleOakLeaves, v -> NoRenderCfg.noPaleOakLeaves = v);
        add(right, y + SPACING_Y * 8, "Squid Ink", "Hide squid ink", NoRenderCfg.noSquidInk, v -> NoRenderCfg.noSquidInk = v);
        add(left, y + SPACING_Y * 9, "Egg Crack", "Hide egg crack", NoRenderCfg.noEggCrack, v -> NoRenderCfg.noEggCrack = v);
        add(right, y + SPACING_Y * 9, "Sulfur", "Hide sulfur particles", NoRenderCfg.noSulfur, v -> NoRenderCfg.noSulfur = v);
        add(left, y + SPACING_Y * 10, "Noxious Gas", "Hide noxious gas", NoRenderCfg.noNoxiousGas, v -> NoRenderCfg.noNoxiousGas = v);
        add(right, y + SPACING_Y * 10, "Geyser", "Hide geyser particles", NoRenderCfg.noGeyser, v -> NoRenderCfg.noGeyser = v);
    }

    private void addExtraParticleButtons(int left, int right, int y) {
        add(left, y, "Glow Particle", "Hide glow particles", NoRenderCfg.noGlowParticle, v -> NoRenderCfg.noGlowParticle = v);
        add(right, y, "Wax On/Off", "Hide wax particles", NoRenderCfg.noWax, v -> NoRenderCfg.noWax = v);
        add(left, y + SPACING_Y, "Scrape", "Hide scrape particles", NoRenderCfg.noScrape, v -> NoRenderCfg.noScrape = v);
        add(right, y + SPACING_Y, "Copper Flame", "Hide copper fire flame", NoRenderCfg.noCopperFireFlame, v -> NoRenderCfg.noCopperFireFlame = v);
        add(left, y + SPACING_Y * 2, "Nautilus", "Hide nautilus particles", NoRenderCfg.noNautilus, v -> NoRenderCfg.noNautilus = v);
        add(right, y + SPACING_Y * 2, "Dolphin", "Hide dolphin particles", NoRenderCfg.noDolphin, v -> NoRenderCfg.noDolphin = v);
        add(left, y + SPACING_Y * 3, "Underwater", "Hide underwater particles", NoRenderCfg.noUnderwater, v -> NoRenderCfg.noUnderwater = v);
        add(right, y + SPACING_Y * 3, "Reverse Portal", "Hide reverse portal", NoRenderCfg.noReversePortal, v -> NoRenderCfg.noReversePortal = v);
        add(left, y + SPACING_Y * 4, "End Rod", "Hide end rod particles", NoRenderCfg.noEndRod, v -> NoRenderCfg.noEndRod = v);
        add(right, y + SPACING_Y * 4, "Mycelium", "Hide mycelium particles", NoRenderCfg.noMycelium, v -> NoRenderCfg.noMycelium = v);
        add(left, y + SPACING_Y * 5, "Spit", "Hide spit particles", NoRenderCfg.noSpit, v -> NoRenderCfg.noSpit = v);
        add(right, y + SPACING_Y * 5, "Sneeze", "Hide sneeze particles", NoRenderCfg.noSneeze, v -> NoRenderCfg.noSneeze = v);
        add(left, y + SPACING_Y * 6, "Witch", "Hide witch particles", NoRenderCfg.noWitchParticle, v -> NoRenderCfg.noWitchParticle = v);
        add(right, y + SPACING_Y * 6, "Fishing", "Hide fishing particles", NoRenderCfg.noFishing, v -> NoRenderCfg.noFishing = v);
        add(left, y + SPACING_Y * 7, "Angry Villager", "Hide angry villager", NoRenderCfg.noAngryVillager, v -> NoRenderCfg.noAngryVillager = v);
        add(right, y + SPACING_Y * 7, "Happy Villager", "Hide happy villager", NoRenderCfg.noHappyVillager, v -> NoRenderCfg.noHappyVillager = v);
        add(left, y + SPACING_Y * 8, "Crimson Spore", "Hide crimson spores", NoRenderCfg.noCrimsonSpore, v -> NoRenderCfg.noCrimsonSpore = v);
        add(right, y + SPACING_Y * 8, "Warped Spore", "Hide warped spores", NoRenderCfg.noWarpedSpore, v -> NoRenderCfg.noWarpedSpore = v);
        add(left, y + SPACING_Y * 9, "Trial Spawner Eject", "Hide trial spawner ejection", NoRenderCfg.noTrialSpawnerEjection, v -> NoRenderCfg.noTrialSpawnerEjection = v);
        add(right, y + SPACING_Y * 9, "Ominous Item", "Hide ominous item spawn", NoRenderCfg.noOminousItemSpawn, v -> NoRenderCfg.noOminousItemSpawn = v);
        add(left, y + SPACING_Y * 10, "Item Slime", "Hide item slime particles", NoRenderCfg.noItemSlime, v -> NoRenderCfg.noItemSlime = v);
        add(right, y + SPACING_Y * 10, "Item Cobweb", "Hide item cobweb particles", NoRenderCfg.noItemCobweb, v -> NoRenderCfg.noItemCobweb = v);
        add(left, y + SPACING_Y * 11, "Item Snowball", "Hide item snowball particles", NoRenderCfg.noItemSnowball, v -> NoRenderCfg.noItemSnowball = v);
        add(right, y + SPACING_Y * 11, "Dust Plume", "Hide dust plume particles", NoRenderCfg.noDustPlume, v -> NoRenderCfg.noDustPlume = v);
        add(left, y + SPACING_Y * 12, "Dust Pillar", "Hide dust pillar particles", NoRenderCfg.noDustPillar, v -> NoRenderCfg.noDustPillar = v);
        add(right, y + SPACING_Y * 12, "Pause Growth", "Pause mob growth particles", NoRenderCfg.noPauseMobGrowth, v -> NoRenderCfg.noPauseMobGrowth = v);
        add(left, y + SPACING_Y * 13, "Sulfur Cubes", "Hide sulfur cube goo", NoRenderCfg.noSulfurCubes, v -> NoRenderCfg.noSulfurCubes = v);
        add(right, y + SPACING_Y * 13, "Reset Growth", "Hide reset mob growth", NoRenderCfg.noResetMobGrowth, v -> NoRenderCfg.noResetMobGrowth = v);
    }

    private void addMoreEntityButtons(int left, int right, int y) {
        add(left, y, "Minecarts", "Hide all minecarts", NoRenderCfg.noMinecarts, v -> NoRenderCfg.noMinecarts = v);
        add(right, y, "Boats", "Hide all boats", NoRenderCfg.noBoats, v -> NoRenderCfg.noBoats = v);
        add(left, y + SPACING_Y, "Paintings", "Hide paintings", NoRenderCfg.noPaintings, v -> NoRenderCfg.noPaintings = v);
        add(right, y + SPACING_Y, "Leash Knots", "Hide leash knots", NoRenderCfg.noLeashKnots, v -> NoRenderCfg.noLeashKnots = v);
        add(left, y + SPACING_Y * 2, "Falling Blocks", "Hide falling blocks", NoRenderCfg.noFallingBlocks, v -> NoRenderCfg.noFallingBlocks = v);
        add(right, y + SPACING_Y * 2, "Projectiles", "Hide arrows, snowballs, fireballs", NoRenderCfg.noProjectiles, v -> NoRenderCfg.noProjectiles = v);
    }

    private void addMiscButtons(int left, int right, int y) {
        add(left, y, "Block Crack", "Hide block breaking overlay", NoRenderCfg.noBlockBreakCrack, v -> NoRenderCfg.noBlockBreakCrack = v);
        add(right, y, "Entity Hitboxes", "Hide F3+B hitbox outlines", NoRenderCfg.noEntityHitboxOutline, v -> NoRenderCfg.noEntityHitboxOutline = v);
    }

    private record WidgetEntry(AbstractWidget widget, int originalX, int originalY) {
    }
}