package team.lodestar.lodestone.helpers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.Vec3d;
import team.lodestar.lodestone.mixin.accessor.LivingEntityAccessor;
import team.lodestar.lodestone.mixin.accessor.StatusEffectInstanceAccessor;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class EntityHelper {

    public static void amplifyEffect(StatusEffectInstance instance, LivingEntity target, int addedAmplifier, int cap) {
        StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor) instance;
        accessor.setAmplifier(Math.max(Math.min(cap, instance.getAmplifier() + addedAmplifier), instance.getAmplifier()));
        syncEffect(instance, target);
    }

    public static void amplifyEffect(StatusEffectInstance instance, LivingEntity target, int addedAmplifier) {
        StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor) instance;
        accessor.setAmplifier(instance.getAmplifier() + addedAmplifier);
        syncEffect(instance, target);
    }

    public static void extendEffect(StatusEffectInstance instance, LivingEntity target, int addedDuration, int cap) {
        StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor) instance;
        accessor.setDuration(Math.max(Math.min(cap, instance.getDuration() + addedDuration), instance.getDuration()));
        syncEffect(instance, target);
    }

    public static void extendEffect(StatusEffectInstance instance, LivingEntity target, int addedDuration) {
        StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor) instance;
        accessor.setDuration(instance.getDuration() + addedDuration);
        syncEffect(instance, target);
    }

    public static void shortenEffect(StatusEffectInstance instance, LivingEntity target, int removedDuration) {
        StatusEffectInstanceAccessor accessor = (StatusEffectInstanceAccessor) instance;
        accessor.setDuration(Math.max(0, instance.getDuration() - removedDuration));
        syncEffect(instance, target);
    }

    public static void syncEffect(StatusEffectInstance instance, LivingEntity target) {
        LivingEntityAccessor accessor = (LivingEntityAccessor) target;

        accessor.setEffectsChanged(true);
        accessor.invokeOnStatusEffectUpgraded(instance, true, target);
    }

    /**
     * Tracks the travel path of an entity or other object
     *
     * @param pastPositions     An ArrayList that houses all the past positions.
     * @param currentPosition   The current position to be added to the list.
     * @param distanceThreshold the minimum distance from the latest PastPos before a new position is added.
     */
    public static void trackPastPositions(ArrayList<PastPosition> pastPositions, Vec3d currentPosition, float distanceThreshold) {
        for (PastPosition pastPosition : pastPositions) {
            pastPosition.time++;
        }
        if (!pastPositions.isEmpty()) {
            PastPosition latest = pastPositions.get(pastPositions.size() - 1);
            float distance = (float) latest.position.distanceTo(currentPosition);
            if (distance > distanceThreshold) {
                pastPositions.add(new PastPosition(currentPosition, 0));
            }
        } else {
            pastPositions.add(new PastPosition(currentPosition, 0));
        }
    }

    public static class PastPosition {
        public Vec3d position;
        public int time;

        public PastPosition(Vec3d position, int time) {
            this.position = position;
            this.time = time;
        }
    }
}