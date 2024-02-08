package com.bawnorton.silkstone.registry.custom;

public final class SilkstoneWorldEventTypeRegistry {
    private static final SilkstoneWorldEventTypeRegistry INSTANCE = new SilkstoneWorldEventTypeRegistry();

/* TODO: Add event types
    public Map<String, WorldEventType> EVENT_TYPES = new HashMap<>();
*/

    public static void bootstrap() {
        // no-op
    }

/* TODO: Add event types
    public WorldEventType registerEventType(WorldEventType eventType) {
        EVENT_TYPES.put(eventType.id, eventType);
        return eventType;
    }
 */

    public static SilkstoneWorldEventTypeRegistry get() {
        return INSTANCE;
    }

    private SilkstoneWorldEventTypeRegistry() {
    }
}
