package team.lodestar.lodestone.handlers;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.attachment.EntityDataAttachment;
import team.lodestar.lodestone.attachment.LodestoneAttachments;
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

    public static @Nullable FireEffectInstance getFireEffectInstance(Entity entity) {
        EntityDataAttachment attachment = entity.getAttached(LodestoneAttachments.ENTITY_DATA);
        if (attachment != null) {
            return attachment.fireEffectInstance;
        }
        return null;
    }

    public static void setCustomFireInstance(Entity entity, FireEffectInstance instance) {
        EntityDataAttachment attachment = new EntityDataAttachment(instance);
        entity.setAttached(LodestoneAttachments.ENTITY_DATA, attachment);
        if (attachment.fireEffectInstance != null) {
            if (entity.getFireTicks() > 0) {
                entity.setFireTicks(0);
            }
            if (!entity.getWorld().isClient) {
                attachment.fireEffectInstance.sync(entity);
            }
        } else if (!entity.getWorld().isClient()) {
//            LodestonePacketRegistry.ORTUS_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClearFireEffectInstancePacket(entity.getId()));
        }
    }

    /*
    public static class ClientOnly {
        public static void renderUIFireEffect(Minecraft pMinecraft, MatrixStack pPoseStack) {
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

        public static void renderWorldFireEffect(MatrixStack pMatrixStack, MultiBufferSource pBuffer, Camera camera, Entity pEntity) {
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