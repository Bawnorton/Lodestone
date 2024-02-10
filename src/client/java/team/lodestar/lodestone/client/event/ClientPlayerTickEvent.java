package team.lodestar.lodestone.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;

public interface ClientPlayerTickEvent {
    Event<ClientPlayerTickEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerTickEvent.class, (listeners) -> (player) -> {
        for (ClientPlayerTickEvent event : listeners) {
            event.onPlayerTick(player);
        }
    });

    void onPlayerTick(ClientPlayerEntity player);
}
