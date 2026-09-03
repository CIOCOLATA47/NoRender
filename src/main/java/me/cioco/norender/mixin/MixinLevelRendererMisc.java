package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class MixinLevelRendererMisc {

    @Inject(
            method = "submitBlockDestroyAnimation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onBlockCrack(CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noBlockBreakCrack) {
            ci.cancel();
        }
    }

    @Inject(
            method = "doEntityOutline",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onEntityHitboxes(CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noEntityHitboxOutline) {
            ci.cancel();
        }
    }
}