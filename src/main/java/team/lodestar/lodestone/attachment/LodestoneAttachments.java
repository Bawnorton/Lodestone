package team.lodestar.lodestone.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import team.lodestar.lodestone.Lodestone;

public class LodestoneAttachments {
    public static final AttachmentType<EntityDataAttachment> ENTITY_DATA = AttachmentRegistry.<EntityDataAttachment>builder()
            .persistent(EntityDataAttachment.CODEC)
            .initializer(() -> new EntityDataAttachment(null))
            .copyOnDeath()
            .buildAndRegister(Lodestone.id("entity_data"));

    public static final AttachmentType<PlayerDataAttachment> PLAYER_DATA = AttachmentRegistry.<PlayerDataAttachment>builder()
            .persistent(PlayerDataAttachment.CODEC)
            .initializer(() -> new PlayerDataAttachment(false))
            .copyOnDeath()
            .buildAndRegister(Lodestone.id("player_data"));

    public static final AttachmentType<WorldDataAttachment> WORLD_EVENT_DATA = AttachmentRegistry.<WorldDataAttachment>builder()
            .persistent(WorldDataAttachment.CODEC)
            .initializer(WorldDataAttachment::new)
            .buildAndRegister(Lodestone.id("world_event_data"));
}
