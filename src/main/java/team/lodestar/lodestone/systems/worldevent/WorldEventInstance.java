package team.lodestar.lodestone.systems.worldevent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import team.lodestar.lodestone.registry.custom.LodestoneWorldEventTypeRegistry;
import java.util.UUID;

/**
 * World events are tickable instanced objects which are saved in a world capability, which means they are unique per dimension.
 * They can exist on the client and are ticked separately.
 */
public class WorldEventInstance {
    public static final Codec<WorldEventInstance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                Codec.STRING.fieldOf("uuid").forGetter((event) -> event.uuid.toString()),
                Codec.STRING.fieldOf("type").forGetter((event) -> event.type.id()),
                Codec.BOOL.fieldOf("discarded").forGetter((event) -> event.discarded)
            ).apply(instance, WorldEventInstance::new)
    );

    public UUID uuid; //TODO: figure out why this is here.
    public WorldEventType type;
    public boolean discarded;

    public WorldEventInstance(WorldEventType type) {
        this.uuid = UUID.randomUUID();
        this.type = type;
    }

    private WorldEventInstance(String uuid, String typeId, boolean discarded) {
        this.uuid = UUID.fromString(uuid);
        this.type = LodestoneWorldEventTypeRegistry.EVENT_TYPES.get(typeId);
        this.discarded = discarded;
    }

    /**
     * Syncs the world event to all players.
     */
    public void sync(World world) {
        if (!world.isClient() && isClientSynced()) {
            sync(this);
        }
    }

    /**
     * Should this event exist on the client? It will be automatically synced in {@link #sync(World)}
     */
    public boolean isClientSynced() {
        return false;
    }

    public void start(World world) {
    }

    public void tick(World world) {

    }

    public void end(World world) {
        discarded = true;
    }


    public static <T extends WorldEventInstance> void sync(T instance) {
//        LodestonePacketRegistry.ORTUS_CHANNEL.send(PacketDistributor.ALL.noArg(), new SyncWorldEventPacket(instance.type.id, true, instance.serializeNBT(new NbtCompound())));
    }

    public static <T extends WorldEventInstance> void sync(T instance, ServerPlayerEntity player) {
//        LodestonePacketRegistry.ORTUS_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncWorldEventPacket(instance.type.id, false, instance.serializeNBT(new NbtCompound())));
    }
}