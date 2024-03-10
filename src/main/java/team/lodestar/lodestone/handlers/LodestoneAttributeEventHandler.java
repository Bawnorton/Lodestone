package team.lodestar.lodestone.handlers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import team.lodestar.lodestone.registry.builtin.LodestoneEntityAttributeRegistry;
import team.lodestar.lodestone.registry.custom.LodestoneTags;

/**
 * A handler for common attributes I use in my mods.
 */
public class LodestoneAttributeEventHandler {
    public static float processAttributes(LivingEntity hurt, DamageSource damageSource, float amount) {
        if (amount <= 0) return amount;

        if (damageSource.getTypeRegistryEntry().isIn(LodestoneTags.IS_MAGIC)) {
            if (damageSource.getAttacker() instanceof LivingEntity attacker) {
                EntityAttributeInstance magicProficiency = attacker.getAttributeInstance(LodestoneEntityAttributeRegistry.MAGIC_PROFICIENCY);
                if (magicProficiency != null && magicProficiency.getValue() > 0) {
                    amount += (float) (magicProficiency.getValue() * 0.5f);
                }
            }
            EntityAttributeInstance magicResistance = hurt.getAttributeInstance(LodestoneEntityAttributeRegistry.MAGIC_RESISTANCE);
            if (magicResistance != null && magicResistance.getValue() > 0) {
                amount *= (float) applyMagicResistance(magicResistance.getValue());
            }
            return amount;
        }

        if (!(damageSource.getAttacker() instanceof LivingEntity attacker)) return amount;
        if (damageSource.getTypeRegistryEntry().isIn(LodestoneTags.IS_MAGIC)) return amount;

        EntityAttributeInstance magicDamage = attacker.getAttributeInstance(LodestoneEntityAttributeRegistry.MAGIC_DAMAGE);
        if (magicDamage == null || magicDamage.getValue() <= 0 || !hurt.isAlive()) return amount;

        World world = hurt.getWorld();
        RegistryEntry.Reference<DamageType> damageTypeReference = world.getRegistryManager().getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC);
        DamageSource magic = new DamageSource(damageTypeReference, attacker, attacker);
        hurt.timeUntilRegen = 0;
        hurt.damage(magic, (float) magicDamage.getValue());
        return amount;
    }

    public static double applyMagicResistance(double magicResistance) {
        return (1 - (0.75 * (1 / (0.2 * (magicResistance + 1))))) * 0.8;
    }
}