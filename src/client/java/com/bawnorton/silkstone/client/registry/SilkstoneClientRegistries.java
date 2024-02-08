package com.bawnorton.silkstone.client.registry;

import com.bawnorton.silkstone.client.registry.custom.SilkstoneClientParticleTypeRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneOptionRegistry;
import com.bawnorton.silkstone.client.registry.custom.SilkstoneRenderLayerRegistry;

public final class SilkstoneClientRegistries {
    public static final SilkstoneOptionRegistry OPTIONS = SilkstoneOptionRegistry.get();
    public static final SilkstoneRenderLayerRegistry RENDER_LAYERS = SilkstoneRenderLayerRegistry.get();

    public static void bootstrap() {
        SilkstoneClientParticleTypeRegistry.bootstrap();
        SilkstoneOptionRegistry.bootstrap();
        SilkstoneRenderLayerRegistry.bootstrap();
    }
}
