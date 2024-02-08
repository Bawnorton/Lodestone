package com.bawnorton.silkstone.registry.builtin;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;

public final class SilkstonePaintingRegistry implements SilkstoneBuiltinRegistry<PaintingVariant> {
    private static final SilkstonePaintingRegistry INSTANCE = new SilkstonePaintingRegistry();

    public PaintingVariant LE_FUNNY = register("lefunny", 64, 64);
    public PaintingVariant MICRO_FUNNY = register("microfunny", 16, 16);

    public static void bootstrap() {
        // no-op
    }

    public static SilkstonePaintingRegistry get() {
        return INSTANCE;
    }

    private PaintingVariant register(String name, int width, int height) {
        return register(Registries.PAINTING_VARIANT, name, new PaintingVariant(width, height));
    }

    private SilkstonePaintingRegistry() {
    }
}
