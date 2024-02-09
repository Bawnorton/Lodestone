package team.lodestar.lodestone.registry.builtin;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import java.util.function.Supplier;

public final class LodestoneParticleTypeRegistry {
/* TODO: Add particle types
    public static final ParticleType<?> WISP_PARTICLE = register("wisp", LodestoneParticleType::new);;
    public static final ParticleType<?> SMOKE_PARTICLE = register("smoke", LodestoneParticleType::new);
    public static final ParticleType<?> SPARKLE_PARTICLE = register("sparkle", LodestoneParticleType::new);
    public static final ParticleType<?> TWINKLE_PARTICLE = register("twinkle", LodestoneParticleType::new);
    public static final ParticleType<?> STAR_PARTICLE = register("star", LodestoneParticleType::new);
    public static final ParticleType<?> SPARK_PARTICLE = register("spark", LodestoneSparkParticleType::new);
    public static final ParticleType<?> TERRAIN_PARTICLE = register("terrain", LodestoneTerrainParticleType::new);
    public static final ParticleType<?> ITEM_PARTICLE = register("item", LodestoneItemCrumbsParticleType::new);
 */

    public static void bootstrap() {
        // no-op
    }

    private static ParticleType<?> register(String name, Supplier<ParticleType<?>> supplier) {
        return Registry.register(Registries.PARTICLE_TYPE, name, supplier.get());
    }

    private LodestoneParticleTypeRegistry() {
    }
}
