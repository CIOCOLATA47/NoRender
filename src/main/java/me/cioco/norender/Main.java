package me.cioco.norender;

import com.mojang.blaze3d.platform.InputConstants;
import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.gui.NoRenderGui;
import me.cioco.norender.util.NoRenderUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class Main implements ClientModInitializer {

    public static final KeyMapping.Category CATEGORY_NORENDER =
            KeyMapping.Category.register(
                    Identifier.fromNamespaceAndPath(
                            "norender",
                            "key_category"
                    )
            );

    public static KeyMapping guiKeyBinding;

    @Override
    public void onInitializeClient() {
        NoRenderCfg.loadConfiguration();

        guiKeyBinding = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.norender.open_gui",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_UNKNOWN,
                        CATEGORY_NORENDER
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            NoRenderUtil.tick();

            if (!NoRenderUtil.isGameReady()) {
                return;
            }

            if (guiKeyBinding.consumeClick()) {
                client.setScreenAndShow(
                        new NoRenderGui(client.gui.screen())
                );
            }
        });
    }
}