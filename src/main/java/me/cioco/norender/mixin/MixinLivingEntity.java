package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "spawnItemParticles", at = @At("HEAD"), cancellable = true)
    private void handleFoodParticleDisplay(ItemStack stack, int count, CallbackInfo ci) {
        if (NoRenderCfg.noEatParticles) {
            if (stack.getComponents().contains(DataComponentTypes.FOOD)) {
                ci.cancel();
            }
        }
    }
}