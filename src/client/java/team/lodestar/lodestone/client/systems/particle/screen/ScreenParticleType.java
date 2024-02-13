package team.lodestar.lodestone.client.systems.particle.screen;

import net.minecraft.client.world.ClientWorld;
import team.lodestar.lodestone.client.systems.particle.options.ScreenParticleOptions;
import team.lodestar.lodestone.client.systems.particle.screen.base.ScreenParticle;

public class ScreenParticleType<T extends ScreenParticleOptions> {
    public ParticleProvider<T> provider;

    public interface ParticleProvider<T extends ScreenParticleOptions> {
        ScreenParticle createParticle(ClientWorld world, T options, double x, double y, double speedX, double speedY);
    }
}
