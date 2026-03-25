package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "spawnItemParticles", at = @At("HEAD"), cancellable = true)
    private void handleFoodParticleDisplay(ItemStack stack, int count, CallbackInfo ci) {
        if (NoRenderCfg.noEatParticles) {
            if (stack.has(DataComponents.FOOD)) {
                ci.cancel();
            }
        }
    }
}