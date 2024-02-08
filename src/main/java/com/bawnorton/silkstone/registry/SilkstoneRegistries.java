package com.bawnorton.silkstone.registry;

import com.bawnorton.silkstone.registry.builtin.SilkstoneBlockEntityRegistry;
import com.bawnorton.silkstone.registry.builtin.SilkstoneEntityAttributeRegistry;
import com.bawnorton.silkstone.registry.builtin.SilkstonePaintingRegistry;
import com.bawnorton.silkstone.registry.builtin.SilkstoneParticleTypeRegistry;
import com.bawnorton.silkstone.registry.builtin.SilkstonePlacementModifierTypeRegistry;
import com.bawnorton.silkstone.registry.builtin.SilkstoneRecipeSerializerRegistry;
import com.bawnorton.silkstone.registry.custom.SilkstoneFireEffectReigstry;
import com.bawnorton.silkstone.registry.custom.SilkstoneScreenParticleRegistry;
import com.bawnorton.silkstone.registry.custom.SilkstoneTags;
import com.bawnorton.silkstone.registry.custom.SilkstoneWorldEventTypeRegistry;

public final class SilkstoneRegistries {
    public static final SilkstoneBlockEntityRegistry BLOCK_ENTITY_TYPE = SilkstoneBlockEntityRegistry.get();
    public static final SilkstoneEntityAttributeRegistry ATTRIBUTE = SilkstoneEntityAttributeRegistry.get();
    public static final SilkstonePaintingRegistry PAINTING_VARIANT = SilkstonePaintingRegistry.get();
    public static final SilkstoneParticleTypeRegistry PARTICLE_TYPE = SilkstoneParticleTypeRegistry.get();
    public static final SilkstonePlacementModifierTypeRegistry PLACEMENT_MODIFIER_TYPE = SilkstonePlacementModifierTypeRegistry.get();
    public static final SilkstoneRecipeSerializerRegistry RECIPE_SERIALIZER = SilkstoneRecipeSerializerRegistry.get();
    public static final SilkstoneFireEffectReigstry FIRE_EFFECT = SilkstoneFireEffectReigstry.get();
    public static final SilkstoneScreenParticleRegistry SCREEN_PARTICLE_TYPE = SilkstoneScreenParticleRegistry.get();
    public static final SilkstoneTags TAGS = SilkstoneTags.get();
    public static final SilkstoneWorldEventTypeRegistry WORLD_EVENT_TYPE = SilkstoneWorldEventTypeRegistry.get();

    public static void bootstrap() {
        SilkstoneBlockEntityRegistry.bootstrap();
        SilkstoneEntityAttributeRegistry.bootstrap();
        SilkstonePaintingRegistry.bootstrap();
        SilkstoneParticleTypeRegistry.bootstrap();
        SilkstonePlacementModifierTypeRegistry.bootstrap();
        SilkstoneRecipeSerializerRegistry.bootstrap();
        SilkstoneFireEffectReigstry.bootstrap();
        SilkstoneScreenParticleRegistry.bootstrap();
        SilkstoneTags.bootstrap();
        SilkstoneWorldEventTypeRegistry.bootstrap();
    }
}
