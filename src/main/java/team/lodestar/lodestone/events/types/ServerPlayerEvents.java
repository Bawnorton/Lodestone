package team.lodestar.lodestone.events.types;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public final class ServerPlayerEvents {
    public static final Event<Tick> TICK_EVENT = EventFactory.createArrayBacked(Tick.class, (listeners) -> (player) -> {
        for (Tick event : listeners) {
            event.onPlayerTick(player);
        }
    });

    @FunctionalInterface
    public interface Tick {
        void onPlayerTick(ServerPlayerEntity player);
    }
}
