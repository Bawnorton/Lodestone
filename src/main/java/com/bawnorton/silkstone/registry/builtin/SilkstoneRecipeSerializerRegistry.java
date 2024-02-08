package com.bawnorton.silkstone.registry.builtin;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import java.util.function.Supplier;

public final class SilkstoneRecipeSerializerRegistry implements SilkstoneBuiltinRegistry<RecipeSerializer<?>> {
    private static final SilkstoneRecipeSerializerRegistry INSTANCE = new SilkstoneRecipeSerializerRegistry();

/* TODO: Add recipe serializers
    public RecipeSerializer<NBTCarryRecipe> NBT_CARRY_RECIPE_SERIALIZER = register("nbt_carry", NBTCarryRecipe.Serializer::new);
 */

    public static void bootstrap() {
        // no-op
    }

    public static SilkstoneRecipeSerializerRegistry get() {
        return INSTANCE;
    }

    private RecipeSerializer<?> register(String name, Supplier<RecipeSerializer<?>> supplier) {
        return register(Registries.RECIPE_SERIALIZER, name, supplier);
    }

    private SilkstoneRecipeSerializerRegistry() {
    }
}
