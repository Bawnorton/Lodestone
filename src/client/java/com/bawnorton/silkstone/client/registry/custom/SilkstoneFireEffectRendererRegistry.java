package com.bawnorton.silkstone.client.registry.custom;

import java.util.HashMap;
import java.util.Map;

public final class SilkstoneFireEffectRendererRegistry {
    public static final SilkstoneFireEffectRendererRegistry INSTANCE = new SilkstoneFireEffectRendererRegistry();

/* TODO: Add fire effect renderers
    public Map<FireEffectType, FireEffectRenderer<FireEffectInstance>> RENDERERS = new HashMap<>();
*/

    public static void bootstrap() {
        // no-op
    }

/* TODO: Add fire effect renderers
    public void registerRenderer(FireEffectType type, FireEffectRenderer<? extends FireEffectInstance> renderer) {
        RENDERERS.put(type, (FireEffectRenderer<FireEffectInstance>) renderer);
    }
 */

    public static SilkstoneFireEffectRendererRegistry get() {
        return INSTANCE;
    }

    private SilkstoneFireEffectRendererRegistry() {
    }
}
