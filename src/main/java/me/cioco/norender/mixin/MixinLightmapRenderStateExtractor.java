package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapRenderStateExtractor.class)
public class MixinLightmapRenderStateExtractor {
    @Inject(method = "extract", at = @At("RETURN"))
    private void noDarkness(LightmapRenderState renderState, float partialTicks, CallbackInfo ci) {
        if (!NoRenderUtil.shouldCancel()) return;
        if (NoRenderCfg.noDarkness) {
            renderState.darknessEffectScale = 0.0F;
        }
    }
}