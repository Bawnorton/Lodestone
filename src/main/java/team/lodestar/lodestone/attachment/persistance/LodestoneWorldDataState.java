package team.lodestar.lodestone.attachment.persistance;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import team.lodestar.lodestone.attachment.LodestoneWorldDataAttachment;

public class LodestoneWorldDataState extends PersistentState {
    public LodestoneWorldDataAttachment data = new LodestoneWorldDataAttachment(new NbtCompound());

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return data.writeNbt(nbt);
    }

    public static LodestoneWorldDataState createFromNbt(NbtCompound nbt) {
        LodestoneWorldDataState loader = new LodestoneWorldDataState();
        loader.data = new LodestoneWorldDataAttachment(nbt);
        return loader;
    }

    public static LodestoneWorldDataState getWorldDataState(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        LodestoneWorldDataState loader = manager.getOrCreate(LodestoneWorldDataState::createFromNbt, LodestoneWorldDataState::new, "lodestone:world_data");
        loader.markDirty();
        return loader;
    }
}
