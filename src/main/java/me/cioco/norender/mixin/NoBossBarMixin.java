package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class NoBossBarMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelBossBar(DrawContext context, CallbackInfo ci) {
        if (NoRenderCfg.noBossBar) {
            ci.cancel();
        }
    }
}