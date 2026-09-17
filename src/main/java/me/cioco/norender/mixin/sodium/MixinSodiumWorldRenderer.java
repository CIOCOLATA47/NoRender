package me.cioco.norender.mixin.sodium;

import me.cioco.norender.config.NoRenderCfg;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.caffeinemc.mods.sodium.client.util.FogParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = SodiumWorldRenderer.class, remap = false)
public class MixinSodiumWorldRenderer {

    @ModifyVariable(
            method = "setupTerrain",
            at = @At("HEAD"),
            argsOnly = true,
            remap = false
    )
    private FogParameters norender$killSodiumFog(FogParameters params) {
        if (!NoRenderCfg.noFog || params == null) return params;

        return new FogParameters(
                params.red(),
                params.green(),
                params.blue(),
                params.alpha(),
                Float.MAX_VALUE,
                Float.MAX_VALUE,
                Float.MAX_VALUE,
                Float.MAX_VALUE,
                params.cullDistance()
        );
    }
}