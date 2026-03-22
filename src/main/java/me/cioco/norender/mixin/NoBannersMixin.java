package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BannerBlockEntityRenderer.class)
public class NoBannersMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void cancelBanner(CallbackInfo ci) {
        if (NoRenderCfg.noBanners) {
            ci.cancel();
        }
    }
}