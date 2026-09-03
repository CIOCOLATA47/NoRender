package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import me.cioco.norender.util.NoRenderUtil;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.decoration.painting.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void noRenderEntities(T entity, Frustum culler, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        if (!NoRenderUtil.isGameReady()) return;

        if (NoRenderCfg.noDroppedItems && entity instanceof ItemEntity) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noItemFrames && entity instanceof ItemFrame) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noArmorStands && entity instanceof ArmorStand) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noExperienceOrbs && entity instanceof ExperienceOrb) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noMinecarts && entity instanceof Minecart) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noBoats && entity instanceof Boat) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noPaintings && entity instanceof Painting) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noLeashKnots && entity instanceof LeashFenceKnotEntity) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noFallingBlocks && entity instanceof FallingBlockEntity) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noProjectiles && entity instanceof Projectile) {
            cir.setReturnValue(false);
        }
    }
}