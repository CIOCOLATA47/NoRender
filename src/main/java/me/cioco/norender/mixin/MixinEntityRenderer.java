package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(
            method = "shouldRender",
            at = @At("HEAD"),
            cancellable = true
    )
    private void noRenderEntities(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir
    ) {
        if (NoRenderCfg.noDroppedItems && entity instanceof ItemEntity) {
            cir.setReturnValue(false);
            return;
        }

        if (NoRenderCfg.noItemFrames && entity instanceof ItemFrameEntity) {
            cir.setReturnValue(false);
            return;
        }

        if (NoRenderCfg.noArmorStands && entity instanceof ArmorStandEntity) {
            cir.setReturnValue(false);
        }
    }
}
