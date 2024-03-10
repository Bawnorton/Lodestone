package team.lodestar.lodestone.handlers;

import net.minecraft.registry.Registries;
import team.lodestar.lodestone.dummyclient.RenderLayersReference;
import team.lodestar.lodestone.systems.block.LodestoneBlockSettings;
import team.lodestar.lodestone.systems.block.LodestoneThrowawayBlockData;
import team.lodestar.lodestone.mixin.accessor.AbstractBlockAccessor;
import team.lodestar.lodestone.systems.datagen.LodestoneDatagenBlockData;
import java.util.HashMap;
import java.util.Map;

public class ThrowawayBlockDataHandler {
    public static Map<LodestoneBlockSettings, LodestoneThrowawayBlockData> THROWAWAY_DATA_CACHE = new HashMap<>();
    public static Map<LodestoneBlockSettings, LodestoneDatagenBlockData> DATAGEN_DATA_CACHE = new HashMap<>();

    public static void wipeCache() {
        THROWAWAY_DATA_CACHE = null;
        DATAGEN_DATA_CACHE = null;
    }

    public static void bootstrap() {
        Registries.BLOCK.stream()
                .filter(block -> ((AbstractBlockAccessor) block).getSettings() instanceof LodestoneBlockSettings blockSettings && blockSettings.getThrowawayData().hasCustomRenderLayer())
                .forEach(block -> {
                    LodestoneBlockSettings blockSettings = (LodestoneBlockSettings) ((AbstractBlockAccessor) block).getSettings();
                    RenderLayersReference.modifyBlocks(map -> {
                        map.put(block, blockSettings.getThrowawayData().getRenderLayer().get().get());
                        return map;
                    });
                });
    }
}
