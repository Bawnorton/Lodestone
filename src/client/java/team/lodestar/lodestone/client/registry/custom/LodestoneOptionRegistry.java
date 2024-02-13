package team.lodestar.lodestone.client.registry.custom;

import team.lodestar.lodestone.client.events.types.ScreenEvents;
import team.lodestar.lodestone.client.mixin.accessor.SimpleOptionsScreenAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class LodestoneOptionRegistry {
    public static final SimpleOption<Double> SCREEN_SHAKE_INTENSITY = createSilderOption("lodestone.options.screenshakeIntensity");
    public static final SimpleOption<Double> FIRE_OFFSET = createSilderOption("lodestone.options.fireOffset");

    public static void bootstrap() {
        ScreenEvents.POST_INIT.register((screen, children, addElement, removeElement) -> {
            if(screen instanceof AccessibilityOptionsScreen && screen instanceof SimpleOptionsScreenAccessor accessor) {
                accessor.getButtonList().addOptionEntry(
                        SCREEN_SHAKE_INTENSITY,
                        FIRE_OFFSET
                );
            }
        });
    }

    private static SimpleOption<Double> createSilderOption(String key) {
        return new SimpleOption<>(key,
                SimpleOption.constantTooltip(Text.translatable(key + ".tooltip")),
                (text, value) -> value == 0.0D ? Text.translatable("options.generic_value", text, ScreenTexts.OFF) : Text.translatable("options.percent_value", text, (int) (value * 100)),
                SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(MathHelper::square, Math::sqrt),
                Codec.doubleRange(0, 1), 1D, value -> {}
        );
    }

    private LodestoneOptionRegistry() {
    }
}
