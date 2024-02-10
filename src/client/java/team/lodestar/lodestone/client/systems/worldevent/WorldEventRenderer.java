package team.lodestar.lodestone.client.systems.worldevent;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;

public abstract class WorldEventRenderer<T extends WorldEventInstance> {
    public boolean canRender(T instance) {
        return false;
    }

    public void render(T instance, MatrixStack stack, VertexConsumerProvider provider, float deltaTicks) {

    }
}
