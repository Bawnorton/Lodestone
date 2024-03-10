package team.lodestar.lodestone.handlers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.systems.item.EventResponderItem;

/**
 * A handler for firing {@link EventResponderItem} events
 */
public class ItemEventHandler {
    public static void respondToDeath(LivingEntity killed, DamageSource damageSource) {
        LivingEntity attacker = null;
        if (damageSource.getAttacker() instanceof LivingEntity directAttacker) {
            attacker = directAttacker;
        }
        if (attacker == null) {
            attacker = killed.getLastAttacker();
        }
        if (attacker != null) {
            LivingEntity finalAttacker = attacker;
            ItemHelper.getEventResponders(attacker).forEach(stack -> ((EventResponderItem) stack.getItem()).killEvent(damageSource, finalAttacker, killed, stack));
        }
    }

    public static void respondToHurt(LivingEntity hurt, DamageSource damageSource, float amount) {
        if (amount <= 0) return;

        if (damageSource.getAttacker() instanceof LivingEntity attacker) {
            ItemHelper.getEventResponders(attacker).forEach(s -> ((EventResponderItem) s.getItem()).hurtEvent(damageSource, amount, attacker, hurt, s));
            ItemHelper.getEventResponders(hurt).forEach(s -> ((EventResponderItem) s.getItem()).takeDamageEvent(damageSource, amount, attacker, hurt, s));
        }
    }
}