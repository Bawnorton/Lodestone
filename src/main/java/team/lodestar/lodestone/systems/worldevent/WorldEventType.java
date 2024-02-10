package team.lodestar.lodestone.systems.worldevent;

import net.minecraft.nbt.NbtCompound;

public record WorldEventType(String id, EventInstanceSupplier supplier) {
    public WorldEventInstance createInstance(NbtCompound tag) {
        return supplier.getInstance().deserializeNBT(tag);
    }

    public interface EventInstanceSupplier {
        WorldEventInstance getInstance();
    }
}