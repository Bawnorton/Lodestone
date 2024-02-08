package com.bawnorton.silkstone.registry.custom;

public final class SilkstoneScreenParticleRegistry {
    private static final SilkstoneScreenParticleRegistry INSTANCE = new SilkstoneScreenParticleRegistry();

/* TODO: Add screen particle types
    public final List<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();
    public final ScreenParticleType<ScreenParticleOptions> WISP = register(new SilkstoneParticleType());
    public final ScreenParticleType<ScreenParticleOptions> SMOKE = register(new SilkstoneParticleType());
    public final ScreenParticleType<ScreenParticleOptions> SPARKLE = register(new SilkstoneParticleType());
    public final ScreenParticleType<ScreenParticleOptions> TWINKLE = register(new SilkstoneParticleType());
    public final ScreenParticleType<ScreenParticleOptions> STAR = register(new SilkstoneParticleType());
 */

    public static void bootstrap() {
        // no-op
    }

/* TODO: Add screen particle types
    private <T extends ScreenParticleOptions> ScreenParticleType<T> register(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }
    private <T extends ScreenParticleOptions> void register(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }
 */

    public static SilkstoneScreenParticleRegistry get() {
        return INSTANCE;
    }

    private SilkstoneScreenParticleRegistry() {
    }
}
