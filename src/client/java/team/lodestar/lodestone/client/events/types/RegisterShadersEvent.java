package team.lodestar.lodestone.client.events.types;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.resource.ResourceFactory;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public interface RegisterShadersEvent {
    Event<RegisterShadersEvent> EVENT = EventFactory.createArrayBacked(RegisterShadersEvent.class, (listeners) -> (factory, shaders) -> {
        for (RegisterShadersEvent listener : listeners) {
            listener.registerShaders(factory, shaders);
        }
    });

    void registerShaders(ResourceFactory factory, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaders) throws IOException;
}
