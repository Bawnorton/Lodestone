package team.lodestar.lodestone.client.attachment.persistance;

import team.lodestar.lodestone.client.attachment.LodestoneClientPlayerDataAttachment;

public interface ClientPlayerEntityExtender {
    LodestoneClientPlayerDataAttachment lodestone$getAttachment();

    void lodestone$setAttachment(LodestoneClientPlayerDataAttachment attachment);
}
