package team.lodestar.lodestone.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.attachment.LodestoneEntityDataAttachment;
import team.lodestar.lodestone.attachment.persistance.EntityExtender;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExtender {
    @Unique
    private LodestoneEntityDataAttachment lodestone$attachment;

    @Override
    public LodestoneEntityDataAttachment lodestone$getAttachment() {
        return lodestone$attachment;
    }

    @Inject(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", shift = At.Shift.AFTER))
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        lodestone$attachment = new LodestoneEntityDataAttachment();
        lodestone$attachment.readNbt(nbt);
    }

    @Inject(method = "writeNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", shift = At.Shift.AFTER))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        lodestone$attachment.writeNbt(nbt);
    }
}
