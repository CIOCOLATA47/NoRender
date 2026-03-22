package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.block.entity.AbstractSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.SignBlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignBlockEntityRenderer.class)
public abstract class HideSignTextMixin {

    @Inject(method = "renderText", at = @At("HEAD"), cancellable = true)
    private void skipSignText(SignBlockEntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, boolean front, CallbackInfo ci) {
        if (NoRenderCfg.noSignText) {
            ci.cancel();
        }
    }
}