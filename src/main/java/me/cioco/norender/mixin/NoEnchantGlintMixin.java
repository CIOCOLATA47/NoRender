package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class NoEnchantGlintMixin {

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II[ILjava/util/List;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/render/item/ItemRenderState$Glint;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static ItemRenderState.Glint checkGlintOverride(ItemRenderState.Glint currentGlint) {
        return NoRenderCfg.noEnchantmentGlint ? ItemRenderState.Glint.NONE : currentGlint;
    }
}
