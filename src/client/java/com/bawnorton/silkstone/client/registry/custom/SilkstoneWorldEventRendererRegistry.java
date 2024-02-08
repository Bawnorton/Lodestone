package com.bawnorton.silkstone.client.registry.custom;

import java.util.HashMap;
import java.util.Map;

public final class SilkstoneWorldEventRendererRegistry {
    public static Map<WorldEventType, WorldEventRenderer<WorldEventInstance>> RENDERERS = new HashMap<>();

    public static void registerRenderer(WorldEventType type, WorldEventRenderer<? extends WorldEventInstance> renderer) {
        RENDERERS.put(type, renderer);
    }

}
