package team.lodestar.lodestone.client.systems.rendering.shader;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import team.lodestar.lodestone.client.mixin.accessor.ShaderProgramAccessor;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ExtendedShaderProgram extends ShaderProgram {
    protected Map<String, Consumer<GlUniform>> defaultUniformData;

    public ExtendedShaderProgram(ResourceFactory factory, Identifier location, VertexFormat vertexFormat) throws IOException {
        super(factory, location.toString(), vertexFormat);
    }

    public void setUniformDefaults() {
        for(Map.Entry<String, Consumer<GlUniform>> defaultDataEntry : getDefaultUniformData().entrySet()) {
            GlUniform uniform = ((ShaderProgramAccessor) this).getLoadedUniforms().get(defaultDataEntry.getKey());
            defaultDataEntry.getValue().accept(uniform);
        }
    }

    public abstract ShaderHolder getShaderHolder();

    public Map<String, Consumer<GlUniform>> getDefaultUniformData() {
        if (defaultUniformData == null) {
            defaultUniformData = new HashMap<>();
        }
        return defaultUniformData;
    }
}
