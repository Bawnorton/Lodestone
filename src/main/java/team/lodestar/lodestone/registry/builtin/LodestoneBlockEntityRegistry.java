package team.lodestar.lodestone.registry.builtin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class LodestoneBlockEntityRegistry {
/* TODO: Add block entity types
    public static final BlockEntityType<MultiblockComponentBlockEntity> MULTIBLOCK_COMPONENT = register("multiblock_component", MultiblockComponentBlockEntity::new, LodestoneBlocks.MULTIBLOCK_COMPONENTS);
    public static final BlockEntityType<LodestoneSignBlockEntity> SIGN = register("sign", LodestoneSignBlockEntity::new, LodestoneBlocks.SIGNS);
*/
    public static void bootstrap() {
        // no-op
    }

    private static BlockEntityType<? extends BlockEntity> register(String name, BlockEntityType.BlockEntityFactory<? extends BlockEntity> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, name, BlockEntityType.Builder.create(factory, blocks).build(null));
    }

    private LodestoneBlockEntityRegistry() {
    }
}
