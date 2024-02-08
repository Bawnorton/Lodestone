package com.bawnorton.silkstone.client.registry.custom;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class SilkstoneClientParticleTypeRegistry {
    public static void bootstrap() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
    /* TODO: Add particle types
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.WISP_PARTICLE, LodestoneParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.SMOKE_PARTICLE, LodestoneParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.SPARKLE_PARTICLE, LodestoneParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.TWINKLE_PARTICLE, LodestoneParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.STAR_PARTICLE, LodestoneParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.SPARK_PARTICLE, LodestoneSparkParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.TERRAIN_PARTICLE, LodestoneTerrainParticleType.Factory::new);
        registry.register(SilkstoneRegistries.PARTICLE_TYPE.ITEM_PARTICLE, LodestoneItemCrumbsParticleType.Factory::new);
    */
    }
}
