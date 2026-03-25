package me.cioco.norender.mixin;

import me.cioco.norender.config.NoRenderCfg;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer", remap = false)
public class MixinNoGrassFlowersSodium {

    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true, remap = false)
    private void skipGrassAndFlowers(BlockStateModel model, BlockState state, BlockPos pos, BlockPos origin, CallbackInfo ci) {
        if (NoRenderCfg.noGrassAndFlowers) {
            if (state.is(BlockTags.FLOWERS)
                    || state.is(BlockTags.SMALL_FLOWERS)
                    || state.is(BlockTags.REPLACEABLE_BY_TREES)
                    || state.is(BlockTags.REPLACEABLE)
                    || state.is(Blocks.SHORT_GRASS)
                    || state.is(Blocks.TALL_GRASS)
                    || state.is(Blocks.FERN)
                    || state.is(Blocks.LARGE_FERN)
                    || state.is(Blocks.DEAD_BUSH)) {

                if (state.is(BlockTags.LEAVES)) {
                    return;
                }

                if (state.getBlock() instanceof SnowLayerBlock) {
                    return;
                }

                ci.cancel();
            }
        }
    }
}