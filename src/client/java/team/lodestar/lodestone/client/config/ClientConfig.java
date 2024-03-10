package team.lodestar.lodestone.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import team.lodestar.lodestone.Lodestone;
import team.lodestar.lodestone.systems.config.LodestoneConfig;

public class ClientConfig extends LodestoneConfig {
    public static ConfigValueHolder<Boolean> DELAYED_PARTICLE_RENDERING = new ConfigValueHolder<>(Lodestone.MOD_ID, "client/graphics/particle", builder ->
            builder.comment("Should particles render on the delayed buffer? This means they will properly render after clouds & water do, but could cause issues with mods like sodium.")
                    .define("buffer_particles", true));
    public static ConfigValueHolder<Double> FIRE_OVERLAY_OFFSET = new ConfigValueHolder<>(Lodestone.MOD_ID, "client/graphics/fire", builder ->
            builder.comment("Downwards offset create Minecraft's first-person fire overlay. Higher numbers cause it to visually display lower and free up more screen space.")
                    .defineInRange("fire_overlay_offset", 0d, 0d, 1d));
    public static ConfigValueHolder<Double> SCREENSHAKE_INTENSITY = new ConfigValueHolder<>(Lodestone.MOD_ID, "client/screenshake", builder ->
            builder.comment("Intensity create screenshake. Higher numbers increase amplitude. Disable to turn off screenshake.")
                    .defineInRange("screenshake_intensity", 1d, 0d, 5d));
    public static ConfigValueHolder<Boolean> ENABLE_SCREEN_PARTICLES = new ConfigValueHolder<>(Lodestone.MOD_ID, "client/screen_particles", builder ->
            builder.comment("Are screen particles enabled?")
                    .define("enable_screen_particles", true));

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        super(Lodestone.MOD_ID, "client", builder);
    }

    public static final ClientConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }
}