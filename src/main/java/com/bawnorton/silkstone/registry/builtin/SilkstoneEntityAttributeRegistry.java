package com.bawnorton.silkstone.registry.builtin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SilkstoneEntityAttributeRegistry implements SilkstoneBuiltinRegistry<EntityAttribute> {
    private static final SilkstoneEntityAttributeRegistry INSTANCE = new SilkstoneEntityAttributeRegistry();

    public final Map<EntityAttribute, UUID> UUIDS = new HashMap<>();
    public final EntityAttribute MAGIC_RESISTANCE = register("magic_resistance", 0.0D, 0.0D, 2048);
    public final EntityAttribute MAGIC_PROFICIENCY = register("magic_proficiency", 0.0D, 0.0D, 2048);
    public final EntityAttribute MAGIC_DAMAGE = register("magic_damage", 0.0D, 0.0D, 2048);

    public static void bootstrap() {
        // no-op
    }

    public static SilkstoneEntityAttributeRegistry get() {
        return INSTANCE;
    }

    private EntityAttribute register(String name, double defaultValue, double min, double max) {
        return register(name, defaultValue, min, max, true);
    }

    private EntityAttribute register(String name, double defaultValue, double min, double max, boolean tracked) {
        EntityAttribute attribute = register(Registries.ATTRIBUTE, name, new ClampedEntityAttribute("attribute.silkstone." + name, defaultValue, min, max).setTracked(tracked));
        UUIDS.put(attribute, UUID.randomUUID());
        return attribute;
    }

    private SilkstoneEntityAttributeRegistry() {
    }
}
