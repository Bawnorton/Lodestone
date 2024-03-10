package team.lodestar.lodestone.systems.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public interface EventResponderItem {
    default void takeDamageEvent(DamageSource damageSource, float amount, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        takeDamageEvent(attacker, attacked, stack);
    }

    default void takeDamageEvent(LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
    }

    default void hurtEvent(DamageSource damageSource, float amount, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        hurtEvent(attacker, target, stack);
    }

    default void hurtEvent(LivingEntity attacker, LivingEntity target, ItemStack stack) {
    }

    default void killEvent(DamageSource damageSource, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        killEvent(attacker, target, stack);
    }

    default void killEvent(LivingEntity attacker, LivingEntity target, ItemStack stack) {
    }
}
