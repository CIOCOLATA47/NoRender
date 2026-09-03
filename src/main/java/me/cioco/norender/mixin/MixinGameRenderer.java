package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "displayItemActivation", at = @At("HEAD"), cancellable = true)
    private void onShowFloatingItem(ItemStack itemStack, CallbackInfo ci) {
        if (!NoRenderUtil.isGameReady()) return;
        if (NoRenderCfg.noTotemAnimation && itemStack.is(Items.TOTEM_OF_UNDYING)) {
            ci.cancel();
        }
    }
}