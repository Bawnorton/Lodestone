package team.lodestar.lodestone.client.systems.particle.data;

import net.minecraft.util.math.MathHelper;
import team.lodestar.lodestone.systems.easing.Easing;

public class GenericParticleData {
    public final float startingValue, middleValue, endingValue;
    public final float coefficient;
    public final Easing startToMiddleEasing, middleToEndEasing;

    public float valueMultiplier = 1;
    public float coefficientMultiplier = 1;

    protected GenericParticleData(float startingValue, float middleValue, float endingValue, float coefficient, Easing startToMiddleEasing, Easing middleToEndEasing) {
        this.startingValue = startingValue;
        this.middleValue = middleValue;
        this.endingValue = endingValue;
        this.coefficient = coefficient;
        this.startToMiddleEasing = startToMiddleEasing;
        this.middleToEndEasing = middleToEndEasing;
    }

    public GenericParticleData multiplyCoefficient(float coefficientMultiplier) {
        this.coefficientMultiplier *= coefficientMultiplier;
        return this;
    }

    public GenericParticleData multiplyValue(float valueMultiplier) {
        this.valueMultiplier *= valueMultiplier;
        return this;
    }

    public GenericParticleData overrideCoefficientMultiplier(float coefficientMultiplier) {
        this.coefficientMultiplier = coefficientMultiplier;
        return this;
    }

    public GenericParticleData overrideValueMultiplier(float valueMultiplier) {
        this.valueMultiplier = valueMultiplier;
        return this;
    }

    public boolean isTrinary() {
        return endingValue != -1;
    }

    public float getProgress(float age, float lifetime) {
        return MathHelper.clamp((age * coefficient * coefficientMultiplier) / lifetime, 0, 1);
    }

    public float getValue(float age, float lifetime) {
        float progress = getProgress(age, lifetime);
        float result;
        if (isTrinary()) {
            if (progress >= 0.5f) {
                result = MathHelper.lerp(middleToEndEasing.ease(progress - 0.5f, 0, 1, 0.5f), middleValue, endingValue);
            } else {
                result = MathHelper.lerp(startToMiddleEasing.ease(progress, 0, 1, 0.5f), startingValue, middleValue);
            }
        } else {
            result = MathHelper.lerp(startToMiddleEasing.ease(progress, 0, 1, 1), startingValue, middleValue);
        }
        return result * valueMultiplier;
    }

    public static GenericParticleDataBuilder create(float value) {
        return new GenericParticleDataBuilder(value, value, -1);
    }

    public static GenericParticleDataBuilder create(float startingValue, float endingValue) {
        return new GenericParticleDataBuilder(startingValue, endingValue, -1);
    }

    public static GenericParticleDataBuilder create(float startingValue, float middleValue, float endingValue) {
        return new GenericParticleDataBuilder(startingValue, middleValue, endingValue);
    }

    public static GenericParticleData constrictTransparency(GenericParticleData data) {
        float startingValue = MathHelper.clamp(data.startingValue, 0, 1);
        float middleValue = MathHelper.clamp(data.middleValue, 0, 1);
        float endingValue = data.endingValue == -1 ? -1 : MathHelper.clamp(data.endingValue, 0, 1);
        float coefficient = data.coefficient;
        Easing startToMiddleEasing = data.startToMiddleEasing;
        Easing middleToEndEasing = data.middleToEndEasing;
        return new GenericParticleData(startingValue, middleValue, endingValue, coefficient, startToMiddleEasing, middleToEndEasing);
    }
}