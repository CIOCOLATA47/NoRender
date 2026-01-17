package me.cioco.norender.gui;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class NoRenderGui extends Screen {
    private final Screen parent;

    private int overlayY, worldY, particleY;
    private final int spacingY = 24;

    public NoRenderGui(Screen parent) {
        super(Text.literal("NoRender Options"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int leftCol = centerX - 155;
        int rightCol = centerX + 5;

        this.overlayY = 35;
        addOverlayButtons(leftCol, rightCol);

        this.worldY = overlayY + (spacingY * 6) + 20;

        this.addDrawableChild(createToggleButton(leftCol, worldY + 5, "Weather", "Hide rain/snow", NoRenderCfg.noWeather, v -> NoRenderCfg.noWeather = v));
        this.addDrawableChild(createToggleButton(rightCol, worldY + 5, "Totem Pop", "Hide totem animation", NoRenderCfg.noTotemAnimation, v -> NoRenderCfg.noTotemAnimation = v));

        this.addDrawableChild(createToggleButton(leftCol, worldY + 5 + spacingY, "Items", "Hide dropped item entities", NoRenderCfg.noDroppedItems, v -> NoRenderCfg.noDroppedItems = v));
        this.addDrawableChild(createToggleButton(rightCol, worldY + 5 + spacingY, "Frames", "Hide item & glow item frames", NoRenderCfg.noItemFrames, v -> NoRenderCfg.noItemFrames = v));

        this.addDrawableChild(createToggleButton(leftCol, worldY + 5 + (spacingY * 2), "Stands", "Hide armor stand entities", NoRenderCfg.noArmorStands, v -> NoRenderCfg.noArmorStands = v));

        this.particleY = worldY + (spacingY * 3) + 20;

        addParticleButtons(leftCol, rightCol);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("DONE").formatted(Formatting.AQUA, Formatting.BOLD), (button) -> {
            this.close();
        }).dimensions(centerX - 100, this.height - 25, 200, 20).build());
    }

    private void addOverlayButtons(int leftCol, int rightCol) {
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5, "Portals", "Hide portal effect", NoRenderCfg.noPortalOverlay, v -> NoRenderCfg.noPortalOverlay = v));
        this.addDrawableChild(createToggleButton(rightCol, overlayY + 5, "Fire", "Hide fire overlay", NoRenderCfg.noFireOverlay, v -> NoRenderCfg.noFireOverlay = v));
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5 + spacingY, "Nausea", "Hide nausea tilt", NoRenderCfg.noNausea, v -> NoRenderCfg.noNausea = v));
        this.addDrawableChild(createToggleButton(rightCol, overlayY + 5 + spacingY, "Darkness", "Hide darkness", NoRenderCfg.noDarkness, v -> NoRenderCfg.noDarkness = v));
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5 + (spacingY * 2), "Pumpkin", "Hide pumpkin blur", NoRenderCfg.noPumpkinOverlay, v -> NoRenderCfg.noPumpkinOverlay = v));
        this.addDrawableChild(createToggleButton(rightCol, overlayY + 5 + (spacingY * 2), "Vignette", "Hide dark corners", NoRenderCfg.noVignette, v -> NoRenderCfg.noVignette = v));
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5 + (spacingY * 3), "Spyglass", "Hide telescope ring", NoRenderCfg.noSpyglassOverlay, v -> NoRenderCfg.noSpyglassOverlay = v));
        this.addDrawableChild(createToggleButton(rightCol, overlayY + 5 + (spacingY * 3), "Liquid", "Hide water overlay", NoRenderCfg.noLiquidOverlay, v -> NoRenderCfg.noLiquidOverlay = v));
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5 + (spacingY * 4), "In-Wall", "Hide block overlay", NoRenderCfg.noInWallOverlay, v -> NoRenderCfg.noInWallOverlay = v));
        this.addDrawableChild(createToggleButton(rightCol, overlayY + 5 + (spacingY * 4), "Snow Blur", "Hide powder snow blur", NoRenderCfg.noPowderedSnowOverlay, v -> NoRenderCfg.noPowderedSnowOverlay = v));
        this.addDrawableChild(createToggleButton(leftCol, overlayY + 5 + (spacingY * 5), "Potions", "Hide HUD icons", NoRenderCfg.noPotionIcons, v -> NoRenderCfg.noPotionIcons = v));
    }

    private void addParticleButtons(int leftCol, int rightCol) {
        int[] rows = {0, 1, 2, 3, 4, 5, 6};
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 0), "Explosions", "Hide explosion blast", NoRenderCfg.noExplosions, v -> NoRenderCfg.noExplosions = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 0), "Fireworks", "Hide firework sparks", NoRenderCfg.noFireworks, v -> NoRenderCfg.noFireworks = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 1), "Campfire", "Hide campfire smoke", NoRenderCfg.noCampfireSmoke, v -> NoRenderCfg.noCampfireSmoke = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 1), "Hearts", "Hide taming/breeding hearts", NoRenderCfg.noHeartParticles, v -> NoRenderCfg.noHeartParticles = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 2), "Eating", "Hide food crumbs", NoRenderCfg.noEatParticles, v -> NoRenderCfg.noEatParticles = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 2), "Blocks", "Hide break particles", NoRenderCfg.noBlockBreakParticles, v -> NoRenderCfg.noBlockBreakParticles = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 3), "Totem", "Hide totem particles", NoRenderCfg.noTotemParticles, v -> NoRenderCfg.noTotemParticles = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 3), "Potion", "Hide entity potion swirls", NoRenderCfg.noPotionParticles, v -> NoRenderCfg.noPotionParticles = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 4), "Sonic Boom", "Hide Warden blast", NoRenderCfg.noSonicBoom, v -> NoRenderCfg.noSonicBoom = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 4), "Vibrations", "Hide sculk lines", NoRenderCfg.noVibration, v -> NoRenderCfg.noVibration = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 5), "Damage", "Hide hit indicators", NoRenderCfg.noDamageParticles, v -> NoRenderCfg.noDamageParticles = v));
        this.addDrawableChild(createToggleButton(rightCol, particleY + 5 + (spacingY * 5), "Sweep", "Hide sweep attack", NoRenderCfg.noSweepParticles, v -> NoRenderCfg.noSweepParticles = v));
        this.addDrawableChild(createToggleButton(leftCol, particleY + 5 + (spacingY * 6), "Fall Dust", "Hide sand dust", NoRenderCfg.noFallingDust, v -> NoRenderCfg.noFallingDust = v));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderInGameBackground(context);
        int centerX = this.width / 2;
        int pWidth = 330;
        int pX = centerX - 165;

        drawStyledPanel(context, pX, overlayY - 15, pWidth, (spacingY * 6) + 20);
        drawStyledPanel(context, pX, worldY - 15, pWidth, (spacingY * 3) + 20);
        drawStyledPanel(context, pX, particleY - 15, pWidth, (spacingY * 7) + 20);

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("NoRender SETTINGS").formatted(Formatting.AQUA, Formatting.BOLD, Formatting.UNDERLINE), centerX, 10, 0xFFFFFFFF);

        context.drawTextWithShadow(this.textRenderer, "§d§l> §fOverlays", pX + 7, overlayY - 10, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "§d§l> §fWorld & Entity", pX + 7, worldY - 10, 0xFFFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "§d§l> §fParticles", pX + 7, particleY - 10, 0xFFFFFFFF);
    }

    private void drawStyledPanel(DrawContext context, int x, int y, int width, int height) {
        context.fill(x, y, x + width, y + height, 0x90000000);
        context.fill(x, y, x + 2, y + height, 0xFF00FFFF);
        context.fill(x + width - 2, y, x + width, y + height, 0xFF00FFFF);
    }

    @Override
    public void close() {
        NoRenderCfg.saveConfiguration();
        super.close();
        if (this.client != null) this.client.setScreen(this.parent);
    }

    private ButtonWidget createToggleButton(int x, int y, String label, String desc, boolean initVal, Consumer<Boolean> action) {
        return ButtonWidget.builder(getToggleText(label, initVal), (button) -> {
            boolean newVal = !button.getMessage().getString().contains("ON");
            action.accept(newVal);
            button.setMessage(getToggleText(label, newVal));
        }).dimensions(x, y, 150, 20).tooltip(Tooltip.of(Text.literal("§e" + desc))).build();
    }

    private Text getToggleText(String label, boolean value) {
        return Text.literal(label + ": ").append(value ? Text.literal("ON").formatted(Formatting.GREEN) : Text.literal("OFF").formatted(Formatting.RED));
    }
}