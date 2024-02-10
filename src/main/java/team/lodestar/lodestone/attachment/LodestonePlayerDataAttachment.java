package team.lodestar.lodestone.attachment;

import net.minecraft.nbt.NbtCompound;

public abstract class LodestonePlayerDataAttachment {
    public boolean hasJoinedBefore;
    public boolean rightClickHeld;
    public int rightClickTicks;
    public boolean leftClickHeld;
    public int leftClickTicks;

    protected LodestonePlayerDataAttachment() {
        this.hasJoinedBefore = false;
        this.rightClickHeld = false;
        this.rightClickTicks = 0;
        this.leftClickHeld = false;
        this.leftClickTicks = 0;
    }

    public void readNbt(NbtCompound nbt) {
        this.hasJoinedBefore = nbt.getBoolean("hasJoinedBefore");
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("hasJoinedBefore", this.hasJoinedBefore);
        return nbt;
    }

    public void onPlayerTick() {
        rightClickTicks = rightClickHeld ? rightClickTicks + 1 : 0;
        leftClickTicks = leftClickHeld ? leftClickTicks + 1 : 0;
    }
}
