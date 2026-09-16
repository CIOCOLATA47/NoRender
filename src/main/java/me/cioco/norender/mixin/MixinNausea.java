package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class MixinNausea {

    @Redirect(
            method = "renderLevel",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/state/level/PlayerRenderState;nauseaEffectIntensity:F",
                    opcode = Opcodes.GETFIELD
            )
    )
    private float norender$noNausea(PlayerRenderState state) {
        return NoRenderCfg.noNausea ? 0.0F : state.nauseaEffectIntensity;
    }
}