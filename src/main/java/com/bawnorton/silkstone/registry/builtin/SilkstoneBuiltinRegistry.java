package com.bawnorton.silkstone.registry.builtin;

import com.bawnorton.silkstone.Silkstone;
import net.minecraft.registry.Registry;
import java.util.function.Supplier;

public interface SilkstoneBuiltinRegistry<T> {
    default T register(Registry<T> registry, String name, T object) {
        return Registry.register(registry, Silkstone.id(name), object);
    }

    default T register(Registry<T> registry, String name, Supplier<T> object) {
        return register(registry, name, object.get());
    }
}
