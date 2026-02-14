package me.cioco.norender.gui;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NoRenderGui extends Screen {

    private static final int SPACING_Y = 24;
    private static final int SECTION_MARGIN = 35;
    private static final int TITLE_HEIGHT = 20;
    private final Screen parent;
    private final List<ClickableWidget> scrollableWidgets = new ArrayList<>();
    private int scrollOffset = 0;
    private int maxScroll;
    private int contentHeight;
    private ButtonWidget doneButton;

    public NoRenderGui(Screen parent) {
        super(Text.literal("NoRender Options"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.clearChildren();
        this.scrollableWidgets.clear();

        int centerX = width / 2;
        int leftCol = centerX - 155;
        int rightCol = centerX + 5;
        int currentY = 70;

        addOverlayButtons(leftCol, rightCol, currentY);
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        addWorldButtons(leftCol, rightCol, currentY);
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        addCommonParticleButtons(leftCol, rightCol, currentY);
        currentY += (SPACING_Y * 8) + SECTION_MARGIN;

        addNewEraParticleButtons(leftCol, rightCol, currentY);
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        addTechnicalButtons(leftCol, rightCol, currentY);
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;

        contentHeight = currentY;
        maxScroll = Math.max(0, contentHeight - (height - 90));

        doneButton = ButtonWidget.builder(
                Text.literal("SAVE & EXIT").formatted(Formatting.AQUA, Formatting.BOLD),
                b -> this.close()
        ).dimensions(centerX - 100, height - 30, 200, 20).build();

        addDrawableChild(doneButton);
        updateWidgetPositions();
    }

    private void updateWidgetPositions() {
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderInGameBackground(ctx);

        int cx = width / 2;
        int panelW = 325;
        int panelX = cx - (panelW / 2);

        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("NoRender").formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE), cx, 15, 0xFFFFFFFF);

        ctx.enableScissor(0, 40, width, height - 40);

        int currentY = 70 - scrollOffset;
        renderSectionGroup(ctx, panelX, currentY, panelW, 5, "Overlays");
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;
        renderSectionGroup(ctx, panelX, currentY, panelW, 5, "World & Entities");
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;
        renderSectionGroup(ctx, panelX, currentY, panelW, 8, "Common Particles");
        currentY += (SPACING_Y * 8) + SECTION_MARGIN;
        renderSectionGroup(ctx, panelX, currentY, panelW, 5, "Sculk & Trial Chambers");
        currentY += (SPACING_Y * 5) + SECTION_MARGIN;
        renderSectionGroup(ctx, panelX, currentY, panelW, 4, "Nether, End & Game");

        for (var element : children()) {
            if (element instanceof ClickableWidget btn && btn != doneButton) {
                btn.visible = (btn.getY() + btn.getHeight() > 40 && btn.getY() < height - 40);
                if (btn.visible) {
                    btn.render(ctx, mouseX, mouseY, delta);
                }
            }
        }
        ctx.disableScissor();

        doneButton.render(ctx, mouseX, mouseY, delta);
        drawScrollBar(ctx);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (maxScroll > 0) {
            int oldOffset = scrollOffset;
            scrollOffset = (int) Math.max(0, Math.min(maxScroll, scrollOffset - (verticalAmount * 25)));

            int diff = oldOffset - scrollOffset;
            for (ClickableWidget widget : scrollableWidgets) {
                widget.setY(widget.getY() + diff);
            }
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void add(int x, int y, String label, String desc, boolean val, Consumer<Boolean> action) {
        ButtonWidget btn = createToggleButton(x, y, label, desc, val, action);
        scrollableWidgets.add(btn);
        addDrawableChild(btn);
    }

    private void renderSectionGroup(DrawContext ctx, int x, int y, int w, int buttonRows, String title) {
        int contentH = (buttonRows * SPACING_Y);
        drawStyledPanel(ctx, x, y - TITLE_HEIGHT - 5, w, contentH + TITLE_HEIGHT + 10);
        ctx.drawTextWithShadow(textRenderer, "§b§l» §f" + title, x + 8, y - TITLE_HEIGHT + 1, 0xFFFFFFFF);
        ctx.fill(x + 5, y - 6, x + w - 5, y - 5, 0x8000FFFF);
    }

    private void drawStyledPanel(DrawContext context, int x, int y, int width, int height) {
        context.fill(x, y, x + width, y + height, 0x90000000);
        context.fill(x, y, x + 2, y + height, 0xFF00FFFF);
        context.fill(x + width - 2, y, x + width, y + height, 0xFF00FFFF);
    }

    private void drawScrollBar(DrawContext ctx) {
        if (maxScroll <= 0) return;
        int trackHeight = height - 80;
        int barHeight = Math.max(20, (trackHeight * trackHeight) / contentHeight);
        int barY = 40 + (int) ((trackHeight - barHeight) * ((float) scrollOffset / maxScroll));
        ctx.fill(width - 6, barY, width - 2, barY + barHeight, 0xFF00FFFF);
    }

    private ButtonWidget createToggleButton(int x, int y, String label, String desc, boolean init, Consumer<Boolean> action) {
        return ButtonWidget.builder(getToggleText(label, init), b -> {
                    boolean currentlyOn = b.getMessage().getString().contains("ON");
                    action.accept(!currentlyOn);
                    b.setMessage(getToggleText(label, !currentlyOn));
                }).dimensions(x, y, 150, 20)
                .tooltip(Tooltip.of(Text.literal("§e" + desc)))
                .build();
    }

    private Text getToggleText(String label, boolean value) {
        return Text.literal(label + ": ").append(value ? Text.literal("ON").formatted(Formatting.GREEN) : Text.literal("OFF").formatted(Formatting.RED));
    }

    private void addOverlayButtons(int left, int right, int y) {
        add(left, y, "Portals", "Hide portal effect", NoRenderCfg.noPortalOverlay, v -> NoRenderCfg.noPortalOverlay = v);
        add(right, y, "Fire", "Hide fire overlay", NoRenderCfg.noFireOverlay, v -> NoRenderCfg.noFireOverlay = v);
        add(left, y + SPACING_Y, "Nausea", "Hide nausea tilt", NoRenderCfg.noNausea, v -> NoRenderCfg.noNausea = v);
        add(right, y + SPACING_Y, "Darkness", "Hide darkness", NoRenderCfg.noDarkness, v -> NoRenderCfg.noDarkness = v);
        add(left, y + (SPACING_Y * 2), "Pumpkin", "Hide pumpkin blur", NoRenderCfg.noPumpkinOverlay, v -> NoRenderCfg.noPumpkinOverlay = v);
        add(right, y + (SPACING_Y * 2), "Vignette", "Hide dark corners", NoRenderCfg.noVignette, v -> NoRenderCfg.noVignette = v);
        add(left, y + (SPACING_Y * 3), "Enchant Glint", "Hide item glow", NoRenderCfg.noEnchantmentGlint, v -> NoRenderCfg.noEnchantmentGlint = v);
        add(right, y + (SPACING_Y * 3), "PowderSnow", "Removes powdersnow overlay", NoRenderCfg.noPowderedSnowOverlay, v -> NoRenderCfg.noPowderedSnowOverlay = v);
        add(left, y + (SPACING_Y * 4), "Spyglass", "Hide spyglass container", NoRenderCfg.noSpyglassOverlay, v -> NoRenderCfg.noSpyglassOverlay = v);
        add(right, y + (SPACING_Y * 4), "Liquid", "Hide water/lava tint", NoRenderCfg.noLiquidOverlay, v -> NoRenderCfg.noLiquidOverlay = v);
    }

    private void addWorldButtons(int left, int right, int y) {
        add(left, y, "Weather", "Hide rain/snow", NoRenderCfg.noWeather, v -> NoRenderCfg.noWeather = v);
        add(right, y, "In Wall", "Hide inside-block tint", NoRenderCfg.noInWallOverlay, v -> NoRenderCfg.noInWallOverlay = v);
        add(left, y + SPACING_Y, "Items", "Hide dropped items", NoRenderCfg.noDroppedItems, v -> NoRenderCfg.noDroppedItems = v);
        add(right, y + SPACING_Y, "XP Orbs", "Hide XP Orbs", NoRenderCfg.noExperienceOrbs, v -> NoRenderCfg.noExperienceOrbs = v);
        add(left, y + (SPACING_Y * 2), "Frames", "Hide item frames", NoRenderCfg.noItemFrames, v -> NoRenderCfg.noItemFrames = v);
        add(right, y + (SPACING_Y * 2), "Stands", "Hide armor stands", NoRenderCfg.noArmorStands, v -> NoRenderCfg.noArmorStands = v);
        add(left, y + (SPACING_Y * 3), "Armor", "Hide player armor", NoRenderCfg.noArmor, v -> NoRenderCfg.noArmor = v);
        add(right, y + (SPACING_Y * 3), "Potion Icons", "Hide HUD effect icons", NoRenderCfg.noPotionIcons, v -> NoRenderCfg.noPotionIcons = v);
        add(left, y + (SPACING_Y * 4), "Totem Anim", "Hide big totem icon", NoRenderCfg.noTotemAnimation, v -> NoRenderCfg.noTotemAnimation = v);
    }

    private void addCommonParticleButtons(int left, int right, int y) {
        add(left, y, "Explosions", "Hide blasts", NoRenderCfg.noExplosions, v -> NoRenderCfg.noExplosions = v);
        add(right, y, "Fireworks", "Hide sparks", NoRenderCfg.noFireworks, v -> NoRenderCfg.noFireworks = v);
        add(left, y + SPACING_Y, "Campfire", "Hide smoke", NoRenderCfg.noCampfireSmoke, v -> NoRenderCfg.noCampfireSmoke = v);
        add(right, y + SPACING_Y, "Hearts", "Hide love hearts", NoRenderCfg.noHeartParticles, v -> NoRenderCfg.noHeartParticles = v);
        add(left, y + (SPACING_Y * 2), "Eating", "Hide food crumbs", NoRenderCfg.noEatParticles, v -> NoRenderCfg.noEatParticles = v);
        add(right, y + (SPACING_Y * 2), "Blocks", "Hide break particles", NoRenderCfg.noBlockBreakParticles, v -> NoRenderCfg.noBlockBreakParticles = v);
        add(left, y + (SPACING_Y * 3), "Potion Swap", "Hide swirls", NoRenderCfg.noPotionParticles, v -> NoRenderCfg.noPotionParticles = v);
        add(right, y + (SPACING_Y * 3), "Damage", "Hide hit indicators", NoRenderCfg.noDamageParticles, v -> NoRenderCfg.noDamageParticles = v);
        add(left, y + (SPACING_Y * 4), "Sweep", "Hide sword sweep", NoRenderCfg.noSweepParticles, v -> NoRenderCfg.noSweepParticles = v);
        add(right, y + (SPACING_Y * 4), "Fall Dust", "Hide sand dust", NoRenderCfg.noFallingDust, v -> NoRenderCfg.noFallingDust = v);
        add(left, y + (SPACING_Y * 5), "Flame", "Hide torch/fire", NoRenderCfg.noFlameParticles, v -> NoRenderCfg.noFlameParticles = v);
        add(right, y + (SPACING_Y * 5), "Smoke", "Hide basic smoke", NoRenderCfg.noSmokeParticles, v -> NoRenderCfg.noSmokeParticles = v);
        add(left, y + (SPACING_Y * 6), "Bubbles", "Hide water bubbles", NoRenderCfg.noBubbleParticles, v -> NoRenderCfg.noBubbleParticles = v);
        add(right, y + (SPACING_Y * 6), "Crits", "Hide crit sparks", NoRenderCfg.noCritParticles, v -> NoRenderCfg.noCritParticles = v);
        add(left, y + (SPACING_Y * 7), "Clouds", "Hide cloud particles", NoRenderCfg.noCloudParticles, v -> NoRenderCfg.noCloudParticles = v);
        add(right, y + (SPACING_Y * 7), "Totem Part.", "Hide totem particles", NoRenderCfg.noTotemParticles, v -> NoRenderCfg.noTotemParticles = v);
    }

    private void addNewEraParticleButtons(int left, int right, int y) {
        add(left, y, "Trial Spawner", "Disable particle effects when Trial Spawners are detected", NoRenderCfg.noTrialSpawnerDetection, v -> NoRenderCfg.noTrialSpawnerDetection = v);
        add(right, y, "Ominous", "Remove ominous visual effects from Trial events", NoRenderCfg.noOminousSpawning, v -> NoRenderCfg.noOminousSpawning = v);
        add(left, y + SPACING_Y, "Infested", "Hide silverfish particle effects from infested blocks", NoRenderCfg.noInfestedParticles, v -> NoRenderCfg.noInfestedParticles = v);
        add(right, y + SPACING_Y, "Wind Explosion", "Suppress gust/blast particles from wind events", NoRenderCfg.noWindExplosion, v -> NoRenderCfg.noWindExplosion = v);
        add(left, y + (SPACING_Y * 2), "Trial Flame", "Disable flame effects from Trial Spawner fire traps", NoRenderCfg.noTrialSpawnerFlame, v -> NoRenderCfg.noTrialSpawnerFlame = v);
        add(right, y + (SPACING_Y * 2), "Sonic Boom", "Remove shockwave particles from Warden attacks", NoRenderCfg.noSonicBoom, v -> NoRenderCfg.noSonicBoom = v);
        add(left, y + (SPACING_Y * 3), "Sculk Charge", "Hide Sculk Charge particle effects during spreading", NoRenderCfg.noSculkCharge, v -> NoRenderCfg.noSculkCharge = v);
        add(right, y + (SPACING_Y * 3), "Vibrations", "Remove Sculk vibration line particles for sensors", NoRenderCfg.noVibration, v -> NoRenderCfg.noVibration = v);
        add(left, y + (SPACING_Y * 4), "Shrieks", "Suppress loud Warden shriek particle effects", NoRenderCfg.noShriekParticle, v -> NoRenderCfg.noShriekParticle = v);
        add(right, y + (SPACING_Y * 4), "Cobwebs", "Hide cobweb visual particles in Trial areas", NoRenderCfg.noCobwebParticles, v -> NoRenderCfg.noCobwebParticles = v);
    }


    private void addTechnicalButtons(int left, int right, int y) {
        add(left, y, "Nether Ash", "Basalt Deltas ash", NoRenderCfg.noAsh, v -> NoRenderCfg.noAsh = v);
        add(right, y, "Soul Particles", "Soul sand effects", NoRenderCfg.noSoulParticles, v -> NoRenderCfg.noSoulParticles = v);
        add(left, y + SPACING_Y, "Dragon Breath", "Ender Dragon gas", NoRenderCfg.noDragonBreath, v -> NoRenderCfg.noDragonBreath = v);
        add(right, y + SPACING_Y, "Drips", "Water/Lava drips", NoRenderCfg.noDripParticles, v -> NoRenderCfg.noDripParticles = v);
    }

    @Override
    public void close() {
        NoRenderCfg.saveConfiguration();
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}