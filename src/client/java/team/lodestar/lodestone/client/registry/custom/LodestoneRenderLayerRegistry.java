package team.lodestar.lodestone.client.registry.custom;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.client.handlers.RenderHandler;
import team.lodestar.lodestone.client.systems.rendering.LodestoneRenderLayer;
import team.lodestar.lodestone.client.systems.rendering.Phases;
import team.lodestar.lodestone.client.systems.rendering.renderlayer.RenderLayerData;
import team.lodestar.lodestone.client.systems.rendering.renderlayer.RenderLayerProvider;
import team.lodestar.lodestone.client.systems.rendering.renderlayer.ShaderUniformHandler;
import team.lodestar.lodestone.client.systems.rendering.shader.ShaderHolder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class LodestoneRenderLayerRegistry extends RenderPhase {
    public static final Runnable TRANSPARENT_FUNCTION = () -> RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
    );

    public static final Runnable ADDITIVE_FUNCTION = () -> RenderSystem.blendFunc(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE
    );

    private LodestoneRenderLayerRegistry(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    public static final Map<Pair<Object, LodestoneRenderLayer>, LodestoneRenderLayer> COPIES = new HashMap<>();

    public static final Function<RenderLayerData, LodestoneRenderLayer> GENERIC = (data) -> createGenericRenderLayer(data.name, data.vertexFormat, data.drawMode, data.shaderProgram, data.transparency, data.texture, data.cull);

    private static Consumer<LodestoneMultiPhaseParametersBuilder> MODIFIER;

    public static final LodestoneRenderLayer ADDITIVE_PARTICLE = createGenericRenderLayer("lodestone:additive_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.PARTICLE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public static final LodestoneRenderLayer ADDITIVE_BLOCK_PARTICLE = createGenericRenderLayer("lodestone:additive_block_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.PARTICLE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public static final LodestoneRenderLayer ADDITIVE_BLOCK = createGenericRenderLayer("lodestone:additive_block", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
    );

    public static final LodestoneRenderLayer ADDITIVE_SOLID = createGenericRenderLayer("lodestone:additive_block", VertexFormats.POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(RenderPhase.NO_TEXTURE)
    );

    public static final LodestoneRenderLayer TRANSPARENT_PARTICLE = createGenericRenderLayer("lodestone:transparent_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.PARTICLE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public static final LodestoneRenderLayer TRANSPARENT_BLOCK_PARTICLE = createGenericRenderLayer("lodestone:transparent_block_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public static final LodestoneRenderLayer TRANSPARENT_BLOCK = createGenericRenderLayer("lodestone:transparent_block", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
    );
    
    public static final LodestoneRenderLayer TRANSPARENT_SOLID = createGenericRenderLayer("lodestone:transparent_block", VertexFormats.POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(RenderPhase.NO_TEXTURE)
    );

    public static LodestoneRenderLayer createGenericRenderLayer(String name, VertexFormat format, VertexFormat.DrawMode drawMode, ShaderProgram shader, Transparency transparency, TextureBase texture, Cull cull) {
        return createGenericRenderLayer(name, format, drawMode, builder()
                .shader(shader)
                .transparency(transparency)
                .texture(texture)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .cull(cull)
        );
    }
    
    public static final LodestoneRenderLayer LUMITRANSPARENT_PARTICLE = copyWithUniformChanges("lodestone:lumitransparent_particle", TRANSPARENT_PARTICLE, ShaderUniformHandler.LUMITRANSPARENT);
    public static final LodestoneRenderLayer LUMITRANSPARENT_BLOCK_PARTICLE = copyWithUniformChanges("lodestone:lumitransparent_block_particle", TRANSPARENT_BLOCK_PARTICLE, ShaderUniformHandler.LUMITRANSPARENT);
    public static final LodestoneRenderLayer LUMITRANSPARENT_BLOCK = copyWithUniformChanges("lodestone:lumitransparent_block", TRANSPARENT_BLOCK, ShaderUniformHandler.LUMITRANSPARENT);
    public static final LodestoneRenderLayer LUMITRANSPARENT_SOLID = copyWithUniformChanges("lodestone:lumitransparent_solid", TRANSPARENT_SOLID, ShaderUniformHandler.LUMITRANSPARENT);

    public static final RenderLayerProvider TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.NO_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider TRANSPARENT_TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider TRANSPARENT_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.TRIANGLE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider TRANSPARENT_SCROLLING_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_scrolling_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SCROLLING_TRIANGLE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider ADDITIVE_TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SILKSTONE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.TRIANGLE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static final RenderLayerProvider ADDITIVE_SCROLLING_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_scrolling_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(LodestoneShaderRegistry.SCROLLING_TRIANGLE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static void bootstrap() {
        // no-op
    }

    public static LodestoneRenderLayer createGenericRenderLayer(String name, VertexFormat format, VertexFormat.DrawMode drawMode, LodestoneMultiPhaseParametersBuilder builder) {
        return createGenericRenderLayer(name, format, drawMode, builder, null);
    }

    public static LodestoneRenderLayer createGenericRenderLayer(String name, VertexFormat format, VertexFormat.DrawMode drawMode, LodestoneMultiPhaseParametersBuilder builder, ShaderUniformHandler handler) {
        int size = RenderHandler.LARGE_BUFFER_SOURCES ? 262144 : 256;
        if(MODIFIER != null) MODIFIER.accept(builder);
        VertexFormat.DrawMode mode = builder.mode != null ? builder.mode : drawMode;
        LodestoneRenderLayer layer = LodestoneRenderLayer.create(name, format, mode, size, false, true, builder.build(true));
        RenderHandler.addRenderLayer(layer);
        if(handler != null) applyUniformChanges(layer, handler);
        MODIFIER = null;
        return layer;
    }

    public static LodestoneRenderLayer copyWithUniformChanges(String newName, LodestoneRenderLayer type, ShaderUniformHandler handler) {
        return applyUniformChanges(copy(newName, type), handler);
    }

    public static LodestoneRenderLayer applyUniformChanges(LodestoneRenderLayer type, ShaderUniformHandler handler) {
        RenderHandler.UNIFORM_HANDLERS.put(type, handler);
        return type;
    }

    public static LodestoneRenderLayer copy(LodestoneRenderLayer type) {
        return GENERIC.apply(new RenderLayerData(type));
    }

    public static LodestoneRenderLayer copy(String newName, LodestoneRenderLayer type) {
        return GENERIC.apply(new RenderLayerData(newName, type));
    }

    public static LodestoneRenderLayer copyAndStore(Object index, LodestoneRenderLayer layer) {
        return COPIES.computeIfAbsent(Pair.of(index, layer), (pair) -> copy(layer));
    }

    public static void setModifier(Consumer<LodestoneMultiPhaseParametersBuilder> modifier) {
        MODIFIER = modifier;
    }

    public static LodestoneMultiPhaseParametersBuilder builder() {
        return new LodestoneMultiPhaseParametersBuilder();
    }

    public static class LodestoneMultiPhaseParametersBuilder extends LodestoneRenderLayer.MultiPhaseParameters.Builder {
        protected VertexFormat.DrawMode mode;

        private LodestoneMultiPhaseParametersBuilder() {
            super();
        }

        public LodestoneMultiPhaseParametersBuilder replaceDrawMode(VertexFormat.DrawMode mode) {
            this.mode = mode;
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder texture(Identifier texture) {
            return texture(new Texture(texture, false, false));
        }

        public LodestoneMultiPhaseParametersBuilder texture(Texture texture) {
            super.texture(texture);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder texture(TextureBase texture) {
            super.texture(texture);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder shader(ShaderProgram shader) {
            super.program(shader);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder shader(ShaderHolder holder) {
            super.program(holder.getProgram());
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder transparency(Transparency transparency) {
            super.transparency(transparency);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder depthTest(DepthTest depthTest) {
            super.depthTest(depthTest);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder cull(Cull cull) {
            super.cull(cull);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder lightmap(Lightmap lightmap) {
            super.lightmap(lightmap);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder overlay(Overlay overlay) {
            super.overlay(overlay);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder layering(Layering layering) {
            super.layering(layering);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder target(Target target) {
            super.target(target);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder writeMaskState(WriteMaskState writeMask) {
            super.writeMaskState(writeMask);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder lineWidth(LineWidth lineWidth) {
            super.lineWidth(lineWidth);
            return this;
        }

        public LodestoneMultiPhaseParametersBuilder colorLogic(ColorLogic colorLogic) {
            super.colorLogic(colorLogic);
            return this;
        }
    }
}
