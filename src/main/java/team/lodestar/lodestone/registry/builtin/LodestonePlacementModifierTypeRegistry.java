package team.lodestar.lodestone.registry.builtin;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public final class LodestonePlacementModifierTypeRegistry {
/* TODO: Add placement modifier types
    public static PlacementModifierType<ChancePlacementFilter> CHANCE = register("chance", ChancePlacementFilter.CODEC);
    public static PlacementModifierType<DimensionPlacementFilter> DIMENSION = register("dimension", DimensionPlacementFilter.CODEC);
*/
    public static void bootstrap() {
        // no-op
    }

    private static PlacementModifierType<? extends PlacementModifier> register(String name, Codec<PlacementModifier> codec) {
        return Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, name, () -> codec);
    }

    private LodestonePlacementModifierTypeRegistry() {
    }
}
