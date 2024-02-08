package com.bawnorton.silkstone.client.registry.custom;

import com.bawnorton.silkstone.client.event.ScreenEvents;
import com.bawnorton.silkstone.client.mixin.accessor.SimpleOptionsScreenAccessor;
import com.bawnorton.silkstone.client.registry.SilkstoneClientRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class SilkstoneOptionRegistry {
    private static final SilkstoneOptionRegistry INSTANCE = new SilkstoneOptionRegistry();

    public final SimpleOption<Double> SCREEN_SHAKE_INTENSITY = createSilderOption("silkstone.options.screenshakeIntensity");
    public final SimpleOption<Double> FIRE_OFFSET = createSilderOption("silkstone.options.fireOffset");

    public static void bootstrap() {
        ScreenEvents.POST_INIT.register((screen, children, addElement, removeElement) -> {
            if(screen instanceof AccessibilityOptionsScreen && screen instanceof SimpleOptionsScreenAccessor accessor) {
                accessor.getButtonList().addOptionEntry(
                        SilkstoneClientRegistries.OPTIONS.SCREEN_SHAKE_INTENSITY,
                        SilkstoneClientRegistries.OPTIONS.FIRE_OFFSET
                );
            }
        });
    }

    private SimpleOption<Double> createSilderOption(String key) {
        return new SimpleOption<>(key,
                SimpleOption.constantTooltip(Text.translatable(key + ".tooltip")),
                (text, value) -> value == 0.0D ? Text.translatable("options.generic_value", text, ScreenTexts.OFF) : Text.translatable("options.percent_value", text, (int) (value * 100)),
                SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(MathHelper::square, Math::sqrt),
                Codec.doubleRange(0, 1), 1D, value -> {}
        );
    }

    public static SilkstoneOptionRegistry get() {
        return INSTANCE;
    }

    private SilkstoneOptionRegistry() {
    }
}
