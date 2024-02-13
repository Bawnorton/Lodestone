package team.lodestar.lodestone.client.systems.particle.options;

import team.lodestar.lodestone.client.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.client.systems.particle.data.color.ColourParticleData;
import team.lodestar.lodestone.client.systems.particle.data.spin.SpinParticleData;
import java.awt.Color;
import java.util.function.Supplier;

public abstract class SimpleParticleOptions {
    public static final ColourParticleData DEFAULT_COLOUR = ColourParticleData.create(Color.WHITE, Color.WHITE).build();
    public static final SpinParticleData DEFAULT_SPIN = SpinParticleData.create(0).build();
    public static final GenericParticleData DEFAULT_GENERIC = GenericParticleData.create(1, 0).build();

    public ParticleSpritePicker spritePicker = ParticleSpritePicker.FIRST_INDEX;
    public ParticleDiscardFunctionType discardFunctionType = ParticleDiscardFunctionType.INVISIBLE;

    public ColourParticleData colorData = DEFAULT_COLOUR;
    public GenericParticleData transparencyData = DEFAULT_GENERIC;
    public GenericParticleData scaleData = DEFAULT_GENERIC;
    public SpinParticleData spinData = DEFAULT_SPIN;

    public Supplier<Integer> lifetimeSupplier = () -> 20;
    public Supplier<Integer> lifeDelaySupplier = () -> 0;
    public Supplier<Float> gravityStrengthSupplier = () -> 0f;

    public enum ParticleSpritePicker {
        FIRST_INDEX, LAST_INDEX, WITH_AGE, RANDOM_SPRITE
    }

    public enum ParticleDiscardFunctionType {
        NONE, INVISIBLE, ENDING_CURVE_INVISIBLE
    }

}
