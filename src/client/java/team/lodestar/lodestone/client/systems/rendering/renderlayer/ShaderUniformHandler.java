package team.lodestar.lodestone.client.systems.rendering.renderlayer;

import net.minecraft.client.gl.ShaderProgram;

public interface ShaderUniformHandler {
    ShaderUniformHandler LUMITRANSPARENT = program -> program.getUniformOrDefault("LumiTransparency").set(1f);

    void updateShaderData(ShaderProgram program);
}
