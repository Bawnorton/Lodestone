package team.lodestar.lodestone.systems.worldevent;

public record WorldEventType(String id, EventInstanceSupplier supplier) {
    public interface EventInstanceSupplier {
        WorldEventInstance getInstance();
    }
}