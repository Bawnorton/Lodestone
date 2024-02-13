package team.lodestar.lodestone.client.systems.particle.data.color;

import net.minecraft.util.math.MathHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import java.awt.Color;

public class ColourParticleData {
    public final float r1, g1, b1, r2, g2, b2;
    public final float colourCoefficient;
    public final Easing colourCurveEasing;

    public float coefficientMultiplier = 1;

    protected ColourParticleData(float r1, float g1, float b1, float r2, float g2, float b2, float colourCoefficient, Easing colourCurveEasing) {
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.colourCoefficient = colourCoefficient;
        this.colourCurveEasing = colourCurveEasing;
    }

    public ColourParticleData multiplyCoefficient(float coefficientMultiplier) {
        this.coefficientMultiplier *= coefficientMultiplier;
        return this;
    }

    public ColourParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
        this.coefficientMultiplier = coefficientMultiplier;
        return this;
    }

    public float getProgress(float age, float lifetime) {
        return MathHelper.clamp((age * colourCoefficient * coefficientMultiplier) / lifetime, 0, 1);
    }

    public ColourParticleDataBuilder copy() {
        return create(r1, g1, b1, r2, g2, b2).setCoefficient(colourCoefficient).setEasing(colourCurveEasing);
    }

    public static ColourParticleDataBuilder create(float r1, float g1, float b1, float r2, float g2, float b2) {
        return new ColourParticleDataBuilder(r1, g1, b1, r2, g2, b2);
    }

    public static ColourParticleDataBuilder create(Color start, Color end) {
        return create(start.getRed() / 255f, start.getGreen() / 255f, start.getBlue() / 255f, end.getRed() / 255f, end.getGreen() / 255f, end.getBlue() / 255f);
    }

}
