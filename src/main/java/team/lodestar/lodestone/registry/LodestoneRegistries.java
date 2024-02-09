package team.lodestar.lodestone.registry;

import team.lodestar.lodestone.registry.builtin.LodestoneBlockEntityRegistry;
import team.lodestar.lodestone.registry.builtin.LodestoneEntityAttributeRegistry;
import team.lodestar.lodestone.registry.builtin.LodestonePaintingRegistry;
import team.lodestar.lodestone.registry.builtin.LodestoneParticleTypeRegistry;
import team.lodestar.lodestone.registry.builtin.LodestonePlacementModifierTypeRegistry;
import team.lodestar.lodestone.registry.builtin.LodestoneRecipeSerializerRegistry;
import team.lodestar.lodestone.registry.custom.LodestoneFireEffectReigstry;
import team.lodestar.lodestone.registry.custom.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.registry.custom.LodestoneTags;
import team.lodestar.lodestone.registry.custom.LodestoneWorldEventTypeRegistry;

public final class LodestoneRegistries {
    public static void bootstrap() {
        LodestoneBlockEntityRegistry.bootstrap();
        LodestoneEntityAttributeRegistry.bootstrap();
        LodestonePaintingRegistry.bootstrap();
        LodestoneParticleTypeRegistry.bootstrap();
        LodestonePlacementModifierTypeRegistry.bootstrap();
        LodestoneRecipeSerializerRegistry.bootstrap();
        LodestoneFireEffectReigstry.bootstrap();
        LodestoneScreenParticleRegistry.bootstrap();
        LodestoneTags.bootstrap();
        LodestoneWorldEventTypeRegistry.bootstrap();
    }
}
