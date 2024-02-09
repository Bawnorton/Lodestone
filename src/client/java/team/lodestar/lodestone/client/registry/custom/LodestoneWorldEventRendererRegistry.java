package team.lodestar.lodestone.client.registry.custom;

import java.util.HashMap;
import java.util.Map;

public final class LodestoneWorldEventRendererRegistry {
    public static final Map<WorldEventType, WorldEventRenderer<WorldEventInstance>> RENDERERS = new HashMap<>();

    public static void bootstrap() {
        // no-op
    }

    public static void registerRenderer(WorldEventType type, WorldEventRenderer<? extends WorldEventInstance> renderer) {
        RENDERERS.put(type, renderer);
    }

    private LodestoneWorldEventRendererRegistry() {
    }
}
