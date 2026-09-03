package me.cioco.norender.util;

import net.minecraft.client.Minecraft;

public final class NoRenderUtil {

    private NoRenderUtil() {}

    private static boolean ready = false;

    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        ready = mc.gui.overlay() == null && mc.player != null && mc.level != null;
    }

    public static boolean isGameReady() {
        return ready;
    }
	
    public static boolean shouldCancel() {
        return !ready;
    }
}