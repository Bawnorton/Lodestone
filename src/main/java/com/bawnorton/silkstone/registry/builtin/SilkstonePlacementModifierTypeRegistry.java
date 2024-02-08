package com.bawnorton.silkstone.registry.builtin;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public final class SilkstonePlacementModifierTypeRegistry implements SilkstoneBuiltinRegistry<PlacementModifierType<? extends PlacementModifier>> {
    private static final SilkstonePlacementModifierTypeRegistry INSTANCE = new SilkstonePlacementModifierTypeRegistry();

/* TODO: Add placement modifier types
    public PlacementModifierType<ChancePlacementFilter> CHANCE = register("chance", ChancePlacementFilter.CODEC);
    public PlacementModifierType<DimensionPlacementFilter> DIMENSION = register("dimension", DimensionPlacementFilter.CODEC);
*/
    public static void bootstrap() {
        // no-op
    }

    public static SilkstonePlacementModifierTypeRegistry get() {
        return INSTANCE;
    }

    private PlacementModifierType<? extends PlacementModifier> register(String name, Codec<PlacementModifier> codec) {
        return register(Registries.PLACEMENT_MODIFIER_TYPE, name, () -> codec);
    }

    private SilkstonePlacementModifierTypeRegistry() {
    }
}
