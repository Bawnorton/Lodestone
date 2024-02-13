package team.lodestar.lodestone.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.attachment.LodestoneServerPlayerDataAttachment;
import team.lodestar.lodestone.attachment.persistance.ServerPlayerEntityExtender;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityExtender {
    @Unique
    private LodestoneServerPlayerDataAttachment lodestone$attachment;

    @Override
    public LodestoneServerPlayerDataAttachment lodestone$getAttachment() {
        return lodestone$attachment;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        lodestone$attachment = new LodestoneServerPlayerDataAttachment();
        lodestone$attachment.readNbt(nbt);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (lodestone$attachment != null) lodestone$attachment.writeNbt(nbt);
    }
}
