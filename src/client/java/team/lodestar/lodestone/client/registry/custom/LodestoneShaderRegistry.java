package team.lodestar.lodestone.client.registry.custom;

import team.lodestar.lodestone.Lodestone;
import team.lodestar.lodestone.client.event.RegisterShadersEvent;
import team.lodestar.lodestone.client.systems.rendering.shader.ExtendedShaderProgram;
import team.lodestar.lodestone.client.systems.rendering.shader.ShaderHolder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import java.util.List;
import java.util.function.Consumer;

public final class LodestoneShaderRegistry {
    public static final ShaderHolder SILKSTONE_TEXTURE = new ShaderHolder(Lodestone.id("lodestone_texure"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "LumiTransparency");
    public static final ShaderHolder PARTICLE = new ShaderHolder(Lodestone.id("particle/particle"), VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, "LumiTransparency");

    public static final ShaderHolder SCREEN_PARTICLE = new ShaderHolder(Lodestone.id("screen/screen_particle"), VertexFormats.POSITION_COLOR_TEXTURE);
    public static final ShaderHolder DISTORTED_TEXTURE = new ShaderHolder(Lodestone.id("screen/distorted_texture"), VertexFormats.POSITION_COLOR_TEXTURE, "Speed", "TimeOffset", "Intensity", "XFrequency", "YFrequency", "UVCoordinates");

    public static final ShaderHolder SCROLLING_TEXTURE = new ShaderHolder(Lodestone.id("shapes/scrolling_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "Speed", "LumiTransparency");
    public static final ShaderHolder TRIANGLE_TEXTURE = new ShaderHolder(Lodestone.id("shapes/triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "LumiTransparency");
    public static final ShaderHolder SCROLLING_TRIANGLE_TEXTURE = new ShaderHolder(Lodestone.id("shapes/scrolling_triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "Speed", "LumiTransparency");


    public static void bootstrap() {
        RegisterShadersEvent.EVENT.register(((factory, shaders) -> {
            registerShader(shaders, SILKSTONE_TEXTURE.createProgram(factory));
            registerShader(shaders, PARTICLE.createProgram(factory));
            registerShader(shaders, SCREEN_PARTICLE.createProgram(factory));
            registerShader(shaders, DISTORTED_TEXTURE.createProgram(factory));
            registerShader(shaders, SCROLLING_TEXTURE.createProgram(factory));
            registerShader(shaders, TRIANGLE_TEXTURE.createProgram(factory));
            registerShader(shaders, SCROLLING_TRIANGLE_TEXTURE.createProgram(factory));
        }));
    }

    public static void registerShader(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaders, ExtendedShaderProgram shaderProgram) {
        shaders.add(Pair.of(shaderProgram, (shader) -> {}));
    }

    private LodestoneShaderRegistry() {
    }
}
