package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer.class)
public class NoBeaconBeamMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelBeaconBeam(CallbackInfo ci) {
        if (NoRenderCfg.noBeaconBeam) {
            ci.cancel();
        }
    }
}