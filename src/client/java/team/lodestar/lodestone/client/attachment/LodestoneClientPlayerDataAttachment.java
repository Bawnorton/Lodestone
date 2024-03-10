package team.lodestar.lodestone.client.attachment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import team.lodestar.lodestone.attachment.LodestoneAttachments;

public class LodestoneClientPlayerDataAttachment {
    public static void tick(ClientPlayerEntity player) {
        MinecraftClient client = MinecraftClient.getInstance();
        player.modifyAttached(LodestoneAttachments.PLAYER_DATA, attachment -> {
            if(attachment == null) return null;

            boolean left = client.options.attackKey.isPressed();
            boolean right = client.options.useKey.isPressed();
            if(left != attachment.leftClickHeld) {
                attachment.leftClickHeld = left;
            }
            if(right != attachment.rightClickHeld) {
                attachment.rightClickHeld = right;
            }
            return attachment;
        });
    }
}
