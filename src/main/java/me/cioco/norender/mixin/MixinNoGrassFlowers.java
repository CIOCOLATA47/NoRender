package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.BlockQuadOutput;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBlockRenderer.class)
public class MixinNoGrassFlowers {

    @Inject(method = "tesselateBlock", at = @At("HEAD"), cancellable = true)
    private void skipGrassAndFlowers(BlockQuadOutput output, float x, float y, float z, BlockAndTintGetter level, BlockPos pos, BlockState blockState, BlockStateModel model, long seed, CallbackInfo ci) {
        if (NoRenderCfg.noGrassAndFlowers) {
            if (blockState.is(BlockTags.FLOWERS)
                    || blockState.is(BlockTags.SMALL_FLOWERS)
                    || blockState.is(BlockTags.REPLACEABLE_BY_TREES)
                    || blockState.is(BlockTags.REPLACEABLE)
                    || blockState.is(Blocks.SHORT_GRASS)
                    || blockState.is(Blocks.TALL_GRASS)
                    || blockState.is(Blocks.FERN)
                    || blockState.is(Blocks.LARGE_FERN)
                    || blockState.is(Blocks.DEAD_BUSH)) {
                ci.cancel();
            }
        }
    }
}