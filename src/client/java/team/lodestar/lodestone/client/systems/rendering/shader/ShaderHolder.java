package team.lodestar.lodestone.client.systems.rendering.shader;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class ShaderHolder {
    public final Identifier shaderLocation;
    public final VertexFormat shaderFormat;

    protected ExtendedShaderProgram shaderProgram;
    public Collection<String> uniformsToCache;
    public final RenderPhase.ShaderProgram program = new RenderPhase.ShaderProgram(getInstance());

    public ShaderHolder(Identifier shaderLocation, VertexFormat shaderFormat, String... uniformsToCache) {
        this.shaderLocation = shaderLocation;
        this.shaderFormat = shaderFormat;
        this.uniformsToCache = new ArrayList<>();
        Collections.addAll(this.uniformsToCache, uniformsToCache);
    }

    public ExtendedShaderProgram createProgram(ResourceFactory factory) throws IOException {
        ExtendedShaderProgram shaderProgram = new ExtendedShaderProgram(factory, shaderLocation, shaderFormat) {
            @Override
            public ShaderHolder getShaderHolder() {
                return ShaderHolder.this;
            }
        };
        this.shaderProgram = shaderProgram;
        return shaderProgram;
    }

    public Supplier<ShaderProgram> getInstance() {
        return () -> shaderProgram;
    }

    public RenderPhase.ShaderProgram getProgram() {
        return program;
    }
}
