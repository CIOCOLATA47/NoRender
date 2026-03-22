package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.block.entity.ConduitBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConduitBlockEntityRenderer.class)
public class NoConduitEyeMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelConduitEye(CallbackInfo ci) {
        if (NoRenderCfg.noConduitEye) {
            ci.cancel();
        }
    }
}