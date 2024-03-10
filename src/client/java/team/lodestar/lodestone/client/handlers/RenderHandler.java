package team.lodestar.lodestone.client.handlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.WorldRenderer;
import team.lodestar.lodestone.Lodestone;
import team.lodestar.lodestone.client.helpers.RenderHelper;
import team.lodestar.lodestone.client.mixin.accessor.WorldRendererAccessor;
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

    public static Framebuffer LODESTONE_DEPTH_CACHE;
    public static Framebuffer LODESTONE_TRANSLUCENT;
    public static Framebuffer LODESTONE_TRANSLUCENT_PARTICLE;
    public static Framebuffer LODESTONE_ADDITIVE;
    public static Framebuffer LODESTONE_ADDITIVE_PARTICLE;

    public static PostEffectProcessor LODESTONE_POST_EFFECT_PROCESSOR;

    public static void bootstrap() {
        int size = LARGE_BUFFER_SOURCES ? 262144 : 256;

        DELAYED_RENDER = VertexConsumerProvider.immediate(BUFFERS, new BufferBuilder(size));
        DELAYED_PARTICLE_RENDER = VertexConsumerProvider.immediate(PARTICLE_BUFFERS, new BufferBuilder(size));
    }
    
    public static void setupLodestoneFramebuffers() {
        MinecraftClient client = MinecraftClient.getInstance();
        try {
            PostEffectProcessor postEffectProcessor = new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), Lodestone.id("shaders/lodestone_post_effect_processor.json"));
            postEffectProcessor.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            LODESTONE_DEPTH_CACHE = postEffectProcessor.getSecondaryTarget("lodestone_depth_cache");

            LODESTONE_TRANSLUCENT = postEffectProcessor.getSecondaryTarget("lodestone_translucent");
            LODESTONE_TRANSLUCENT_PARTICLE = postEffectProcessor.getSecondaryTarget("lodestone_translucent_particle");
            LODESTONE_ADDITIVE = postEffectProcessor.getSecondaryTarget("lodestone_additive");
            LODESTONE_ADDITIVE_PARTICLE = postEffectProcessor.getSecondaryTarget("lodestone_additive_particle");

            LODESTONE_POST_EFFECT_PROCESSOR = postEffectProcessor;
        } catch (Exception e) {
            throw new RuntimeException("Failed to setup Lodestone frame buffers", e);
        }
    }
    
    public static void closeLodestoneFramebuffers() {
        if (LODESTONE_POST_EFFECT_PROCESSOR != null) {
            LODESTONE_POST_EFFECT_PROCESSOR.close();
            
            LODESTONE_DEPTH_CACHE.delete();
            LODESTONE_TRANSLUCENT.delete();
            LODESTONE_TRANSLUCENT_PARTICLE.delete();
            LODESTONE_ADDITIVE.delete();
            LODESTONE_ADDITIVE_PARTICLE.delete();
            
            LODESTONE_POST_EFFECT_PROCESSOR = null;
            LODESTONE_DEPTH_CACHE = null;
            LODESTONE_TRANSLUCENT = null;
            LODESTONE_TRANSLUCENT_PARTICLE = null;
            LODESTONE_ADDITIVE = null;
            LODESTONE_ADDITIVE_PARTICLE = null;
        }
    }
    
    public static void resize(int width, int height) {
        if (LODESTONE_POST_EFFECT_PROCESSOR != null) {
            LODESTONE_POST_EFFECT_PROCESSOR.setupDimensions(width, height);
            LODESTONE_DEPTH_CACHE.resize(width, height, MinecraftClient.IS_SYSTEM_MAC);
        }
    }
    
    public static void endBatchesEarly() {
        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        endBatches(((WorldRendererAccessor) worldRenderer).getTransparencyPostProcessor() != null);
    }
    
    public static void endBatchesLate() {
        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        if (((WorldRendererAccessor) worldRenderer).getTransparencyPostProcessor() != null) {
            LODESTONE_POST_EFFECT_PROCESSOR.render(MinecraftClient.getInstance().getTickDelta());
        }
    }

    public static void endBatches(boolean isFabulous) {
        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        Matrix4f last = new Matrix4f(RenderSystem.getModelViewMatrix());
        if (isFabulous) {
            LODESTONE_DEPTH_CACHE.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            LODESTONE_TRANSLUCENT_PARTICLE.clear(MinecraftClient.IS_SYSTEM_MAC);
            LODESTONE_TRANSLUCENT_PARTICLE.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            LODESTONE_TRANSLUCENT_PARTICLE.beginWrite(false);
        }
        beginBufferedRendering();
        renderBufferedParticles(true);
        if (RenderHandler.MATRIX4F != null) {
            RenderSystem.getModelViewMatrix().set(MATRIX4F);
        }
        if (isFabulous) {
            LODESTONE_TRANSLUCENT_PARTICLE.endWrite();
            LODESTONE_TRANSLUCENT.clear(MinecraftClient.IS_SYSTEM_MAC);
            LODESTONE_TRANSLUCENT.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            LODESTONE_TRANSLUCENT.beginWrite(false);
        }
        renderBufferedBatches(true);
        if (isFabulous) {
            LODESTONE_TRANSLUCENT.endWrite();
            LODESTONE_ADDITIVE.clear(MinecraftClient.IS_SYSTEM_MAC);
            LODESTONE_ADDITIVE.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            LODESTONE_ADDITIVE.beginWrite(false);
        }
        renderBufferedBatches(false);
        RenderSystem.getModelViewMatrix().set(last);
        if (isFabulous) {
            LODESTONE_ADDITIVE.endWrite();
            LODESTONE_ADDITIVE_PARTICLE.clear(MinecraftClient.IS_SYSTEM_MAC);
            LODESTONE_ADDITIVE_PARTICLE.copyDepthFrom(MinecraftClient.getInstance().getFramebuffer());
            LODESTONE_ADDITIVE_PARTICLE.beginWrite(false);
        }
        renderBufferedParticles(false);

        endBufferedRendering();
        if (isFabulous) {
            LODESTONE_ADDITIVE_PARTICLE.endWrite();
            worldRenderer.getCloudsFramebuffer().beginWrite(false);
        }
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
    
    public static void beginBufferedRendering() {
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
    
    public static void endBufferedRendering() {
        RenderSystem.setShaderFogStart(FOG_START);
        RenderSystem.setShaderFogEnd(FOG_END);
        RenderSystem.setShaderFogShape(FOG_SHAPE);
        RenderSystem.setShaderFogColor(FOG_RED, FOG_GREEN, FOG_BLUE);
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
