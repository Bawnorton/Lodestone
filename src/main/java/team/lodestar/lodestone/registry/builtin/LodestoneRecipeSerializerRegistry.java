package team.lodestar.lodestone.registry.builtin;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import java.util.function.Supplier;

public final class LodestoneRecipeSerializerRegistry {
/* TODO: Add recipe serializers
    public static RecipeSerializer<NBTCarryRecipe> NBT_CARRY_RECIPE_SERIALIZER = register("nbt_carry", NBTCarryRecipe.Serializer::new);
 */

    public static void bootstrap() {
        // no-op
    }

    private static RecipeSerializer<?> register(String name, Supplier<RecipeSerializer<?>> supplier) {
        return Registry.register(Registries.RECIPE_SERIALIZER, name, supplier.get());
    }

    private LodestoneRecipeSerializerRegistry() {
    }
}
