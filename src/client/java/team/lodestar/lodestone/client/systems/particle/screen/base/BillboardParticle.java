package team.lodestar.lodestone.client.systems.particle.screen.base;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.VecHelper;

public abstract class BillboardParticle extends ScreenParticle {
    protected float quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

    protected BillboardParticle(ClientWorld world, double x, double y) {
        super(world, x, y);
    }

    protected BillboardParticle(ClientWorld world, double x, double y, double speedX, double speedY) {
        super(world, x, y, speedX, speedY);
    }

    @Override
    public void render(BufferBuilder bufferBuilder) {
        float tickDelta = MinecraftClient.getInstance().getTickDelta();
        float size = getQuadSize(tickDelta) * 10;
        float minU = getMinU();
        float maxU = getMaxU();
        float minV = getMinV();
        float maxV = getMaxV();
        Vector3f[] vectors = applyRollAndScale(tickDelta, size);
        float quadZ = getQuadZPosition();
        bufferBuilder.vertex(vectors[0].x(), vectors[0].y(), quadZ).texture(maxU, maxV).color(this.red, this.green, this.blue, this.alpha).next();
        bufferBuilder.vertex(vectors[1].x(), vectors[1].y(), quadZ).texture(maxU, minV).color(this.red, this.green, this.blue, this.alpha).next();
        bufferBuilder.vertex(vectors[2].x(), vectors[2].y(), quadZ).texture(minU, minV).color(this.red, this.green, this.blue, this.alpha).next();
        bufferBuilder.vertex(vectors[3].x(), vectors[3].y(), quadZ).texture(minU, maxV).color(this.red, this.green, this.blue, this.alpha).next();
    }

    @NotNull
    private Vector3f[] applyRollAndScale(float tickDelta, float size) {
        float roll = MathHelper.lerp(tickDelta, this.prevRoll, this.roll);
        Vector3f[] vectors = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        Quaternionf rotation = new Quaternionf(new AxisAngle4f(roll, VecHelper.Vector3fHelper.ZP));
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vectors[i];
            vector3f.rotate(rotation);
            vector3f.mul(size);
            vector3f.add((float) x, (float) y, 0);
        }
        return vectors;
    }

    public float getQuadSize(float tickDelta) {
        return this.quadSize;
    }

    public float getQuadZPosition() {
        return 390;
    }

    protected abstract float getMinU();

    protected abstract float getMaxU();

    protected abstract float getMinV();

    protected abstract float getMaxV();
}