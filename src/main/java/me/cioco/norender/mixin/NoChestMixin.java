package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntityRenderer.class)
public class NoChestMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelChest(CallbackInfo ci) {
        if (NoRenderCfg.noChests) {
            ci.cancel();
        }
    }
}