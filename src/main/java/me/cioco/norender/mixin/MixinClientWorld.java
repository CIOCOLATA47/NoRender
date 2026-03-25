package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class MixinClientWorld {

    @Inject(method = "addDestroyBlockEffect", at = @At("HEAD"), cancellable = true)
    private void onAddBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (NoRenderCfg.noBlockBreakParticles) {
            ci.cancel();
        }
    }
}