package team.lodestar.lodestone.handlers;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import team.lodestar.lodestone.events.PlayerInteractionEvents;
import team.lodestar.lodestone.systems.placementassistance.PlacementAssistant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlacementAssistantHandler {
    public static final List<PlacementAssistant> ASSISTANTS = new ArrayList<>();
    public static int animationTick = 0;
    public static BlockHitResult target;

    public static void bootstrap() {
        Registries.BLOCK.stream()
                .filter(block -> block instanceof PlacementAssistant)
                .map(block -> (PlacementAssistant) block)
                .forEach(ASSISTANTS::add);

        PlayerInteractionEvents.INTERACT_BLOCK_EVENT.register(PlacementAssistantHandler::placeBlock);
    }

    public static void placeBlock(PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        World world = player.getWorld();
        if(world.isClient()) {
            List<Pair<PlacementAssistant, ItemStack>> assistants = findAssistants(world, player, hitResult);
            for (Pair<PlacementAssistant, ItemStack> pair : assistants) {
                PlacementAssistant assistant = pair.getFirst();
                BlockState state = world.getBlockState(hitResult.getBlockPos());
                assistant.onPlaceBlock(player, world, hitResult, state, pair.getSecond());
            }
            animationTick = Math.max(0, animationTick - 5);
        }
    }

    public static void tick(PlayerEntity player, HitResult hitResult) {
        World world = player.getWorld();
        List<Pair<PlacementAssistant, ItemStack>> placementAssistants = findAssistants(world, player, hitResult);
        if (hitResult instanceof BlockHitResult blockHitResult && !blockHitResult.getType().equals(HitResult.Type.MISS)) {
            target = blockHitResult;
            for (Pair<PlacementAssistant, ItemStack> pair : placementAssistants) {
                PlacementAssistant assistant = pair.getFirst();
                BlockState state = world.getBlockState(blockHitResult.getBlockPos());
                assistant.onObserveBlock(player, world, blockHitResult, state, pair.getSecond());
            }
        } else {
            target = null;
        }
        if (target == null) {
            if (animationTick > 0) {
                animationTick = Math.max(animationTick - 2, 0);
            }
            return;
        }
        if (animationTick < 10) {
            animationTick++;
        }
    }

    private static List<Pair<PlacementAssistant, ItemStack>> findAssistants(World world, PlayerEntity player, HitResult hitResult) {
        if (!(hitResult instanceof BlockHitResult)) {
            return Collections.emptyList();
        }
        return findAssistants(world, player);
    }

    private static List<Pair<PlacementAssistant, ItemStack>> findAssistants(World world, PlayerEntity player) {
        if (world == null || player == null || player.isSneaking()) {
            return Collections.emptyList();
        }
        List<Pair<PlacementAssistant, ItemStack>> matchingAssistants = new ArrayList<>();
        for (Hand hand : Hand.values()) {
            ItemStack held = player.getStackInHand(hand);
            matchingAssistants.addAll(ASSISTANTS.stream().filter(s -> s.canAssist().test(held)).map(a -> Pair.of(a, held)).collect(Collectors.toCollection(ArrayList::new)));
        }
        return matchingAssistants;
    }

    public static float getCurrentAlpha() {
        return Math.min(animationTick / 10F, 1F);
    }
}