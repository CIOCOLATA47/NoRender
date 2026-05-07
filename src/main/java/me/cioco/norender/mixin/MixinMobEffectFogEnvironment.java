package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.fog.environment.MobEffectFogEnvironment;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectFogEnvironment.class)
public abstract class MixinMobEffectFogEnvironment {

    @Shadow
    public abstract Holder<MobEffect> getMobEffect();

    @Inject(method = "isApplicable", at = @At("RETURN"), cancellable = true)
    private void onIsApplicable(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ()) return;

        Holder<MobEffect> effect = getMobEffect();

        if (NoRenderCfg.noBlindness && effect.is(MobEffects.BLINDNESS)) {
            cir.setReturnValue(false);
        } else if (NoRenderCfg.noDarkness && effect.is(MobEffects.DARKNESS)) {
            cir.setReturnValue(false);
        }
    }
}