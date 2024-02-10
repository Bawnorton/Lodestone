package team.lodestar.lodestone.registry.custom;

import team.lodestar.lodestone.systems.worldevent.WorldEventType;
import java.util.HashMap;
import java.util.Map;

public final class LodestoneWorldEventTypeRegistry {
    public static Map<String, WorldEventType> EVENT_TYPES = new HashMap<>();

    public static void bootstrap() {
        // no-op
    }

    public static WorldEventType registerEventType(WorldEventType eventType) {
        EVENT_TYPES.put(eventType.id(), eventType);
        return eventType;
    }

    private LodestoneWorldEventTypeRegistry() {
    }
}
