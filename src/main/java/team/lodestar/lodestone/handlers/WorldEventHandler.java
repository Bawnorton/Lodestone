package team.lodestar.lodestone.handlers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import team.lodestar.lodestone.attachment.LodestoneAttachments;
import team.lodestar.lodestone.attachment.PlayerDataAttachment;
import team.lodestar.lodestone.attachment.WorldDataAttachment;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import java.util.Iterator;
import java.util.List;

public class WorldEventHandler {
    public static void bootstrap() {
        ServerPlayConnectionEvents.JOIN.register(WorldEventHandler::playerJoin);
        ServerTickEvents.END_WORLD_TICK.register(WorldEventHandler::tick);
    }

    public static <T extends WorldEventInstance> T addWorldEvent(ServerWorld world, T instance) {
        return addWorldEvent(world, true, instance);
    }
    public static <T extends WorldEventInstance> T addWorldEvent(ServerWorld world, boolean shouldStart, T instance) {
        WorldDataAttachment attachment = world.getAttached(LodestoneAttachments.WORLD_EVENT_DATA);
        if (attachment != null) {
            attachment.inboundWorldEvents().add(instance);
            if (shouldStart) {
                instance.start(world);
            }
            instance.sync(world);
        }
        return instance;
    }

    public static void playerJoin(ServerPlayNetworkHandler networkHandler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player =  networkHandler.player;
        ServerWorld world = player.getServerWorld();
        PlayerDataAttachment playerAttachment = player.getAttached(LodestoneAttachments.PLAYER_DATA);
        if (playerAttachment == null) return;

        WorldDataAttachment worldEventsAttachment = world.getAttached(LodestoneAttachments.WORLD_EVENT_DATA);
        if (worldEventsAttachment == null) return;

        for (WorldEventInstance instance : worldEventsAttachment.activeWorldEvents()) {
            if (instance.isClientSynced()) {
                WorldEventInstance.sync(instance, player);
            }
        }
    }

    public static void tick(ServerWorld world) {
        WorldDataAttachment attachment = world.getAttached(LodestoneAttachments.WORLD_EVENT_DATA);
        if (attachment == null) return;

        List<WorldEventInstance> activeWorldEvents = attachment.activeWorldEvents();
        List<WorldEventInstance> inboundWorldEvents = attachment.inboundWorldEvents();
        activeWorldEvents.addAll(inboundWorldEvents);
        inboundWorldEvents.clear();
        Iterator<WorldEventInstance> iterator = activeWorldEvents.iterator();
        while (iterator.hasNext()) {
            WorldEventInstance instance = iterator.next();
            if (instance.discarded) {
                iterator.remove();
            } else {
                instance.tick(world);
            }
        }
    }

    /*
    public static class ClientOnly {
        public static void renderWorldEvents(MatrixStack stack, float partialTicks) {
            LodestoneWorldDataCapability.getCapabilityOptional(Minecraft.getInstance().level).ifPresent(capability -> {
                for (WorldEventInstance instance : capability.activeWorldEvents) {
                    WorldEventRenderer<WorldEventInstance> renderer = LodestoneWorldEventRendererRegistry.RENDERERS.get(instance.type);
                    if (renderer != null) {
                        if (renderer.canRender(instance)) {
                            renderer.render(instance, stack, RenderHandler.DELAYED_RENDER, partialTicks);
                        }
                    }
                }
            });
        }
    }
     */
}