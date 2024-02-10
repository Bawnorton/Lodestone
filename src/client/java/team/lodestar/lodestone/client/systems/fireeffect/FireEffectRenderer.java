package team.lodestar.lodestone.client.systems.fireeffect;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import team.lodestar.lodestone.helpers.VecHelper;
import team.lodestar.lodestone.systems.fireeffect.FireEffectInstance;


public abstract class FireEffectRenderer<T extends FireEffectInstance> {
    public boolean canRender(T instance) {
        return true;
    }

    public SpriteIdentifier getFirstFlame() {
        return null;
    }

    public SpriteIdentifier getSecondFlame() {
        return null;
    }

    public void renderScreen(T instance, MinecraftClient client, MatrixStack matrixStack) {
        matrixStack.push();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Sprite sprite = getFirstFlame().getSprite();
        RenderSystem.setShaderTexture(0, sprite.getAtlasId());
        float f = sprite.getMinU();
        float f1 = sprite.getMaxU();
        float f2 = (f + f1) / 2.0F;
        float f3 = sprite.getMinV();
        float f4 = sprite.getMaxV();
        float f5 = (f3 + f4) / 2.0F;
        float f6 = sprite.getAnimationFrameDelta();
        float f7 = MathHelper.lerp(f6, f, f2);
        float f8 = MathHelper.lerp(f6, f1, f2);
        float f9 = MathHelper.lerp(f6, f3, f5);
        float f10 = MathHelper.lerp(f6, f4, f5);

        for (int i = 0; i < 2; ++i) {
            matrixStack.push();
            matrixStack.translate(((float) (-(i * 2 - 1)) * 0.24F), -0.3F, 0.0D);
//            matrixStack.translate(0, -(ClientConfig.FIRE_OVERLAY_OFFSET.getConfigValue()) * 0.3f, 0);
            matrixStack.multiply(VecHelper.Vector3fHelper.rotation((float) (i * 2 - 1) * 10.0F, VecHelper.Vector3fHelper.YP));
            Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
            bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
            bufferbuilder.vertex(matrix4f, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(f8, f10).next();
            bufferbuilder.vertex(matrix4f, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(f7, f10).next();
            bufferbuilder.vertex(matrix4f, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(f7, f9).next();
            bufferbuilder.vertex(matrix4f, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(f8, f9).next();
            BufferBuilder.BuiltBuffer renderedBuffer = bufferbuilder.end();
            BufferRenderer.drawWithGlobalProgram(renderedBuffer);
            matrixStack.pop();
        }

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
        matrixStack.pop();
    }

    public void renderWorld(T instance, MatrixStack pMatrixStack, VertexConsumerProvider provider, Camera camera, Entity entity) {
        Sprite sprite0 = getFirstFlame().getSprite();
        Sprite sprite1 = getSecondFlame().getSprite();
        pMatrixStack.push();
        float f = entity.getWidth() * 1.4F;
        pMatrixStack.scale(f, f, f);
        float f1 = 0.5F;
        float f3 = entity.getHeight() / f;
        float f4 = 0.0F;
        pMatrixStack.multiply(VecHelper.Vector3fHelper.rotation(-camera.getYaw(), VecHelper.Vector3fHelper.YP));
        pMatrixStack.translate(0.0D, 0.0D, -0.3F + (float) ((int) f3) * 0.02F);
        float f5 = 0.0F;
        int i = 0;
        VertexConsumer vertexconsumer = provider.getBuffer(TexturedRenderLayers.getEntityCutout());

        for (MatrixStack.Entry entry = pMatrixStack.peek(); f3 > 0.0F; ++i) {
            Sprite finalSprite = i % 2 == 0 ? sprite0 : sprite1;
            float f6 = finalSprite.getMinU();
            float f7 = finalSprite.getMinV();
            float f8 = finalSprite.getMaxU();
            float f9 = finalSprite.getMaxV();
            if (i / 2 % 2 == 0) {
                float f10 = f8;
                f8 = f6;
                f6 = f10;
            }

            fireVertex(entry, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f8, f9);
            fireVertex(entry, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f6, f9);
            fireVertex(entry, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f6, f7);
            fireVertex(entry, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f8, f7);
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
        }

        pMatrixStack.pop();
    }

    protected static void fireVertex(MatrixStack.Entry entry, VertexConsumer consumer, float x, float y, float z, float u, float v) {
        consumer.vertex(entry.getPositionMatrix(), x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(0, 10).light(240).normal(entry.getNormalMatrix(), 0.0F, 1.0F, 0.0F).next();
    }
}