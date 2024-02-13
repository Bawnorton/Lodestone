package team.lodestar.lodestone.client.systems.particle.screen.base;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.random.Random;
import team.lodestar.lodestone.client.systems.particle.renderlayer.LodestoneScreenParticleRenderLayer;

public abstract class ScreenParticle {
    public final ClientWorld world;
    public double prevX;
    public double prevY;
    public double x;
    public double y;
    public double velocityX;
    public double velocityY;
    public double movedX;
    public double movedY;
    public boolean removed;
    public final Random random = Random.create();
    public int age;
    public int lifetime;
    public float gravity;
    public float size = 1;
    public float red = 1.0F;
    public float green = 1.0F;
    public float blue = 1.0F;
    public float alpha = 1.0F;
    public float roll;
    public float prevRoll;
    public float friction = 0.98F;

    protected ScreenParticle(ClientWorld world, double x, double y) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.lifetime = (int)(4.0F / (this.random.nextFloat() * 0.9F + 0.1F));
    }

    public ScreenParticle(ClientWorld world, double x, double y, double speedX, double speedY) {
        this(world, x, y);
        this.velocityX = speedX + (Math.random() * 2.0D - 1.0D) * (double) 0.4F;
        this.velocityY = speedY + (Math.random() * 2.0D - 1.0D) * (double) 0.4F;
        double d0 = (Math.random() + Math.random() + 1.0D) * (double) 0.15F;
        double d1 = Math.sqrt(this.velocityX * this.velocityY + this.velocityY * this.velocityY);
        this.velocityX = this.velocityX / d1 * d0 * (double) 0.4F;
        this.velocityY = this.velocityY / d1 * d0 * (double) 0.4F + (double) 0.1F;
    }

    public void setParticleSpeed(double deltaX, double deltaY) {
        this.velocityX = deltaX;
        this.velocityY = deltaY;
    }

    public ScreenParticle setSize(float size) {
        this.size = size;
        return this;
    }

    public void setColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    protected void setAlpha(float pAlpha) {
        this.alpha = pAlpha;
    }

    public void setLifetime(int pParticleLifeTime) {
        this.lifetime = pParticleLifeTime;
    }

    public int getLifetime() {
        return this.lifetime;
    }

    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.velocityY -= 0.04D * (double) this.gravity;
            this.velocityY *= this.friction;
            this.velocityX *= this.friction;
            this.x += velocityX;
            this.y += velocityY;
            this.movedX += velocityX;
            this.movedY += velocityY;
        }
    }

    public abstract void render(BufferBuilder bufferBuilder);

    public abstract LodestoneScreenParticleRenderLayer getRenderLayer();

    public void remove() {
        this.removed = true;
    }

    public boolean isAlive() {
        return !this.removed;
    }
}
