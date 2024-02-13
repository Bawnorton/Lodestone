package team.lodestar.lodestone.client.systems.particle.data.color;

import team.lodestar.lodestone.systems.easing.Easing;

public class ColourParticleDataBuilder {
    protected float r1, g1, b1, r2, g2, b2;
    protected float colourCoefficient = 1f;

    protected Easing colourCurveEasing = Easing.LINEAR;

    protected ColourParticleDataBuilder(float r1, float g1, float b1, float r2, float g2, float b2) {
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
    }

    public ColourParticleDataBuilder setCoefficient(float coefficient) {
        this.colourCoefficient = coefficient;
        return this;
    }

    public ColourParticleDataBuilder setEasing(Easing easing) {
        this.colourCurveEasing = easing;
        return this;
    }

    public ColourParticleData build() {
        return new ColourParticleData(r1, g1, b1, r2, g2, b2, colourCoefficient, colourCurveEasing);
    }
}
