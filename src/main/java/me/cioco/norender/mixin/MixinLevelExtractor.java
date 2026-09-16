package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.extract.LevelExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelExtractor.class)
public abstract class MixinLevelExtractor {

    @Inject(method = "extract", at = @At(value = "HEAD"), cancellable = true)
    private void onExtractParticles(DeltaTracker deltaTracker, Camera camera, float deltaPartialTick, CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noVaultParticles) {
            ci.cancel();
        }
    }
}