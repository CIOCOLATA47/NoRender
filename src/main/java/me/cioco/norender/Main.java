package me.cioco.norender;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.gui.NoRenderGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Main implements ClientModInitializer {

    public static final NoRenderCfg config = new NoRenderCfg();
    public static final KeyBinding.Category CATEGORY_NORENDER =
            KeyBinding.Category.create(Identifier.of("norender", "key_category"));
    public static KeyBinding guiBind;

    private static void openGui(MinecraftClient client) {
        if (client == null) return;
        client.setScreen(new NoRenderGui(client.currentScreen));
    }

    @Override
    public void onInitializeClient() {

        NoRenderCfg.loadConfiguration();

        guiBind = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.norender.open_gui",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        CATEGORY_NORENDER
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiBind.wasPressed()) {
                openGui(client);
            }
        });
    }
}
