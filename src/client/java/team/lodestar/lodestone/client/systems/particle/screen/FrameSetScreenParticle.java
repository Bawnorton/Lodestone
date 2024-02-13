package team.lodestar.lodestone.client.systems.particle.screen;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import team.lodestar.lodestone.client.systems.particle.options.ScreenParticleOptions;
import java.util.ArrayList;
import java.util.List;

public class FrameSetScreenParticle extends GenericScreenParticle {
    public List<Integer> frameSet = new ArrayList<>();

    public FrameSetScreenParticle(ClientWorld world, ScreenParticleOptions data, ParticleManager.SimpleSpriteProvider spriteProvider, double x, double y, double velocityX, double motionY) {
        super(world, data, spriteProvider, x, y, velocityX, motionY);
    }

    @Override
    public void tick() {
        pickSprite(frameSet.get(age));
        super.tick();
    }

    protected void addLoop(int min, int max, int times) {
        for (int i = 0; i < times; i++) {
            addFrames(min, max);
        }
    }

    protected void addFrames(int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(i);
        }
    }

    protected void insertFrames(int insertIndex, int min, int max) {
        for (int i = min; i <= max; i++) {
            frameSet.add(insertIndex, i);
        }
    }
}