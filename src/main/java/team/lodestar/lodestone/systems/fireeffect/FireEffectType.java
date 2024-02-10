package team.lodestar.lodestone.systems.fireeffect;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class FireEffectType {
    public final String id;
    protected final int damage;
    protected final int tickInterval;

    public FireEffectType(String id, int damage, int tickInterval) {
        this.id = id;
        this.damage = damage;
        this.tickInterval = tickInterval;
    }

    public int getDamage(FireEffectInstance instance) {
        return damage;
    }

    public int getTickInterval(FireEffectInstance instance) {
        return tickInterval;
    }

    public void extinguish(FireEffectInstance instance, Entity target) {
        instance.duration = 0;

        World world = target.getWorld();
        if(!world.isClient()) {
            world.playSound(null, target.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, target.getSoundCategory(),0.7F, 1.6F + (world.getRandom().nextFloat() - world.getRandom().nextFloat()) * 0.4F);
        }
    }

    public void tick(FireEffectInstance instance, Entity target) {
        target.damage(target.getWorld().getDamageSources().inFire(), getDamage(instance));
    }

    public boolean isValid(FireEffectInstance instance) {
        return instance.duration > 0;
    }
}