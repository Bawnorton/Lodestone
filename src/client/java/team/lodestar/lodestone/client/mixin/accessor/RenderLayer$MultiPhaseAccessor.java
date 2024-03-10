package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.function.BiFunction;

@Mixin(RenderLayer.MultiPhase.class)
public interface RenderLayer$MultiPhaseAccessor extends RenderPhaseAccessor {
    @Accessor
    RenderLayer.MultiPhaseParameters getPhases();

    @Accessor("CULLING_LAYERS")
    static BiFunction<Identifier, RenderPhase.Cull, RenderLayer> getCullingLayers() {
        throw new AssertionError();
    }
}
