package team.lodestar.lodestone.systems.placementassistance;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import java.util.function.Predicate;

/**
 * A placement assistant is a helpful client-sided system which allows you to do custom things when a player places something.
 */
public interface PlacementAssistant {
    void onPlaceBlock(PlayerEntity player, World world, BlockHitResult hitResult, BlockState state, ItemStack stack);

    void onObserveBlock(PlayerEntity player, World world, BlockHitResult hitResult, BlockState state, ItemStack stack);

    Predicate<ItemStack> canAssist();
}
