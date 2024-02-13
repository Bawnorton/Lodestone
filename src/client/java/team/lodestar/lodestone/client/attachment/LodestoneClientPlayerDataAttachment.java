package team.lodestar.lodestone.client.attachment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import team.lodestar.lodestone.attachment.LodestonePlayerDataAttachment;
import team.lodestar.lodestone.client.attachment.persistance.ClientPlayerEntityExtender;
import java.util.Optional;

public class LodestoneClientPlayerDataAttachment extends LodestonePlayerDataAttachment {
    public static Optional<LodestoneClientPlayerDataAttachment> getAttachmentOptional(ClientPlayerEntity player) {
        return player instanceof ClientPlayerEntityExtender extender ? Optional.of(extender.lodestone$getAttachment()) : Optional.empty();
    }

    public static void tick(ClientPlayerEntity player) {
        MinecraftClient client = MinecraftClient.getInstance();
        getAttachmentOptional(player).ifPresent(attachment -> {
            boolean left = client.options.attackKey.isPressed();
            boolean right = client.options.useKey.isPressed();
            if(left != attachment.leftClickHeld) {
                attachment.leftClickHeld = left;
                // TODO: Sync to server
            }
            if(right != attachment.rightClickHeld) {
                attachment.rightClickHeld = right;
            }
        });
    }
}
