package com.bawnorton.silkstone.client.registry;

import com.bawnorton.silkstone.client.registry.custom.SilkstoneClientParticleTypeRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneOptionRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneRenderLayerRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneShaderRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneWorldEventRendererRegistry;

public final class SilkstoneClientRegistries {
    public static final SilkstoneOptionRegistry OPTIONS = SilkstoneOptionRegistry.get();
    public static final SilkstoneRenderLayerRegistry RENDER_LAYERS = SilkstoneRenderLayerRegistry.get();
    public static final SilkstoneShaderRegistry SHADER_REGISTRY = SilkstoneShaderRegistry.get();
    public static final SilkstoneWorldEventRendererRegistry WORLD_EVENT_RENDERERS = SilkstoneWorldEventRendererRegistry.get();

    public static void bootstrap() {
        SilkstoneClientParticleTypeRegistry.bootstrap();
        SilkstoneOptionRegistry.bootstrap();
        SilkstoneRenderLayerRegistry.bootstrap();
        SilkstoneShaderRegistry.bootstrap();
        SilkstoneWorldEventRendererRegistry.bootstrap();
    }
}
