package team.lodestar.lodestone.attachment;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import team.lodestar.lodestone.attachment.persistance.LodestoneWorldDataState;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LodestoneWorldDataAttachment {
    public final List<WorldEventInstance> activeWorldEvents = new ArrayList<>();
    public final List<WorldEventInstance> inboundWorldEvents = new ArrayList<>();

    public LodestoneWorldDataAttachment(NbtCompound nbt) {
        this.readNbt(nbt);
    }

    public void readNbt(NbtCompound nbt) {
        WorldEventHandler.readNbt(this, nbt);
    }

    public static Optional<LodestoneWorldDataAttachment> getAttachmentOptional(World world) {
        return world instanceof ServerWorld serverWorld ? Optional.of(LodestoneWorldDataState.getWorldDataState(serverWorld).data) : Optional.empty();
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        WorldEventHandler.writeNbt(this, nbt);
        return nbt;
    }
}
