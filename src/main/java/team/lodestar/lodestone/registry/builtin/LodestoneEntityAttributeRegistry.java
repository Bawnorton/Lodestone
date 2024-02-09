package team.lodestar.lodestone.registry.builtin;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class LodestoneEntityAttributeRegistry {
    public static final Map<EntityAttribute, UUID> UUIDS = new HashMap<>();
    public static final EntityAttribute MAGIC_RESISTANCE = register("magic_resistance", 0.0D, 0.0D, 2048);
    public static final EntityAttribute MAGIC_PROFICIENCY = register("magic_proficiency", 0.0D, 0.0D, 2048);
    public static final EntityAttribute MAGIC_DAMAGE = register("magic_damage", 0.0D, 0.0D, 2048);

    public static void bootstrap() {
        // no-op
    }

    private static EntityAttribute register(String name, double defaultValue, double min, double max) {
        return register(name, defaultValue, min, max, true);
    }

    private static EntityAttribute register(String name, double defaultValue, double min, double max, boolean tracked) {
        EntityAttribute attribute = Registry.register(Registries.ATTRIBUTE, name, new ClampedEntityAttribute("attribute.lodestone." + name, defaultValue, min, max).setTracked(tracked));
        UUIDS.put(attribute, UUID.randomUUID());
        return attribute;
    }

    private LodestoneEntityAttributeRegistry() {
    }
}
