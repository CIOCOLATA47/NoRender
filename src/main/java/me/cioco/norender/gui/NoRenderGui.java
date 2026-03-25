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
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        addWorldButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addCommonParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 8) + SECTION_MARGIN;

        addNewEraParticleButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        addTechnicalButtons(leftCol, rightCol, currentY + 5);
        currentY += (SPACING_Y * 6) + SECTION_MARGIN;

        contentHeight = currentY;
        maxScroll = Math.max(0, contentHeight - (height - 90));

        doneButton = Button.builder(
                Component.literal("SAVE & EXIT").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD),
                b -> this.onClose()
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
            renderSectionGroup(ctx, panelX, y, panelW, 5, "Overlays");
            y += (SPACING_Y * 5) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "World & Entities");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 8, "Common Particles");
            y += (SPACING_Y * 8) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "Sculk & Trial Chambers");
            y += (SPACING_Y * 6) + SECTION_MARGIN;
            renderSectionGroup(ctx, panelX, y, panelW, 6, "Nether, End & Game");
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
            scrollOffset = (int) Math.max(0, Math.min(maxScroll, scrollOffset - (verticalAmount * 25)));
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
        if (minecraft != null) minecraft.setScreen(parent);
    }

    private void addOverlayButtons(int left, int right, int y) {
        add(left, y, "Portals", "Hide portal effect", NoRenderCfg.noPortalOverlay, v -> NoRenderCfg.noPortalOverlay = v);
        add(right, y, "Fire", "Hide fire overlay", NoRenderCfg.noFireOverlay, v -> NoRenderCfg.noFireOverlay = v);
        add(left, y + SPACING_Y, "Nausea", "Hide nausea tilt", NoRenderCfg.noNausea, v -> NoRenderCfg.noNausea = v);
        add(right, y + SPACING_Y, "Darkness", "Hide darkness", NoRenderCfg.noDarkness, v -> NoRenderCfg.noDarkness = v);
        add(left, y + SPACING_Y * 2, "Pumpkin", "Hide pumpkin blur", NoRenderCfg.noPumpkinOverlay, v -> NoRenderCfg.noPumpkinOverlay = v);
        add(right, y + SPACING_Y * 2, "Vignette", "Hide dark corners", NoRenderCfg.noVignette, v -> NoRenderCfg.noVignette = v);
        add(left, y + SPACING_Y * 3, "Enchant Glint", "Hide item glow", NoRenderCfg.noEnchantmentGlint, v -> NoRenderCfg.noEnchantmentGlint = v);
        add(right, y + SPACING_Y * 3, "PowderSnow", "Removes powdersnow overlay", NoRenderCfg.noPowderedSnowOverlay, v -> NoRenderCfg.noPowderedSnowOverlay = v);
        add(left, y + SPACING_Y * 4, "Spyglass", "Hide spyglass container", NoRenderCfg.noSpyglassOverlay, v -> NoRenderCfg.noSpyglassOverlay = v);
        add(right, y + SPACING_Y * 4, "Liquid", "Hide water/lava tint", NoRenderCfg.noLiquidOverlay, v -> NoRenderCfg.noLiquidOverlay = v);
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
        add(right, y + SPACING_Y * 4, "Grass & Flowers", "Hide grass and flowers", NoRenderCfg.noGrassAndFlowers, v -> {
            NoRenderCfg.noGrassAndFlowers = v;
            if (minecraft != null && minecraft.levelRenderer != null) {
                minecraft.levelRenderer.allChanged();
            }
        });
        add(left, y + SPACING_Y * 5, "Fog", "Hide all fog", NoRenderCfg.noFog, v -> NoRenderCfg.noFog = v);
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
        add(left, y + SPACING_Y * 2, "Chests", "Hide chest models", NoRenderCfg.noChests, v -> NoRenderCfg.noChests = v);
        add(right, y + SPACING_Y * 2, "Boss Bar", "Hide boss health bar", NoRenderCfg.noBossBar, v -> NoRenderCfg.noBossBar = v);
        add(left, y + SPACING_Y * 3, "Scoreboard", "Hide sidebar scoreboard", NoRenderCfg.noScoreboard, v -> NoRenderCfg.noScoreboard = v);
        add(right, y + SPACING_Y * 3, "Banners", "Hide banner models", NoRenderCfg.noBanners, v -> NoRenderCfg.noBanners = v);
        add(left, y + SPACING_Y * 4, "Shulker Boxes", "Hide shulker box models", NoRenderCfg.noShulkerBoxes, v -> NoRenderCfg.noShulkerBoxes = v);
        add(right, y + SPACING_Y * 4, "Sign Text", "Hide text rendered on signs", NoRenderCfg.noSignText, v -> NoRenderCfg.noSignText = v);
        add(left, y + SPACING_Y * 5, "Beacon Beam", "Hide beacon beam column", NoRenderCfg.noBeaconBeam, v -> NoRenderCfg.noBeaconBeam = v);
        add(right, y + SPACING_Y * 5, "Conduit Eye", "Hide conduit effects", NoRenderCfg.noConduitEye, v -> NoRenderCfg.noConduitEye = v);
    }

    private record WidgetEntry(AbstractWidget widget, int originalX, int originalY) {
    }
}