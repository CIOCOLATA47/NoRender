package me.cioco.norender.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRendererMisc {

    @Inject(
            method = "submitBlockDestroyAnimation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onBlockCrack(PoseStack poseStack,
                              SubmitNodeCollector submitNodeCollector,
                              LevelRenderState levelRenderState,
                              CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noBlockBreakCrack) {
            ci.cancel();
        }
    }

    @Inject(
            method = "executeOutline",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onEntityHitboxes(FeatureRenderDispatcher.PreparedFrame featureFrame, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noEntityHitboxOutline) {
            ci.cancel();
        }
    }
}