package team.lodestar.lodestone.dummyclient;

import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import java.lang.invoke.VarHandle;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class RenderLayersReference {
    private static final String INTERMEDIARY_NAME = "net.minecraft.class_4696";

    @Nullable
    private static VarHandle BLOCKS;

    static {
        ClientClassReference.getClass(INTERMEDIARY_NAME).ifPresent(referenceClass -> {
            ClientClassReference.getStaticField(referenceClass, "field_21469", "Ljava/util/Map;", Map.class).ifPresent(field -> BLOCKS = field);
        });
    }

    @SuppressWarnings("unchecked")
    public static Optional<Map<Block, RenderLayerReference>> getBlocks() {
        return Optional.ofNullable(BLOCKS).map(handle -> {
            Map<Block, Object> map = (Map<Block, Object>) handle.get();
            return map.entrySet()
                    .stream()
                    .collect(
                            () -> new HashMap<>(map.size()),
                            (m, e) -> m.put(e.getKey(), new RenderLayerReference(e.getValue())),
                            Map::putAll
                    );
        });
    }

    public static void setBlocks(Map<Block, RenderLayerReference> blocks) {
        if (BLOCKS == null) return;

        Map<Block, Object> map = new HashMap<>(blocks.size());
        blocks.forEach((k, v) -> map.put(k, v.getActual()));
        BLOCKS.set(map);
    }

    public static void modifyBlocks(UnaryOperator<Map<Block, RenderLayerReference>> function) {
        if (BLOCKS == null) return;

        setBlocks(function.apply(getBlocks().orElse(new HashMap<>())));
    }
}
