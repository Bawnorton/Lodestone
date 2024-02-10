package team.lodestar.lodestone.attachment;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import team.lodestar.lodestone.attachment.persistance.EntityExtender;
import team.lodestar.lodestone.handlers.FireEffectHandler;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;
import java.util.Optional;

public class LodestoneEntityDataAttachment {
    public FireEffectInstance fireEffectInstance;

    public void readNbt(NbtCompound tag) {
        FireEffectHandler.readNbt(this, tag);
    }

    public NbtCompound writeNbt(NbtCompound tag) {
        if(fireEffectInstance != null) {
            fireEffectInstance.writeNbt(tag);
        }
        return tag;
    }

    public static Optional<LodestoneEntityDataAttachment> getAttachmentOptional(Entity entity) {
        return entity instanceof EntityExtender extender ? Optional.of(extender.lodestone$getAttachment()) : Optional.empty();
    }

    public static LodestoneEntityDataAttachment getAttachment(Entity entity) {
        return getAttachmentOptional(entity).orElse(new LodestoneEntityDataAttachment());
    }
}
