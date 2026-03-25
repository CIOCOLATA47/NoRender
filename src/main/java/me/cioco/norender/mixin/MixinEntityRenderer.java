package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void noRenderEntities(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (NoRenderCfg.noDroppedItems && entity instanceof ItemEntity) {
            cir.setReturnValue(false);
            return;
        }
        if (NoRenderCfg.noItemFrames && entity instanceof ItemFrame) {
            cir.setReturnValue(false);
            return;
        }
        if (NoRenderCfg.noArmorStands && entity instanceof ArmorStand) {
            cir.setReturnValue(false);
            return;
        }
        if (NoRenderCfg.noExperienceOrbs && entity instanceof ExperienceOrb) {
            cir.setReturnValue(false);
        }
    }
}