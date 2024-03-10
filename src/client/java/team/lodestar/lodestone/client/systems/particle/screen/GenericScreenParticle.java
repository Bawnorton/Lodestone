package team.lodestar.lodestone.client.systems.particle.screen;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3d;
import team.lodestar.lodestone.client.access.Accessor;
import team.lodestar.lodestone.client.handlers.ScreenParticleHandler;
import team.lodestar.lodestone.client.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.client.systems.particle.data.color.ColourParticleData;
import team.lodestar.lodestone.client.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.client.systems.particle.options.ScreenParticleOptions;
import team.lodestar.lodestone.client.systems.particle.options.SimpleParticleOptions;
import team.lodestar.lodestone.client.systems.particle.renderlayer.LodestoneScreenParticleRenderLayer;
import team.lodestar.lodestone.client.systems.particle.screen.base.SpriteBillboardScreenParticle;
import java.awt.Color;
import java.util.function.Consumer;

public class GenericScreenParticle extends SpriteBillboardScreenParticle {
    private final LodestoneScreenParticleRenderLayer renderLayer;
    protected final ParticleManager.SimpleSpriteProvider spriteProvider;
    protected final SimpleParticleOptions.ParticleSpritePicker spritePicker;
    protected final SimpleParticleOptions.ParticleDiscardFunctionType discardFunctionType;
    protected final ColourParticleData colourData;
    protected final GenericParticleData transparencyData;
    protected final GenericParticleData scaleData;
    protected final SpinParticleData spinData;
    protected final Consumer<GenericScreenParticle> actor;
    private final boolean tracksStack;
    private final double stackTrackXOffset;
    private final double stackTrackYOffset;

    private boolean reachedPositiveAlpha;
    private boolean reachedPositiveScale;

    private int lifeDelay;

    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericScreenParticle(ClientWorld world, ScreenParticleOptions options, ParticleManager.SimpleSpriteProvider spriteProvider, double x, double y, double velocityX, double velocityY) {
        super(world, x, y);
        this.renderLayer = options.renderLayer;
        this.spriteProvider = spriteProvider;
        this.spritePicker = options.spritePicker;
        this.discardFunctionType = options.discardFunctionType;
        this.colourData = options.colorData;
        this.transparencyData = GenericParticleData.constrictTransparency(options.transparencyData);
        this.scaleData = options.scaleData;
        this.spinData = options.spinData;
        this.actor = options.actor;
        this.tracksStack = options.tracksStack;
        this.stackTrackXOffset = options.stackTrackXOffset;
        this.stackTrackYOffset = options.stackTrackYOffset;
        this.roll = options.spinData.spinOffset + options.spinData.startingValue;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        this.setLifetime(options.lifetimeSupplier.get());
        this.lifeDelay = options.lifeDelaySupplier.get();
        this.gravity = options.gravityStrengthSupplier.get();
        this.friction = 1;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, colourData.r1)), (int) (255 * Math.min(1.0f, colourData.g1)), (int) (255 * Math.min(1.0f, colourData.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, colourData.r2)), (int) (255 * Math.min(1.0f, colourData.g2)), (int) (255 * Math.min(1.0f, colourData.b2)), hsv2);
        updateTraits();
        if (getSpritePicker().equals(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)) {
            pickSprite(spriteProvider);
        }
        if (getSpritePicker().equals(SimpleParticleOptions.ParticleSpritePicker.FIRST_INDEX) || getSpritePicker().equals(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)) {
            pickSprite(0);
        }
        if (getSpritePicker().equals(SimpleParticleOptions.ParticleSpritePicker.LAST_INDEX)) {
            pickSprite(Accessor.of(spriteProvider).getSprites().size() - 1);
        }
        updateTraits();
    }

    public SimpleParticleOptions.ParticleSpritePicker getSpritePicker() {
        return spritePicker;
    }

    public void pickSprite(int spriteIndex) {
        if (spriteIndex < Accessor.of(spriteProvider).getSprites().size() && spriteIndex >= 0) {
            setSprite(Accessor.of(spriteProvider).getSprites().get(spriteIndex));
        }
    }

    public void pickColor(float colorCoeff) {
        float h = MathHelper.lerpAngleDegrees(colorCoeff, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = MathHelper.lerp(colorCoeff, hsv1[1], hsv2[1]);
        float v = MathHelper.lerp(colorCoeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = ColorHelper.Argb.getRed(packed) / 255.0f;
        float g = ColorHelper.Argb.getGreen(packed) / 255.0f;
        float b = ColorHelper.Argb.getBlue(packed) / 255.0f;
        setColor(r, g, b);
    }

    public float getCurve(float multiplier) {
        return MathHelper.clamp((age * multiplier) / (float) lifetime, 0, 1);
    }

    protected void updateTraits() {
        boolean shouldAttemptRemoval = discardFunctionType == SimpleParticleOptions.ParticleDiscardFunctionType.INVISIBLE;
        if (discardFunctionType == SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE) {

            if (scaleData.getProgress(age, lifetime) > 0.5f || transparencyData.getProgress(age, lifetime) > 0.5f) {
                shouldAttemptRemoval = true;
            }
        }
        if (shouldAttemptRemoval) {
            if ((reachedPositiveAlpha && alpha <= 0) || (reachedPositiveScale && quadSize <= 0)) {
                remove();
                return;
            }
        }

        if (!reachedPositiveAlpha && alpha > 0) {
            reachedPositiveAlpha = true;
        }
        if (!reachedPositiveScale && quadSize > 0) {
            reachedPositiveScale = true;
        }
        pickColor(colourData.colourCurveEasing.ease(colourData.getProgress(age, lifetime), 0, 1, 1));

        quadSize = scaleData.getValue(age, lifetime);
        alpha = MathHelper.clamp(transparencyData.getValue(age, lifetime), 0, 1);
        prevRoll = roll;
        roll += spinData.getValue(age, lifetime);

        if (actor != null) {
            actor.accept(this);
        }
    }

    @Override
    public void render(BufferBuilder bufferBuilder) {
        if (lifeDelay > 0) {
            return;
        }
        if (tracksStack) {
            x = ScreenParticleHandler.currentItemX + stackTrackXOffset + movedX;
            y = ScreenParticleHandler.currentItemY + stackTrackYOffset + movedY;
        }
        super.render(bufferBuilder);
    }

    @Override
    public void tick() {
        if (lifeDelay > 0) {
            lifeDelay--;
            return;
        }
        updateTraits();
        if (getSpritePicker().equals(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)) {
            setSpriteFromAge(spriteProvider);
        }
        super.tick();
    }

    @Override
    public LodestoneScreenParticleRenderLayer getRenderLayer() {
        return renderLayer;
    }

    public void setParticleSpeed(Vector3d speed) {
        setParticleSpeed(speed.x, speed.y);
    }
}