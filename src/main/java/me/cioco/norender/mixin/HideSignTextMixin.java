package me.cioco.norender.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignRenderer.class)
public abstract class HideSignTextMixin {

    @Inject(method = "submitSignText", at = @At("HEAD"), cancellable = true)
    private void skipSignText(SignRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, SignText signText, CallbackInfo ci) {
        if (NoRenderCfg.noSignText) {
            ci.cancel();
        }
    }
}