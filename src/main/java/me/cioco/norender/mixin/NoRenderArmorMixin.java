package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class NoRenderArmorMixin<
        S extends BipedEntityRenderState,
        M extends BipedEntityModel<S>,
        A extends BipedEntityModel<S>> {

    @Inject(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void skipPlayerArmorRender(MatrixStack matrixStack, OrderedRenderCommandQueue renderQueue, int light, S entityRenderState, float limbAngle, float limbDistance, CallbackInfo ci
    ) {
        if (entityRenderState instanceof PlayerEntityRenderState && NoRenderCfg.noArmor) {
            ci.cancel();
        }
    }
}
