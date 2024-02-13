package team.lodestar.lodestone.client.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import team.lodestar.lodestone.client.attachment.LodestoneClientPlayerDataAttachment;
import team.lodestar.lodestone.client.events.types.FogEvents;
import team.lodestar.lodestone.client.handlers.RenderHandler;
import team.lodestar.lodestone.handlers.PlacementAssistantHandler;
import team.lodestar.lodestone.handlers.WorldEventHandler;

public final class ClientEventSubscriber {
    public static void bootstrap() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.world == null || client.player == null) return;

            if(client.isPaused()) return;

            WorldEventHandler.tick(client.world);
            PlacementAssistantHandler.tick(client.player, client.crosshairTarget);
            LodestoneClientPlayerDataAttachment.tick(client.player);
        });

        FogEvents.RENDER_FOG_EVENT.register(RenderHandler::cacheFogData);
        FogEvents.FOG_COLOUR_EVENT.register(RenderHandler::cacheFogData);
    }
}
