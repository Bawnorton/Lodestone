package team.lodestar.lodestone.client.systems.particle.screen.base;

import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;

public abstract class SpriteBillboardScreenParticle extends BillboardParticle {
    protected Sprite sprite;

    protected SpriteBillboardScreenParticle(ClientWorld world, double x, double y) {
        super(world, x, y);
    }

    protected SpriteBillboardScreenParticle(ClientWorld world, double x, double y, double speedX, double speedY) {
        super(world, x, y, speedX, speedY);
    }

    protected void setSprite(Sprite pSprite) {
        this.sprite = pSprite;
    }

    protected float getMinU() {
        return this.sprite.getMinU();
    }

    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    protected float getMinV() {
        return this.sprite.getMinV();
    }

    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    public void pickSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }

    public void setSpriteFromAge(SpriteProvider spriteProvider) {
        if (!this.removed) {
            this.setSprite(spriteProvider.getSprite(this.age, this.lifetime));
        }
    }
}