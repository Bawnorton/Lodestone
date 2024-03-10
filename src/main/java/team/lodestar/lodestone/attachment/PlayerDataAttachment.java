package team.lodestar.lodestone.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import team.lodestar.lodestone.events.types.ServerPlayerEvents;

public class PlayerDataAttachment {
    public static final Codec<PlayerDataAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("hasJoinedBefore", false).forGetter(attachment -> attachment.hasJoinedBefore)
    ).apply(instance, PlayerDataAttachment::new));

    public boolean hasJoinedBefore;
    public boolean rightClickHeld;
    public int rightClickTicks;
    public boolean leftClickHeld;
    public int leftClickTicks;

    public PlayerDataAttachment(boolean hasJoinedBefore) {
        this.hasJoinedBefore = hasJoinedBefore;
        this.rightClickHeld = false;
        this.rightClickTicks = 0;
        this.leftClickHeld = false;
        this.leftClickTicks = 0;
    }

    public static void bootstrap() {
        ServerPlayerEvents.TICK_EVENT.register((player) -> {
            PlayerDataAttachment attachment = player.getAttachedOrCreate(LodestoneAttachments.PLAYER_DATA);
            attachment.onPlayerTick();
        });
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if(entity instanceof ServerPlayerEntity player) {
                PlayerDataAttachment attachment = player.getAttachedOrCreate(LodestoneAttachments.PLAYER_DATA);
                attachment.hasJoinedBefore = true;
            }
        });
    }

    public void onPlayerTick() {
        rightClickTicks = rightClickHeld ? rightClickTicks + 1 : 0;
        leftClickTicks = leftClickHeld ? leftClickTicks + 1 : 0;
    }
}
