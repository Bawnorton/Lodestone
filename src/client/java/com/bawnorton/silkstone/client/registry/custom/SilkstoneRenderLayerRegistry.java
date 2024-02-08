package com.bawnorton.silkstone.client.registry.custom;

import com.bawnorton.silkstone.client.registry.SilkstoneClientRegistries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public final class SilkstoneRenderLayerRegistry extends RenderPhase {
    private static final SilkstoneRenderLayerRegistry INSTANCE = new SilkstoneRenderLayerRegistry("silkstone:render_layer", () -> {}, () -> {});

    private SilkstoneRenderLayerRegistry(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    public final Map<Pair<Object, RenderLayer>, RenderLayer> COPIES = new HashMap<>();

    public final Function<RenderLayerData, RenderLayer> GENERIC = (data) -> createGenericRenderLayer(data.name, data.format, data.drawMode, data.shader, data.transparency, data.texture, data.cull);

    private Consumer<SilkstoneMultiPhaseParametersBuilder> MODIFIER;

    public final RenderLayer ADDITIVE_PARTICLE = createGenericRenderLayer("silkstone:additive_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.PARTICLE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public final RenderLayer ADDITIVE_BLOCK_PARTICLE = createGenericRenderLayer("silkstone:additive_block_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.PARTICLE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public final RenderLayer ADDITIVE_BLOCK = createGenericRenderLayer("silkstone:additive_block", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
    );

    public final RenderLayer ADDITIVE_SOLID = createGenericRenderLayer("silkstone:additive_block", VertexFormats.POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .texture(RenderPhase.NO_TEXTURE)
    );

    public final RenderLayer TRANSPARENT_PARTICLE = createGenericRenderLayer("silkstone:transparent_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.PARTICLE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public final RenderLayer TRANSPARENT_BLOCK_PARTICLE = createGenericRenderLayer("silkstone:transparent_block_particle", VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
            .cull(RenderPhase.DISABLE_CULLING)
    );

    public final RenderLayer TRANSPARENT_BLOCK = createGenericRenderLayer("silkstone:transparent_block", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)
    );
    
    public final RenderLayer TRANSPARENT_SOLID = createGenericRenderLayer("silkstone:transparent_block", VertexFormats.POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .texture(RenderPhase.NO_TEXTURE)
    );

    public RenderLayer createGenericRenderLayer(Identifier id, VertexFormat format, VertexFormat.DrawMode drawMode, ShaderProgram shader, Transparency transparency, Texture texture, Cull cull) {
        return createGenericRenderLayer(id.toString(), format, drawMode, builder()
                .shader(shader)
                .transparency(transparency)
                .texture(texture)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .cull(cull)
        );
    }
    
    public final RenderLayer LUMITRANSPARENT_PARTICLE = copyWithUniformChanges("silkstone:lumitransparent_particle", TRANSPARENT_PARTICLE, ShaderUniformHandler.LUMITRANSPARENT);
    public final RenderLayer LUMITRANSPARENT_BLOCK_PARTICLE = copyWithUniformChanges("silkstone:lumitransparent_block_particle", TRANSPARENT_BLOCK_PARTICLE, ShaderUniformHandler.LUMITRANSPARENT);
    public final RenderLayer LUMITRANSPARENT_BLOCK = copyWithUniformChanges("silkstone:lumitransparent_block", TRANSPARENT_BLOCK, ShaderUniformHandler.LUMITRANSPARENT);
    public final RenderLayer LUMITRANSPARENT_SOLID = copyWithUniformChanges("silkstone:lumitransparent_solid", TRANSPARENT_SOLID, ShaderUniformHandler.LUMITRANSPARENT);

    public final RenderLayerProvider TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.NO_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider TRANSPARENT_TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider TRANSPARENT_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.TRIANGLE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider TRANSPARENT_SCROLLING_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":transparent_scrolling_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SCROLLING_TRIANGLE_TEXTURE)
            .transparency(Phases.NORMAL_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider ADDITIVE_TEXTURE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_texture", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.TRIANGLE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public final RenderLayerProvider ADDITIVE_SCROLLING_TEXTURE_TRIANGLE = new RenderLayerProvider(texture -> createGenericRenderLayer(texture.getNamespace() + ":additive_scrolling_texture_triangle", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, builder()
            .shader(SilkstoneClientRegistries.SHADER_REGISTRY.SCROLLING_TRIANGLE_TEXTURE)
            .transparency(Phases.ADDITIVE_TRANSPARENCY)
            .lightmap(RenderPhase.ENABLE_LIGHTMAP)
            .cull(RenderPhase.ENABLE_CULLING)
            .texture(texture)
    ));

    public static void bootstrap() {
        // no-op
    }

    public RenderLayer createGenericRenderLayer(String name, VertexFormat format, VertexFormat.DrawMode drawMode, SilkstoneMultiPhaseParametersBuilder builder) {
        int size = LARGE_BUFFER_SOURCES ? 262144 : 256;
        if(MODIFIER != null) MODIFIER.accept(builder);
        VertexFormat.DrawMode mode = builder.mode != null ? builder.mode : drawMode;
        RenderLayer layer = RenderLayer.of(name, format, mode, size, false, false, builder.createCompositeState(true));
        RenderHandler.addRenderLayer(layer);
        MODIFIER = null;
        return layer;
    }

    public RenderLayer copyWithUniformChanges(String newName, RenderLayer type, ShaderUniformHandler handler) {
        return applyUniformChanges(copy(newName, type), handler);
    }

    public RenderLayer applyUniformChanges(RenderLayer type, ShaderUniformHandler handler) {
        RenderHandler.UNIFORM_HANDLERS.put(type, handler);
        return type;
    }

    public RenderLayer copy(RenderLayer type) {
        return GENERIC.apply(new RenderLayerData((RenderLayer.MultiPhaseParameters) type));
    }

    public RenderLayer copy(String newName, RenderLayer type) {
        return GENERIC.apply(new RenderLayerData(newName, (RenderLayer.MultiPhaseParameters) type));
    }

    public RenderLayer copyAndStore(Object index, RenderLayer layer) {
        return COPIES.computeIfAbsent(Pair.of(index, layer), (pair) -> copy(layer));
    }

    public void setModifier(Consumer<SilkstoneMultiPhaseParametersBuilder> modifier) {
        MODIFIER = modifier;
    }

    public SilkstoneMultiPhaseParametersBuilder builder() {
        return new SilkstoneMultiPhaseParametersBuilder();
    }

    public static SilkstoneRenderLayerRegistry get() {
        return INSTANCE;
    }

    public static class SilkstoneMultiPhaseParametersBuilder extends RenderLayer.MultiPhaseParameters.Builder {
        protected VertexFormat.DrawMode mode;

        private SilkstoneMultiPhaseParametersBuilder() {
            super();
        }

        public SilkstoneMultiPhaseParametersBuilder replaceDrawMode(VertexFormat.DrawMode mode) {
            this.mode = mode;
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder texture(Identifier texture) {
            return texture(new Texture(texture, false, false));
        }

        public SilkstoneMultiPhaseParametersBuilder texture(Texture texture) {
            super.texture(texture);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder texture(TextureBase texture) {
            super.texture(texture);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder shader(ShaderProgram shader) {
            super.program(shader);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder shader(ShaderHolder holder) {
            super.program(holder.getProgram());
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder transparency(Transparency transparency) {
            super.transparency(transparency);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder depthTest(DepthTest depthTest) {
            super.depthTest(depthTest);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder cull(Cull cull) {
            super.cull(cull);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder lightmap(Lightmap lightmap) {
            super.lightmap(lightmap);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder overlay(Overlay overlay) {
            super.overlay(overlay);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder layering(Layering layering) {
            super.layering(layering);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder target(Target target) {
            super.target(target);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder writeMaskState(WriteMaskState writeMask) {
            super.writeMaskState(writeMask);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder lineWidth(LineWidth lineWidth) {
            super.lineWidth(lineWidth);
            return this;
        }

        public SilkstoneMultiPhaseParametersBuilder colorLogic(ColorLogic colorLogic) {
            super.colorLogic(colorLogic);
            return this;
        }
    }
}
