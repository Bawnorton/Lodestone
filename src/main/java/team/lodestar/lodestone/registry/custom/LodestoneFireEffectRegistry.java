package team.lodestar.lodestone.registry.custom;

import team.lodestar.lodestone.systems.fireeffect.FireEffectType;
import java.util.HashMap;
import java.util.Map;

public final class LodestoneFireEffectRegistry {
    public static Map<String, FireEffectType> FIRE_TYPES = new HashMap<>();

    public static void bootstrap() {
        // no-op
    }

    public static FireEffectType registerType(FireEffectType type) {
        FIRE_TYPES.put(type.id, type);
        return type;
    }

    private LodestoneFireEffectRegistry() {
    }
}
