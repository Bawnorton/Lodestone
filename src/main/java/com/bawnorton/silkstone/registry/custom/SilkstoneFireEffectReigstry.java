package com.bawnorton.silkstone.registry.custom;

public final class SilkstoneFireEffectReigstry {
    private static final SilkstoneFireEffectReigstry INSTANCE = new SilkstoneFireEffectReigstry();

/* TODO: Add fire effect types
    public Map<String, FireEffectType> FIRE_TYPES = new HashMap<>();
 */

    public static void bootstrap() {
        // no-op
    }

/* TODO: Add fire effect types
    public FireEffectType registerType(FireEffectType type) {
        FIRE_TYPES.put(type.id, type);
        return type;
    }
 */

    public static SilkstoneFireEffectReigstry get() {
        return INSTANCE;
    }

    private SilkstoneFireEffectReigstry() {
    }
}
