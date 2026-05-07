package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.particle.FireworkParticles$Starter")
public class MixinFireworkStarter {

    @Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        if (NoRenderCfg.noFireworks) {
            ci.cancel();
        }
    }
}