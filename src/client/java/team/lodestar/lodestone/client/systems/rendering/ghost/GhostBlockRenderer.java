package team.lodestar.lodestone.client.systems.rendering.ghost;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import team.lodestar.lodestone.client.extender.VertexConsumerExtender;
import team.lodestar.lodestone.client.handlers.RenderHandler;
import team.lodestar.lodestone.handlers.PlacementAssistantHandler;
import java.util.List;

public abstract class GhostBlockRenderer {

    public static final GhostBlockRenderer STANDARD = new DefaultGhostBlockRenderer();
    public static final GhostBlockRenderer TRANSPARENT = new TransparentGhostBlockRenderer();

    public static GhostBlockRenderer standard() {
        return STANDARD;
    }

    public static GhostBlockRenderer transparent() {
        return TRANSPARENT;
    }

    public abstract void render(MatrixStack matrixStack, GhostBlockOptions params);

    private static class DefaultGhostBlockRenderer extends GhostBlockRenderer {
        @Override
        public void render(MatrixStack matrixStack, GhostBlockOptions options) {
            matrixStack.push();
            BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();
            BakedModel bakedModel = manager.getModel(options.blockState);
            RenderLayer renderLayer = RenderLayers.getEntityBlockLayer(options.blockState, false);
            VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(renderLayer);
            BlockPos pos = options.blockPos;

            matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());
            manager.getModelRenderer().render(
                    matrixStack.peek(),
                    consumer,
                    options.blockState,
                    bakedModel,
                    1.0F,
                    1.0F,
                    1.0F,
                    LightmapTextureManager.MAX_LIGHT_COORDINATE,
                    OverlayTexture.DEFAULT_UV
            );
            matrixStack.pop();
        }
    }

    private static class TransparentGhostBlockRenderer extends GhostBlockRenderer {
        @Override
        public void render(MatrixStack matrixStack, GhostBlockOptions options) {
            matrixStack.push();
            MinecraftClient minecraft = MinecraftClient.getInstance();
            BlockRenderManager manager = minecraft.getBlockRenderManager();
            BakedModel bakedModel = manager.getModel(options.blockState);
            RenderLayer renderLayer = RenderLayer.getTranslucent();
            VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(renderLayer);
            BlockPos pos = options.blockPos;

            matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());

            matrixStack.translate(0.5D, 0.5D, 0.5D);
            matrixStack.scale(0.85F, 0.85F, 0.85F);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);

            float alpha = options.alphaSupplier.get() * 0.75F * PlacementAssistantHandler.getCurrentAlpha();
            renderModel(
                    matrixStack.peek(),
                    consumer,
                    options.blockState,
                    bakedModel,
                    1.0F,
                    1.0F,
                    1.0F,
                    alpha,
                    WorldRenderer.getLightmapCoordinates(minecraft.world, pos),
                    OverlayTexture.DEFAULT_UV,
                    minecraft.world.getRandom()
            );

            matrixStack.pop();
        }

        public static void renderModel(MatrixStack.Entry entry, VertexConsumer consumer, BlockState state, BakedModel model, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, Random random) {
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                renderQuadList(entry, consumer, red, green, blue, alpha, model.getQuads(state, direction, random), packedLight, packedOverlay);
            }
            random.setSeed(42L);
            renderQuadList(entry, consumer, red, green, blue, alpha, model.getQuads(state, null, random), packedLight, packedOverlay);
        }

        private static void renderQuadList(MatrixStack.Entry entry, VertexConsumer consumer, float red, float green, float blue, float alpha, List<BakedQuad> quads, int packedLight, int packedOverlay) {
            for (BakedQuad quad : quads) {
                float r, g, b;
                if (quad.hasColor()) {
                    r = MathHelper.clamp(red, 0.0F, 1.0F);
                    g = MathHelper.clamp(green, 0.0F, 1.0F);
                    b = MathHelper.clamp(blue, 0.0F, 1.0F);
                } else {
                    r = 1.0F;
                    g = 1.0F;
                    b = 1.0F;
                }
                ((VertexConsumerExtender) consumer).lodestone$passAlpha(alpha);
                consumer.quad(entry, quad, new float[]{1, 1, 1, 1}, r, g, b, new int[]{packedLight, packedLight, packedLight, packedLight}, packedOverlay, true);
            }
        }
    }
}