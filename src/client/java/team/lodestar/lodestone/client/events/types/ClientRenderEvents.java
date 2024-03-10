package team.lodestar.lodestone.client.events.types;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ClientRenderEvents {
    public static final Event<RenderTickEvent> START_RENDER_TICK_EVENT = EventFactory.createArrayBacked(RenderTickEvent.class, listeners -> (tickDelta) -> {
        for (RenderTickEvent listener : listeners) {
            listener.onRenderTick(tickDelta);
        }
    });

    public static final Event<RenderTickEvent> END_RENDER_TICK_EVENT = EventFactory.createArrayBacked(RenderTickEvent.class, listeners -> (tickDelta) -> {
        for (RenderTickEvent listener : listeners) {
            listener.onRenderTick(tickDelta);
        }
    });

    @FunctionalInterface
    public interface RenderTickEvent {
        void onRenderTick(float tickDelta);
    }
}
