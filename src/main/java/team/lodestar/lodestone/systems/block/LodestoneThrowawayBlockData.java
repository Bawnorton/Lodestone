package team.lodestar.lodestone.systems.block;

import team.lodestar.lodestone.dummyclient.RenderLayerReference;
import java.util.function.Supplier;

public class LodestoneThrowawayBlockData {
    public static final LodestoneThrowawayBlockData EMPTY = new LodestoneThrowawayBlockData();
    private Supplier<Supplier<RenderLayerReference>> renderLayer;
    private boolean hasCustomRenderLayer;

    public LodestoneThrowawayBlockData setRenderLayer(Supplier<Supplier<RenderLayerReference>> renderLayer) {
        this.renderLayer = renderLayer;
        this.hasCustomRenderLayer = true;
        return this;
    }

    public Supplier<Supplier<RenderLayerReference>> getRenderLayer() {
        return renderLayer;
    }

    public boolean hasCustomRenderLayer() {
        return hasCustomRenderLayer;
    }
}
