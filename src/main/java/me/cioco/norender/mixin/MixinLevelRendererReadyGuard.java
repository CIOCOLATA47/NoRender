package me.cioco.norender.mixin;

import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRendererReadyGuard {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void norender$skipUntilReady(CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) {
            ci.cancel();
        }
    }
}