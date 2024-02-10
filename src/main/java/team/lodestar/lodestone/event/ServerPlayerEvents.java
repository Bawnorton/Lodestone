package team.lodestar.lodestone.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPlayerEvents {
    Event<ServerPlayerEvents> EVENT = EventFactory.createArrayBacked(ServerPlayerEvents.class, (listeners) -> (player) -> {
        for (ServerPlayerEvents event : listeners) {
            event.onPlayerTick(player);
        }
    });

    void onPlayerTick(ServerPlayerEntity player);
}
