package team.lodestar.lodestone.client.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import team.lodestar.lodestone.client.attachment.LodestoneClientPlayerDataAttachment;
import team.lodestar.lodestone.client.attachment.persistance.ClientPlayerEntityExtender;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements ClientPlayerEntityExtender {
    @Unique
    private LodestoneClientPlayerDataAttachment lodestone$attachment;

    @Override
    public LodestoneClientPlayerDataAttachment lodestone$getAttachment() {
        return lodestone$attachment;
    }

    @Override
    public void lodestone$setAttachment(LodestoneClientPlayerDataAttachment attachment) {
        lodestone$attachment = attachment;
    }
}
