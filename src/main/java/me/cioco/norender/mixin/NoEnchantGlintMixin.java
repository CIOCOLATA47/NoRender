package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Display.ItemDisplay.ItemRenderState.class)
public class NoEnchantGlintMixin {

    @Inject(method = "itemStack", at = @At("HEAD"), cancellable = true)
    private void hideGlintOnDisplay(CallbackInfoReturnable<ItemStack> cir) {
        if (NoRenderCfg.noEnchantmentGlint) {
            ItemStack stack = cir.getReturnValue();
            if (stack != null && stack.isEnchanted()) {

            }
        }
    }
}