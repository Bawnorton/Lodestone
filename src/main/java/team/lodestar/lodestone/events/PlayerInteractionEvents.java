package team.lodestar.lodestone.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public final class PlayerInteractionEvents {
    public static final Event<InteractBlock> INTERACT_BLOCK_EVENT = EventFactory.createArrayBacked(InteractBlock.class, (listeners) -> (player, hand, hitResult) -> {
        for (InteractBlock event : listeners) {
            event.onInteractBlock(player, hand, hitResult);
        }
    });

    @FunctionalInterface
    public interface InteractBlock {
        void onInteractBlock(PlayerEntity player, Hand hand, BlockHitResult hitResult);
    }
}
