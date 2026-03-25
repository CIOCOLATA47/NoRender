package me.cioco.norender.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public class NoRenderHeadFeatureMixin {

    @Inject(
            method = "submit",
            at = @At("HEAD"),
            cancellable = true
    )
    private void skipHeadRender(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LivingEntityRenderState state, float yRot, float xRot, CallbackInfo ci) {
        if (NoRenderCfg.noArmor) {
            ci.cancel();
        }
    }
}