package team.lodestar.lodestone.handlers;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import team.lodestar.lodestone.attachment.LodestoneServerPlayerDataAttachment;
import team.lodestar.lodestone.attachment.LodestoneWorldDataAttachment;
import team.lodestar.lodestone.registry.custom.LodestoneWorldEventTypeRegistry;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;
import java.util.Iterator;

public class WorldEventHandler {
    public static void bootstrap() {
        ServerPlayConnectionEvents.JOIN.register(WorldEventHandler::playerJoin);
        ServerTickEvents.END_WORLD_TICK.register(WorldEventHandler::tick);
    }

    public static <T extends WorldEventInstance> T addWorldEvent(World world, T instance) {
        return addWorldEvent(world, true, instance);
    }
    public static <T extends WorldEventInstance> T addWorldEvent(World world, boolean shouldStart, T instance) {
        LodestoneWorldDataAttachment.getAttachmentOptional(world).ifPresent(attachment -> {
            attachment.inboundWorldEvents.add(instance);
            if (shouldStart) {
                instance.start(world);
            }
            instance.sync(world);
        });
        return instance;
    }

    public static void playerJoin(ServerPlayNetworkHandler networkHandler, PacketSender sender, MinecraftServer server) {
        ServerPlayerEntity player =  networkHandler.player;
        ServerWorld world = player.getServerWorld();
        LodestoneServerPlayerDataAttachment.getAttachmentOptional(player)
                .flatMap(attachment -> LodestoneWorldDataAttachment.getAttachmentOptional(world))
                .ifPresent(worldCapability -> {
                    for (WorldEventInstance instance : worldCapability.activeWorldEvents) {
                        if (instance.isClientSynced()) {
                            WorldEventInstance.sync(instance, player);
                        }
                    }
                });
    }

    public static void tick(World world) {
        LodestoneWorldDataAttachment.getAttachmentOptional(world).ifPresent(c -> {
            c.activeWorldEvents.addAll(c.inboundWorldEvents);
            c.inboundWorldEvents.clear();
            Iterator<WorldEventInstance> iterator = c.activeWorldEvents.iterator();
            while (iterator.hasNext()) {
                WorldEventInstance instance = iterator.next();
                if (instance.discarded) {
                    iterator.remove();
                } else {
                    instance.tick(world);
                }
            }
        });
    }

    public static void writeNbt(LodestoneWorldDataAttachment attachment, NbtCompound tag) {
        NbtCompound worldTag = new NbtCompound();
        worldTag.putInt("worldEventCount", attachment.activeWorldEvents.size());
        for (int i = 0; i < attachment.activeWorldEvents.size(); i++) {
            WorldEventInstance instance = attachment.activeWorldEvents.get(i);
            NbtCompound instanceTag = new NbtCompound();
            instance.serializeNBT(instanceTag);
            worldTag.put("worldEvent_" + i, instanceTag);
        }
        tag.put("worldEventData", worldTag);
    }

    public static void readNbt(LodestoneWorldDataAttachment attachment, NbtCompound tag) {
        attachment.activeWorldEvents.clear();
        NbtCompound worldTag = tag.getCompound("worldEventData");
        int worldEventCount = worldTag.getInt("worldEventCount");
        for (int i = 0; i < worldEventCount; i++) {
            NbtCompound instanceTag = worldTag.getCompound("worldEvent_" + i);
            WorldEventType reader = LodestoneWorldEventTypeRegistry.EVENT_TYPES.get(instanceTag.getString("type"));
            WorldEventInstance eventInstance = reader.createInstance(instanceTag);
            attachment.activeWorldEvents.add(eventInstance);
        }
    }

    /*
    public static class ClientOnly {
        public static void renderWorldEvents(PoseStack stack, float partialTicks) {
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