package team.lodestar.lodestone.client.registry;

import team.lodestar.lodestone.client.registry.custom.LodestoneClientParticleTypeRegistry;
import team.lodestar.lodestone.client.registry.custom.LodestoneFireEffectRendererRegistry;
import team.lodestar.lodestone.client.registry.custom.LodestoneOptionRegistry;
import team.lodestar.lodestone.client.registry.custom.LodestoneRenderLayerRegistry;
import team.lodestar.lodestone.client.registry.custom.LodestoneShaderRegistry;
import team.lodestar.lodestone.client.registry.custom.LodestoneWorldEventRendererRegistry;

public final class LodestoneClientRegistries {
    public static void bootstrap() {
        LodestoneClientParticleTypeRegistry.bootstrap();
        LodestoneFireEffectRendererRegistry.bootstrap();
        LodestoneOptionRegistry.bootstrap();
        LodestoneRenderLayerRegistry.bootstrap();
        LodestoneShaderRegistry.bootstrap();
        LodestoneWorldEventRendererRegistry.bootstrap();
    }
}
