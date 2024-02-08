package com.bawnorton.silkstone.registry.custom;

import com.bawnorton.silkstone.Silkstone;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class SilkstoneTags {
    private static final SilkstoneTags INSTANCE = new SilkstoneTags();

    public final TagKey<Item> NUGGETS_COPPER = itemTag("nuggets/copper");
    public final TagKey<Item> INGOTS_COPPER = itemTag("ingots/copper");
    public final TagKey<Item> NUGGETS_LEAD = itemTag("nuggets/lead");
    public final TagKey<Item> INGOTS_LEAD = itemTag("ingots/lead");
    public final TagKey<Item> NUGGETS_SILVER = itemTag("nuggets/silver");
    public final TagKey<Item> INGOTS_SILVER = itemTag("ingots/silver");
    public final TagKey<Item> NUGGETS_ALUMINUM = itemTag("nuggets/aluminum");
    public final TagKey<Item> INGOTS_ALUMINUM = itemTag("ingots/aluminum");
    public final TagKey<Item> NUGGETS_NICKEL = itemTag("nuggets/nickel");
    public final TagKey<Item> INGOTS_NICKEL = itemTag("ingots/nickel");
    public final TagKey<Item> NUGGETS_URANIUM = itemTag("nuggets/uranium");
    public final TagKey<Item> INGOTS_URANIUM = itemTag("ingots/uranium");
    public final TagKey<Item> NUGGETS_OSMIUM = itemTag("nuggets/osmium");
    public final TagKey<Item> INGOTS_OSMIUM = itemTag("ingots/osmium");
    public final TagKey<Item> NUGGETS_ZINC = itemTag("nuggets/zinc");
    public final TagKey<Item> INGOTS_ZINC = itemTag("ingots/zinc");
    public final TagKey<Item> NUGGETS_TIN = itemTag("nuggets/tin");
    public final TagKey<Item> INGOTS_TIN = itemTag("ingots/tin");

    public final TagKey<DamageType> IS_MAGIC = damageTypeTag("is_magic");

    public static void bootstrap() {
        // no-op
    }

    public TagKey<Block> blockTag(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Silkstone.id(id));
    }

    public TagKey<Item> itemTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, Silkstone.id(id));
    }

    public TagKey<DamageType> damageTypeTag(String id) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, Silkstone.id(id));
    }

    public static SilkstoneTags get() {
        return INSTANCE;
    }

    private SilkstoneTags() {
    }
}
