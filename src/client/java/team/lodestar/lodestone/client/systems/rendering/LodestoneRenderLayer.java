package team.lodestar.lodestone.client.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import team.lodestar.lodestone.client.access.Accessor;
import team.lodestar.lodestone.client.mixin.accessor.BufferRendererAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseParametersAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayerAccessor;
import team.lodestar.lodestone.client.mixin.accessor.WorldRendererAccessor;
import java.util.Optional;

public class LodestoneRenderLayer extends RenderLayer {
    public final RenderLayer.MultiPhaseParameters multiPhaseParameters;
    private final boolean outline;
    private RenderLayer affectedOutline = null;

    public static LodestoneRenderLayer create(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, MultiPhaseParameters phases) {
        return new LodestoneRenderLayer(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
    }

    public LodestoneRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters multiPhaseParameters) {
        super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, () -> {
            Accessor.of(multiPhaseParameters).getPhases().forEach(RenderPhase::startDrawing);
        }, () -> {
            Accessor.of(multiPhaseParameters).getPhases().forEach(RenderPhase::endDrawing);
        });
        this.multiPhaseParameters = multiPhaseParameters;
        RenderLayer$MultiPhaseParametersAccessor parametersAccessor = Accessor.of(multiPhaseParameters);
        if(parametersAccessor.getOutlineMode() == OutlineMode.AFFECTS_OUTLINE) {
            Accessor.of(parametersAccessor.getTexture())
                    .callGetId()
                    .map(id -> RenderLayer$MultiPhaseAccessor.getCullingLayers().apply(id, parametersAccessor.getCull()))
                    .ifPresent(layer -> this.affectedOutline = layer);
        }
        this.outline = parametersAccessor.getOutlineMode() == OutlineMode.IS_OUTLINE;
    }

    @Override
    public void draw(BufferBuilder buffer, VertexSorter sorter) {
        if (!buffer.isBuilding()) return;

        if(((RenderLayerAccessor) this).isTranslucent()) {
            buffer.setSorter(sorter);
        }

        BufferBuilder.BuiltBuffer builtBuffer = buffer.end();
        if (builtBuffer.isEmpty()) {
            builtBuffer.release();
            return;
        }

        this.startDrawing();
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(false);
        BufferRenderer.drawWithGlobalProgram(builtBuffer);

        if(((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getTransparencyPostProcessor() != null) {
            // Set up depth state
            RenderSystem.colorMask(false, false, false, false);
            RenderSystem.depthMask(true);

            // Redraw buffer
            var shader = RenderSystem.getShader();
            if(shader != null) {
                shader.bind();
                BufferRendererAccessor.getCurrentVertexBuffer().draw();
                shader.unbind();
            }
        }
        this.endDrawing();
        RenderSystem.colorMask(true, true, true, true);
    }

    public Optional<RenderLayer> getAffectedOutline() {
        return Optional.ofNullable(this.affectedOutline);
    }

    public boolean isOutline() {
        return this.outline;
    }

    protected MultiPhaseParameters getMultiPhaseParameters() {
        return multiPhaseParameters;
    }

    @Override
    public String toString() {
        return "LodestoneRenderLayer[" + this.name + ":" + this.multiPhaseParameters + "]";
    }
}
