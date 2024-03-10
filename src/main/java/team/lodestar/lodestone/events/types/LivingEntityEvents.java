package team.lodestar.lodestone.events.types;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public final class LivingEntityEvents {
    public static final Event<DamageEvent> DAMAGE = EventFactory.createArrayBacked(DamageEvent.class, listeners -> (entity, source, amount) -> {
        for (DamageEvent listener : listeners) {
            amount = listener.onDamage(entity, source, amount);
        }
        return amount;
    });

    public static final Event<DeathEvent> DEATH = EventFactory.createArrayBacked(DeathEvent.class, listeners -> (entity, source) -> {
        for (DeathEvent listener : listeners) {
            listener.onDeath(entity, source);
        }
    });

    @FunctionalInterface
    public interface DamageEvent {
        float onDamage(LivingEntity entity, DamageSource source, float amount);
    }

    @FunctionalInterface
    public interface DeathEvent {
        void onDeath(LivingEntity entity, DamageSource source);
    }
}
