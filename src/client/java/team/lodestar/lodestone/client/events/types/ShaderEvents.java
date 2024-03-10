package team.lodestar.lodestone.client.events.types;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.resource.ResourceFactory;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public final class ShaderEvents {
    public static final Event<RegisterEvent> REGISTER = EventFactory.createArrayBacked(RegisterEvent.class, (listeners) -> (factory, shaders) -> {
        for (RegisterEvent listener : listeners) {
            listener.register(factory, shaders);
        }
    });

    @FunctionalInterface
    public interface RegisterEvent {
        void register(ResourceFactory factory, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaders) throws IOException;
    }
}
