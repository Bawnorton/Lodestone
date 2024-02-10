package team.lodestar.lodestone.client.registry.custom;

import team.lodestar.lodestone.client.systems.worldevent.WorldEventRenderer;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;
import java.util.HashMap;
import java.util.Map;

public final class LodestoneWorldEventRendererRegistry {
    public static final Map<WorldEventType, WorldEventRenderer<? extends WorldEventInstance>> RENDERERS = new HashMap<>();

    public static void bootstrap() {
        // no-op
    }

    public static void registerRenderer(WorldEventType type, WorldEventRenderer<? extends WorldEventInstance> renderer) {
        RENDERERS.put(type, renderer);
    }

    private LodestoneWorldEventRendererRegistry() {
    }
}
