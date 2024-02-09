package team.lodestar.lodestone.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.FogShape;

public final class FogEvents {
    public static final Event<RenderFogEvent> RENDER_FOG_EVENT = EventFactory.createArrayBacked(RenderFogEvent.class, listeners -> (fogStart, fogEnd, shape) -> {
        for (RenderFogEvent listener : listeners) {
            listener.onRenderFog(fogStart, fogEnd, shape);
        }
    });

    public static final Event<FogColourEvent> FOG_COLOUR_EVENT = EventFactory.createArrayBacked(FogColourEvent.class, listeners -> (red, green, blue) -> {
        for (FogColourEvent listener : listeners) {
            listener.onFogColour(red, green, blue);
        }
    });

    public interface RenderFogEvent {
        void onRenderFog(float fogStart, float fogEnd, FogShape shape);
    }

    public interface FogColourEvent {
        void onFogColour(float red, float green, float blue);
    }
}
