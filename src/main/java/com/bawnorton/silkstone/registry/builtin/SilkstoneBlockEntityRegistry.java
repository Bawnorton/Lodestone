package com.bawnorton.silkstone.registry.builtin;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;

public final class SilkstoneBlockEntityRegistry implements SilkstoneBuiltinRegistry<BlockEntityType<? extends BlockEntity>> {
    private static final SilkstoneBlockEntityRegistry INSTANCE = new SilkstoneBlockEntityRegistry();

/* TODO: Add block entity types
    public final BlockEntityType<MultiblockComponentBlockEntity> MULTIBLOCK_COMPONENT = register("multiblock_component", MultiblockComponentBlockEntity::new, SilkstoneBlocks.MULTIBLOCK_COMPONENTS);
    public final BlockEntityType<SilkstoneSignBlockEntity> SIGN = register("sign", SilkstoneSignBlockEntity::new, SilkstoneBlocks.SIGNS);
*/
    public static void bootstrap() {
        // no-op
    }

    public static SilkstoneBlockEntityRegistry get() {
        return INSTANCE;
    }

    private BlockEntityType<? extends BlockEntity> register(String name, BlockEntityType.BlockEntityFactory<? extends BlockEntity> factory, Block... blocks) {
        return register(Registries.BLOCK_ENTITY_TYPE, name, BlockEntityType.Builder.create(factory, blocks).build(null));
    }

    private SilkstoneBlockEntityRegistry() {
    }
}
