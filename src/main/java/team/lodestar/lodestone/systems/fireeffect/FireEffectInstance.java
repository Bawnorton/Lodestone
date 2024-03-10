package team.lodestar.lodestone.systems.fireeffect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import team.lodestar.lodestone.handlers.FireEffectHandler;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.registry.custom.LodestoneFireEffectRegistry;

/**
 * A FireEffectInstance is a custom instance of a fire effect, functioning pretty much exactly as a normal fire effect would do
 * You must register a type and can manage a players fire effect through the {@link FireEffectHandler}
 */
public class FireEffectInstance {
    public static final Codec<FireEffectInstance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    FireEffectType.CODEC.fieldOf("type").forGetter(fireEffectInstance -> fireEffectInstance.type),
                    Codec.INT.fieldOf("duration").forGetter(fireEffectInstance -> fireEffectInstance.duration)
            ).apply(instance, FireEffectInstance::new)
    );

    public int duration;
    public final FireEffectType type;

    public FireEffectInstance(FireEffectType type) {
        this.type = type;
    }

    private FireEffectInstance(FireEffectType type, int duration) {
        this.type = type;
        this.duration = duration;
    }

    public FireEffectInstance extendDuration(int increase) {
        this.duration += increase;
        return this;
    }

    public FireEffectInstance setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public FireEffectInstance sync(Entity target) {
//        LodestoneEntityDataCapability.syncTrackingAndSelf(target, NBTHelper.create("fireEffect", "type", "duration").setWhitelist());
        return this;
    }

    public FireEffectInstance syncDuration(Entity target) {
//        LodestoneEntityDataCapability.syncTrackingAndSelf(target, NBTHelper.create("fireEffect", "duration").setWhitelist());
        return this;
    }

    public void tick(Entity target) {
        if ((target.inPowderSnow || target.isWet())) {
            type.extinguish(this, target);
        }
        if (canDamageTarget(target)) {
            duration--;
            if (type.isValid(this) && duration % type.getTickInterval(this) == 0) {
                type.tick(this, target);
            }
        } else {
            duration -= 4;
        }
    }

    public void entityAttack() {

    }

    public boolean canDamageTarget(Entity target) {
        return !target.isFireImmune();
    }

    public boolean isValid() {
        return type.isValid(this);
    }
}