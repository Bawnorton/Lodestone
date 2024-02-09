package team.lodestar.lodestone.client.systems.rendering.renderlayer;

import team.lodestar.lodestone.client.registry.LodestoneClientRegistries;
import team.lodestar.lodestone.client.registry.custom.LodestoneRenderLayerRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import java.util.function.Consumer;
import java.util.function.Function;

public class RenderLayerProvider {
    private final Function<Identifier, RenderLayer> function;
    private final Function<Identifier, RenderLayer> memoizedFunction;

    public RenderLayerProvider(Function<Identifier, RenderLayer> function) {
        this.function = function;
        this.memoizedFunction = Util.memoize(function);
    }

    public RenderLayer apply(Identifier texture) {
        return function.apply(texture);
    }

    public RenderLayer apply(Identifier texture, ShaderUniformHandler uniformHandler) {
        return LodestoneRenderLayerRegistry.applyUniformChanges(function.apply(texture), uniformHandler);
    }

    public RenderLayer applyAndCache(Identifier texture) {
        return this.memoizedFunction.apply(texture);
    }

    public RenderLayer applyWithModifier(Identifier texture, Consumer<LodestoneRenderLayerRegistry.LodestoneMultiPhaseParametersBuilder> modifier) {
        LodestoneRenderLayerRegistry.setModifier(modifier);
        return apply(texture);
    }

    public RenderLayer applyWithModifier(Identifier texture, ShaderUniformHandler uniformHandler, Consumer<LodestoneRenderLayerRegistry.LodestoneMultiPhaseParametersBuilder> modifier) {
        LodestoneRenderLayerRegistry.setModifier(modifier);
        return apply(texture, uniformHandler);
    }

    public RenderLayer applyWithModifierAndCache(Identifier texture, Consumer<LodestoneRenderLayerRegistry.LodestoneMultiPhaseParametersBuilder> modifier) {
        LodestoneRenderLayerRegistry.setModifier(modifier);
        return applyAndCache(texture);
    }
}
