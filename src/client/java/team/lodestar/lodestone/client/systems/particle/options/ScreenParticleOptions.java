package team.lodestar.lodestone.client.systems.particle.options;

import team.lodestar.lodestone.client.systems.particle.renderlayer.LodestoneScreenParticleRenderLayer;
import team.lodestar.lodestone.client.systems.particle.screen.GenericScreenParticle;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleType;
import java.util.function.Consumer;

public class ScreenParticleOptions extends SimpleParticleOptions {
    public final ScreenParticleType<?> type;
    public LodestoneScreenParticleRenderLayer renderLayer = LodestoneScreenParticleRenderLayer.ADDITIVE;
    public Consumer<GenericScreenParticle> actor;

    public boolean tracksStack;
    public double stackTrackXOffset;
    public double stackTrackYOffset;

    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}
