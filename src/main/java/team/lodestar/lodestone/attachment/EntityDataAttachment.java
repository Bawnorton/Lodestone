package team.lodestar.lodestone.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;

public class EntityDataAttachment {
    public static final Codec<EntityDataAttachment> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                FireEffectInstance.CODEC.fieldOf("fireEffectInstance").forGetter(attachment -> attachment.fireEffectInstance)
            ).apply(instance, EntityDataAttachment::new)
    );

    public FireEffectInstance fireEffectInstance;

    public EntityDataAttachment(FireEffectInstance fireEffectInstance) {
        this.fireEffectInstance = fireEffectInstance;
    }
}
