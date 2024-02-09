package team.lodestar.lodestone.client.systems.rendering.renderlayer;

import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseParametersAccessor;
import team.lodestar.lodestone.client.systems.rendering.Phases;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;

public class RenderLayerData {
    public final String name;
    public final VertexFormat vertexFormat;
    public final VertexFormat.DrawMode drawMode;
    public final RenderPhase.ShaderProgram shaderProgram;
    public final RenderPhase.TextureBase texture;
    public final RenderPhase.Cull cull;
    public final RenderPhase.Lightmap lightmap;
    public RenderPhase.Transparency transparency = Phases.ADDITIVE_TRANSPARENCY;

    public RenderLayerData(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, RenderPhase.ShaderProgram shaderProgram, RenderPhase.TextureBase texture, RenderPhase.Cull cull, RenderPhase.Lightmap lightmap) {
        this.name = name;
        this.vertexFormat = vertexFormat;
        this.drawMode = drawMode;
        this.shaderProgram = shaderProgram;
        this.texture = texture;
        this.cull = cull;
        this.lightmap = lightmap;
    }

    public RenderLayerData(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, RenderPhase.ShaderProgram shaderProgram, RenderPhase.Transparency transparency, RenderPhase.TextureBase texture, RenderPhase.Cull cull, RenderPhase.Lightmap lightmap) {
        this(name, vertexFormat, drawMode, shaderProgram, texture, cull, lightmap);
        this.transparency = transparency;
    }

    public RenderLayerData(String name, RenderLayer.MultiPhase multiPhase) {
        RenderLayer$MultiPhaseAccessor accessor = getAccessor(multiPhase);
        RenderLayer.MultiPhaseParameters parameters = accessor.getPhases();
        RenderLayer$MultiPhaseParametersAccessor parametersAccessor = getAccessor(parameters);
        this.name = name;
        this.vertexFormat = multiPhase.getVertexFormat();
        this.drawMode = multiPhase.getDrawMode();
        this.shaderProgram = parametersAccessor.getProgram();
        this.texture = parametersAccessor.getTexture();
        this.cull = parametersAccessor.getCull();
        this.lightmap = parametersAccessor.getLightmap();
    }

    public RenderLayerData(RenderLayer.MultiPhase multiPhase) {
        this(getAccessor(multiPhase).getName(), multiPhase);
    }

    private static RenderLayer$MultiPhaseAccessor getAccessor(RenderLayer.MultiPhase multiPhase) {
        return (RenderLayer$MultiPhaseAccessor) (Object) multiPhase;
    }

    private static RenderLayer$MultiPhaseParametersAccessor getAccessor(RenderLayer.MultiPhaseParameters multiPhaseParameters) {
        return (RenderLayer$MultiPhaseParametersAccessor) (Object) multiPhaseParameters;
    }
}
