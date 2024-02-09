package team.lodestar.lodestone.registry.builtin;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class LodestonePaintingRegistry {
    public static PaintingVariant LE_FUNNY = register("lefunny", 64, 64);
    public static PaintingVariant MICRO_FUNNY = register("microfunny", 16, 16);

    public static void bootstrap() {
        // no-op
    }

    private static PaintingVariant register(String name, int width, int height) {
        return Registry.register(Registries.PAINTING_VARIANT, name, new PaintingVariant(width, height));
    }

    private LodestonePaintingRegistry() {
    }
}
