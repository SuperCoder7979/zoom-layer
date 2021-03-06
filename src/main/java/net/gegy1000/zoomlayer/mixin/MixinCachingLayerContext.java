package net.gegy1000.zoomlayer.mixin;

import net.gegy1000.zoomlayer.ConcurrentLayerCache;
import net.gegy1000.zoomlayer.FastCachingLayerSampler;
import net.minecraft.world.biome.layer.util.CachingLayerContext;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerOperator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CachingLayerContext.class)
public class MixinCachingLayerContext {
    private ConcurrentLayerCache fastCache;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(int capacity, long seed, long salt, CallbackInfo ci) {
        this.fastCache = new ConcurrentLayerCache(512);
    }

    /**
     * Replace with optimized implementation
     *
     * @author gegy1000
     */
    @Overwrite
    public CachingLayerSampler createSampler(LayerOperator operator) {
        return new FastCachingLayerSampler(this.fastCache, operator);
    }

    /**
     * Replace with optimized implementation
     *
     * @author gegy1000
     */
    @Overwrite
    public CachingLayerSampler createSampler(LayerOperator operator, CachingLayerSampler sampler) {
        return new FastCachingLayerSampler(this.fastCache, operator);
    }

    /**
     * Replace with optimized implementation
     *
     * @author gegy1000
     */
    @Overwrite
    public CachingLayerSampler createSampler(LayerOperator operator, CachingLayerSampler left, CachingLayerSampler right) {
        return new FastCachingLayerSampler(this.fastCache, operator);
    }
}
