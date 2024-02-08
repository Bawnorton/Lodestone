package com.bawnorton.silkstone.registry.builtin;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import java.util.function.Supplier;

public final class SilkstoneParticleTypeRegistry implements SilkstoneBuiltinRegistry<ParticleType<?>> {
    private static final SilkstoneParticleTypeRegistry INSTANCE = new SilkstoneParticleTypeRegistry();

/* TODO: Add particle types
    public final ParticleType<?> WISP_PARTICLE = register("wisp", SilkstoneParticleType::new);;
    public final ParticleType<?> SMOKE_PARTICLE = register("smoke", SilkstoneParticleType::new);
    public final ParticleType<?> SPARKLE_PARTICLE = register("sparkle", SilkstoneParticleType::new);
    public final ParticleType<?> TWINKLE_PARTICLE = register("twinkle", SilkstoneParticleType::new);
    public final ParticleType<?> STAR_PARTICLE = register("star", SilkstoneParticleType::new);
    public final ParticleType<?> SPARK_PARTICLE = register("spark", SilkstoneSparkParticleType::new);
    public final ParticleType<?> TERRAIN_PARTICLE = register("terrain", SilkstoneTerrainParticleType::new);
    public final ParticleType<?> ITEM_PARTICLE = register("item", SilkstoneItemCrumbsParticleType::new);
 */

    public static void bootstrap() {
        // no-op
    }

    private ParticleType<?> register(String name, Supplier<ParticleType<?>> supplier) {
        return register(Registries.PARTICLE_TYPE, name, supplier);
    }

    public static SilkstoneParticleTypeRegistry get() {
        return INSTANCE;
    }

    private SilkstoneParticleTypeRegistry() {
    }
}
