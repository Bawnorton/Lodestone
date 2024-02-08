package com.bawnorton.silkstone.client.registry.custom;

import com.bawnorton.silkstone.Silkstone;
import com.bawnorton.silkstone.client.event.RegisterShadersEvent;
import com.bawnorton.silkstone.client.registry.SilkstoneClientRegistries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;
import java.util.List;
import java.util.function.Consumer;

public final class SilkstoneShaderRegistry {
    private static final SilkstoneShaderRegistry INSTANCE = new SilkstoneShaderRegistry();
    
    public ShaderHolder SILKSTONE_TEXTURE = new ShaderHolder(Silkstone.id("silkstone_texure"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "LumiTransparency");
    public ShaderHolder PARTICLE = new ShaderHolder(Silkstone.id("particle/particle"), VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, "LumiTransparency");

    public ShaderHolder SCREEN_PARTICLE = new ShaderHolder(Silkstone.id("screen/screen_particle"), VertexFormats.POSITION_COLOR_TEXTURE);
    public ShaderHolder DISTORTED_TEXTURE = new ShaderHolder(Silkstone.id("screen/distorted_texture"), VertexFormats.POSITION_COLOR_TEXTURE, "Speed", "TimeOffset", "Intensity", "XFrequency", "YFrequency", "UVCoordinates");

    public ShaderHolder SCROLLING_TEXTURE = new ShaderHolder(Silkstone.id("shapes/scrolling_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "Speed", "LumiTransparency");
    public ShaderHolder TRIANGLE_TEXTURE = new ShaderHolder(Silkstone.id("shapes/triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "LumiTransparency");
    public ShaderHolder SCROLLING_TRIANGLE_TEXTURE = new ShaderHolder(Silkstone.id("shapes/scrolling_triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, "Speed", "LumiTransparency");


    public static void bootstrap() {
        RegisterShadersEvent.EVENT.register(((factory, shaders) -> {
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.SILKSTONE_TEXTURE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.PARTICLE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.SCREEN_PARTICLE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.DISTORTED_TEXTURE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.SCROLLING_TEXTURE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.TRIANGLE_TEXTURE.createInstance(factory));
            registerShader(shaders, SilkstoneClientRegistries.SHADER_REGISTRY.SCROLLING_TRIANGLE_TEXTURE.createInstance(factory));
        }));
    }

    public static void registerShader(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaders, ShaderHolder shaderHolder) {
        shaders.add(Pair.of(shaderHolder, (shader) -> {}));
    }
    
    public static SilkstoneShaderRegistry get() {
        return INSTANCE;
    }
    
    private SilkstoneShaderRegistry() {
    }
}
