package team.lodestar.lodestone.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import java.util.ArrayList;
import java.util.List;

public record WorldDataAttachment(List<WorldEventInstance> activeWorldEvents,
                                  List<WorldEventInstance> inboundWorldEvents) {
    public static final Codec<WorldDataAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(WorldEventInstance.CODEC)
                    .fieldOf("activeWorldEvents")
                    .forGetter(attachment -> attachment.activeWorldEvents),
            Codec.list(WorldEventInstance.CODEC)
                    .fieldOf("inboundWorldEvents")
                    .forGetter(attachment -> attachment.inboundWorldEvents)
    ).apply(instance, WorldDataAttachment::new));

    public WorldDataAttachment() {
        this(new ArrayList<>(), new ArrayList<>());
    }
}
