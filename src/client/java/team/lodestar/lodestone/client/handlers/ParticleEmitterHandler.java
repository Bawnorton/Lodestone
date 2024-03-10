package team.lodestar.lodestone.client.handlers;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticleEmitterHandler {
    public static final Map<Item, List<ItemParticleSupplier>> EMITTERS = new HashMap<>();

    public static void bootstrap() {
        Registries.ITEM.stream()
                .filter(i -> i instanceof ItemParticleSupplier)
                .map(i -> Pair.of(i, (ItemParticleSupplier) i))
                .forEach(pair -> registerItemParticleEmitter(pair.getFirst(), pair.getSecond()));
    }

    public static void registerItemParticleEmitter(Item item, ItemParticleSupplier emitter) {
        if (EMITTERS.containsKey(item)) {
            EMITTERS.get(item).add(emitter);
        }
        else {
            EMITTERS.put(item, new ArrayList<>(List.of(emitter)));
        }
    }

    public static void registerItemParticleEmitter(ItemParticleSupplier emitter, Item... items) {
        for (Item item : items) {
            registerItemParticleEmitter(item, emitter);
        }
    }

    public interface ItemParticleSupplier {
        default void spawnEarlyParticles(ScreenParticleHolder target, World world, float tickDelta, ItemStack stack, float x, float y) {
        }

        default void spawnLateParticles(ScreenParticleHolder target, World world, float tickDelta, ItemStack stack, float x, float y) {
        }
    }
}