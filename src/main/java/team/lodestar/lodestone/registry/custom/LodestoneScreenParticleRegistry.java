package team.lodestar.lodestone.registry.custom;

public final class LodestoneScreenParticleRegistry {
/* TODO: Add screen particle types
    public static final List<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();
    public static final ScreenParticleType<ScreenParticleOptions> WISP = register(new LodestoneParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SMOKE = register(new LodestoneParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SPARKLE = register(new LodestoneParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> TWINKLE = register(new LodestoneParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> STAR = register(new LodestoneParticleType());
 */

    public static void bootstrap() {
        // no-op
    }

/* TODO: Add screen particle types
    private static <T extends ScreenParticleOptions> ScreenParticleType<T> register(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }
    private static <T extends ScreenParticleOptions> void register(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }
 */

    private LodestoneScreenParticleRegistry() {
    }
}
