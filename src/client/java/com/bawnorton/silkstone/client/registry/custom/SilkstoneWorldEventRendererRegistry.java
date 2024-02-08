package com.bawnorton.silkstone.client.registry.custom;

import java.util.HashMap;
import java.util.Map;

public final class SilkstoneWorldEventRendererRegistry {
    private static final SilkstoneWorldEventRendererRegistry INSTANCE = new SilkstoneWorldEventRendererRegistry();

    public Map<WorldEventType, WorldEventRenderer<WorldEventInstance>> RENDERERS = new HashMap<>();

    public static void bootstrap() {
        // no-op
    }

    public static SilkstoneWorldEventRendererRegistry get() {
        return INSTANCE;
    }

    public void registerRenderer(WorldEventType type, WorldEventRenderer<? extends WorldEventInstance> renderer) {
        RENDERERS.put(type, renderer);
    }

    private SilkstoneWorldEventRendererRegistry() {
    }
}
