package team.lodestar.lodestone.systems.datagen;

import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import team.lodestar.lodestone.handlers.ThrowawayBlockDataHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Various throwaway data stored in {@link ThrowawayBlockDataHandler#THROWAWAY_DATA_CACHE}, which is only ever instantiated during the data-generation process.
 */
public class LodestoneDatagenBlockData {

    public static final LodestoneDatagenBlockData EMPTY = new LodestoneDatagenBlockData();

    private final List<TagKey<Block>> tags = new ArrayList<>();
    public boolean hasInheritedLootTable = false;

    public LodestoneDatagenBlockData addTag(TagKey<Block> blockTagKey) {
        tags.add(blockTagKey);
        return this;
    }

    @SafeVarargs
    public final LodestoneDatagenBlockData addTags(TagKey<Block>... blockTagKeys) {
        tags.addAll(Arrays.asList(blockTagKeys));
        return this;
    }

    public List<TagKey<Block>> getTags() {
        return tags;
    }

    public LodestoneDatagenBlockData hasInheritedLoot() {
        hasInheritedLootTable = true;
        return this;
    }

    public LodestoneDatagenBlockData needsPickaxe() {
        return addTag(BlockTags.PICKAXE_MINEABLE);
    }

    public LodestoneDatagenBlockData needsAxe() {
        return addTag(BlockTags.AXE_MINEABLE);
    }

    public LodestoneDatagenBlockData needsShovel() {
        return addTag(BlockTags.SHOVEL_MINEABLE);
    }

    public LodestoneDatagenBlockData needsHoe() {
        return addTag(BlockTags.HOE_MINEABLE);
    }

    public LodestoneDatagenBlockData needsStone() {
        return addTag(BlockTags.NEEDS_STONE_TOOL);
    }

    public LodestoneDatagenBlockData needsIron() {
        return addTag(BlockTags.NEEDS_IRON_TOOL);
    }

    public LodestoneDatagenBlockData needsDiamond() {
        return addTag(BlockTags.NEEDS_DIAMOND_TOOL);
    }
}
