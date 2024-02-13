package team.lodestar.lodestone.client.events.types;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import java.util.List;
import java.util.function.Consumer;

public final class ScreenEvents {
    public static final Event<PostInit> POST_INIT = EventFactory.createArrayBacked(PostInit.class, (listeners) -> (screen, children, addElement, removeElement) -> {
        for (PostInit listener : listeners) {
            listener.onPostInit(screen, children, addElement, removeElement);
        }
    });

    public interface PostInit {
        void onPostInit(Screen screen, List<Element> children, Consumer<Element> addElement, Consumer<Element> removeElement);
    }
}
