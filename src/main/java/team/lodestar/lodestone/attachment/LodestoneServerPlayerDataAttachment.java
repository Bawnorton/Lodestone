package team.lodestar.lodestone.attachment;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import team.lodestar.lodestone.attachment.persistance.ServerPlayerEntityExtender;
import team.lodestar.lodestone.events.ServerPlayerEvents;
import java.util.Optional;

public class LodestoneServerPlayerDataAttachment extends LodestonePlayerDataAttachment {
    public static void bootstrap() {
        ServerPlayerEvents.TICK_EVENT.register((player) -> getAttachmentOptional(player).ifPresent(LodestonePlayerDataAttachment::onPlayerTick));
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayerEntity player) {
                getAttachmentOptional(player).ifPresent(attachment -> attachment.hasJoinedBefore = true);
                // TODO: Sync to client
            }
        });
    }

    public static Optional<LodestoneServerPlayerDataAttachment> getAttachmentOptional(ServerPlayerEntity player) {
        return player instanceof ServerPlayerEntityExtender extender ? Optional.of(extender.lodestone$getAttachment()) : Optional.empty();
    }
}
