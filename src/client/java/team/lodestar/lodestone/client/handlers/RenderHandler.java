package team.lodestar.lodestone.client.handlers;

import team.lodestar.lodestone.client.events.types.FogEvents;
import team.lodestar.lodestone.client.helpers.RenderHelper;
import team.lodestar.lodestone.client.systems.rendering.Phases;
import team.lodestar.lodestone.client.systems.rendering.renderlayer.ShaderUniformHandler;
import team.lodestar.lodestone.client.systems.rendering.shader.ExtendedShaderProgram;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RenderHandler {
    public static final Map<RenderLayer, BufferBuilder> BUFFERS = new HashMap<>();
    public static final Map<RenderLayer, BufferBuilder> PARTICLE_BUFFERS = new HashMap<>();
    public static final Map<RenderLayer, ShaderUniformHandler> UNIFORM_HANDLERS = new HashMap<>();
    public static final Collection<RenderLayer> TRANSPARENT_RENDER_LAYERS = new ArrayList<>();

    public static boolean LARGE_BUFFER_SOURCES = FabricLoader.getInstance().isModLoaded("sodium");

    public static VertexConsumerProvider.Immediate DELAYED_RENDER;
    public static VertexConsumerProvider.Immediate DELAYED_PARTICLE_RENDER;

    public static MatrixStack MAIN_MATRIX_STACK;
    public static Matrix4f MATRIX4F;

    public static float FOG_START;
    public static float FOG_END;
    public static FogShape FOG_SHAPE;
    public static float FOG_RED, FOG_GREEN, FOG_BLUE;

    public static void init() {
        int size = LARGE_BUFFER_SOURCES ? 262144 : 256;

        DELAYED_RENDER = VertexConsumerProvider.immediate(BUFFERS, new BufferBuilder(size));
        DELAYED_PARTICLE_RENDER = VertexConsumerProvider.immediate(PARTICLE_BUFFERS, new BufferBuilder(size));
    }

    public static void cacheFogData(float fogStart, float fogEnd, FogShape shape) {
        FOG_START = fogStart;
        FOG_END = fogEnd;
        FOG_SHAPE = shape;
    }

    public static void cacheFogData(float red, float green, float blue) {
        FOG_RED = red;
        FOG_GREEN = green;
        FOG_BLUE = blue;
    }
    
    public static void beginBufferedRendering(MatrixStack matrixStack) {
        matrixStack.push();
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);

        float[] shaderFogColor = RenderSystem.getShaderFogColor();
        float fogRed = shaderFogColor[0];
        float fogGreen = shaderFogColor[1];
        float fogBlue = shaderFogColor[2];
        float shaderFogStart = RenderSystem.getShaderFogStart();
        float shaderFogEnd = RenderSystem.getShaderFogEnd();
        FogShape shaderFogShape = RenderSystem.getShaderFogShape();

        RenderSystem.setShaderFogStart(FOG_START);
        RenderSystem.setShaderFogEnd(FOG_END);
        RenderSystem.setShaderFogShape(FOG_SHAPE);
        RenderSystem.setShaderFogColor(FOG_RED, FOG_GREEN, FOG_BLUE);

        FOG_RED = fogRed;
        FOG_GREEN = fogGreen;
        FOG_BLUE = fogBlue;

        FOG_START = shaderFogStart;
        FOG_END = shaderFogEnd;
        FOG_SHAPE = shaderFogShape;
    }
    
    public static void endBufferedRendering(MatrixStack matrixStack) {
        RenderSystem.setShaderFogStart(FOG_START);
        RenderSystem.setShaderFogEnd(FOG_END);
        RenderSystem.setShaderFogShape(FOG_SHAPE);
        RenderSystem.setShaderFogColor(FOG_RED, FOG_GREEN, FOG_BLUE);

        matrixStack.pop();
    }

    public static void renderBufferedParticles(boolean transparentOnly) {
        renderBufferedBatches(DELAYED_PARTICLE_RENDER, PARTICLE_BUFFERS, transparentOnly);
    }

    public static void renderBufferedBatches(boolean transparentOnly) {
        renderBufferedBatches(DELAYED_RENDER, BUFFERS, transparentOnly);
    }

    private static void renderBufferedBatches(VertexConsumerProvider.Immediate immediate, Map<RenderLayer, BufferBuilder> buffer, boolean transparentOnly) {
        if (transparentOnly) {
            endBatches(immediate, TRANSPARENT_RENDER_LAYERS);
        } else {
            Collection<RenderLayer> nonTransparentRenderLayers = new ArrayList<>(buffer.keySet());
            nonTransparentRenderLayers.removeIf(TRANSPARENT_RENDER_LAYERS::contains);
            endBatches(immediate, nonTransparentRenderLayers);
        }
    }

    public static void endBatches(VertexConsumerProvider.Immediate immediate, Collection<RenderLayer> renderLayers) {
        for (RenderLayer type : renderLayers) {
            ShaderProgram program = RenderHelper.getShader(type);
            if (UNIFORM_HANDLERS.containsKey(type)) {
                ShaderUniformHandler handler = UNIFORM_HANDLERS.get(type);
                handler.updateShaderData(program);
            }
            immediate.draw(type);
            if (program instanceof ExtendedShaderProgram extendedShaderProgram) {
                extendedShaderProgram.setUniformDefaults();
            }
        }
    }

    public static void addRenderLayer(RenderLayer renderLayer) {
        int size = LARGE_BUFFER_SOURCES ? 262144 : renderLayer.getExpectedBufferSize();
        final boolean isParticle = renderLayer.toString().contains("particle");
        Map<RenderLayer, BufferBuilder> buffers = isParticle ? PARTICLE_BUFFERS : BUFFERS;
        buffers.put(renderLayer, new BufferBuilder(size));
        if (Phases.NORMAL_TRANSPARENCY.equals(RenderHelper.getTransparency(renderLayer))) {
            TRANSPARENT_RENDER_LAYERS.add(renderLayer);
        }
    }
}
