package team.lodestar.lodestone.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import team.lodestar.lodestone.attachment.LodestoneEntityDataAttachment;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;

public class FireEffectHandler {
    public static void entityUpdate(Entity entity) {
        FireEffectInstance instance = getFireEffectInstance(entity);
        if (instance != null) {
            instance.tick(entity);
            if (!instance.isValid()) {
                setCustomFireInstance(entity, null);
            }
        }
    }

    public static void onVanillaFireTimeUpdate(Entity entity) {
        setCustomFireInstance(entity, null);
    }

    public static FireEffectInstance getFireEffectInstance(Entity entity) {
        return LodestoneEntityDataAttachment.getAttachment(entity).fireEffectInstance;
    }

    public static void setCustomFireInstance(Entity entity, FireEffectInstance instance) {
        LodestoneEntityDataAttachment.getAttachmentOptional(entity).ifPresent(attachment -> {
            attachment.fireEffectInstance = instance;
            if (attachment.fireEffectInstance != null) {
                if (entity.getFireTicks() > 0) {
                    entity.setFireTicks(0);
                }
            // TODO: Sync to client
                if (!entity.getWorld().isClient()) {
//                    attachment.fireEffectInstance.sync(entity);
                }
            }
//            else if (!entity.level.isClientSide) {
//                LodestonePacketRegistry.ORTUS_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClearFireEffectInstancePacket(entity.getId()));
//            }
        });
    }

    public static void writeNbt(LodestoneEntityDataAttachment attachment, NbtCompound tag) {
        attachment.writeNbt(tag);
    }

    public static void readNbt(LodestoneEntityDataAttachment attachment, NbtCompound tag) {
        attachment.fireEffectInstance = FireEffectInstance.readNbt(tag);
    }

    /*
    public static class ClientOnly {
        public static void renderUIFireEffect(Minecraft pMinecraft, PoseStack pPoseStack) {
            if (pMinecraft.player != null) {
                if (getFireEffectInstance(pMinecraft.player) == null) {
                    return;
                }
            }
            FireEffectInstance instance = getFireEffectInstance(pMinecraft.player);
            FireEffectRenderer<FireEffectInstance> renderer = LodestoneFireEffectRendererRegistry.RENDERERS.get(instance.type);
            if (renderer != null) {
                if (renderer.canRender(instance)) {
                    renderer.renderScreen(instance, pMinecraft, pPoseStack);
                }
            }
        }

        public static void renderWorldFireEffect(PoseStack pMatrixStack, MultiBufferSource pBuffer, Camera camera, Entity pEntity) {
            if (getFireEffectInstance(pEntity) == null) {
                return;
            }
            FireEffectInstance instance = getFireEffectInstance(pEntity);
            FireEffectRenderer<FireEffectInstance> renderer = LodestoneFireEffectRendererRegistry.RENDERERS.get(instance.type);
            if (renderer != null) {
                if (renderer.canRender(instance)) {
                    renderer.renderWorld(instance, pMatrixStack, pBuffer, camera, pEntity);
                }
            }
        }
    }
     */
}