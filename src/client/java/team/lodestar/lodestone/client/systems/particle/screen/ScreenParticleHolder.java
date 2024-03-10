package team.lodestar.lodestone.client.systems.particle.screen;

import team.lodestar.lodestone.client.systems.particle.renderlayer.LodestoneScreenParticleRenderLayer;
import team.lodestar.lodestone.client.systems.particle.screen.base.ScreenParticle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScreenParticleHolder {
    public final Map<LodestoneScreenParticleRenderLayer, List<ScreenParticle>> particles = new HashMap<>();

    public void tick() {
        particles.forEach((pair, particles) -> {
            Iterator<ScreenParticle> iterator = particles.iterator();
            while (iterator.hasNext()) {
                ScreenParticle particle = iterator.next();
                particle.tick();
                if (!particle.isAlive()) {
                    iterator.remove();
                }
            }
        });
    }

    public void addFrom(ScreenParticleHolder otherHolder) {
        particles.putAll(otherHolder.particles);
    }

    public boolean isEmpty() {
        return particles.values().stream().allMatch(List::isEmpty);
    }
}
