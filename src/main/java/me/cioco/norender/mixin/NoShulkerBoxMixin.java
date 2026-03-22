package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public class NoShulkerBoxMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelShulker(CallbackInfo ci) {
        if (NoRenderCfg.noShulkerBoxes) {
            ci.cancel();
        }
    }
}