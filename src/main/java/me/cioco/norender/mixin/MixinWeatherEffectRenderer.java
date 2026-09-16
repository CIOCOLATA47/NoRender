package me.cioco.norender.mixin;

import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeatherEffectRenderer.class)
public abstract class MixinWeatherEffectRenderer {

    @Inject(
            method = "render(Lnet/minecraft/client/renderer/state/level/WeatherRenderState;Lcom/mojang/renderpearl/api/commands/RenderPass;Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void norender$cancelWeather(WeatherRenderState renderState, RenderPass renderPass, RenderPipeline renderPipeline, CallbackInfo ci) {
        if (NoRenderCfg.noWeather) ci.cancel();
    }
}