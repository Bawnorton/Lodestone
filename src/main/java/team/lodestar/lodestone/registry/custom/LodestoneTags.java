package team.lodestar.lodestone.registry.custom;

import team.lodestar.lodestone.Lodestone;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class LodestoneTags {
    public static final TagKey<Item> NUGGETS_COPPER = itemTag("nuggets/copper");
    public static final TagKey<Item> INGOTS_COPPER = itemTag("ingots/copper");
    public static final TagKey<Item> NUGGETS_LEAD = itemTag("nuggets/lead");
    public static final TagKey<Item> INGOTS_LEAD = itemTag("ingots/lead");
    public static final TagKey<Item> NUGGETS_SILVER = itemTag("nuggets/silver");
    public static final TagKey<Item> INGOTS_SILVER = itemTag("ingots/silver");
    public static final TagKey<Item> NUGGETS_ALUMINUM = itemTag("nuggets/aluminum");
    public static final TagKey<Item> INGOTS_ALUMINUM = itemTag("ingots/aluminum");
    public static final TagKey<Item> NUGGETS_NICKEL = itemTag("nuggets/nickel");
    public static final TagKey<Item> INGOTS_NICKEL = itemTag("ingots/nickel");
    public static final TagKey<Item> NUGGETS_URANIUM = itemTag("nuggets/uranium");
    public static final TagKey<Item> INGOTS_URANIUM = itemTag("ingots/uranium");
    public static final TagKey<Item> NUGGETS_OSMIUM = itemTag("nuggets/osmium");
    public static final TagKey<Item> INGOTS_OSMIUM = itemTag("ingots/osmium");
    public static final TagKey<Item> NUGGETS_ZINC = itemTag("nuggets/zinc");
    public static final TagKey<Item> INGOTS_ZINC = itemTag("ingots/zinc");
    public static final TagKey<Item> NUGGETS_TIN = itemTag("nuggets/tin");
    public static final TagKey<Item> INGOTS_TIN = itemTag("ingots/tin");

    public static final TagKey<DamageType> IS_MAGIC = damageTypeTag("is_magic");

    public static void bootstrap() {
        // no-op
    }

    public static TagKey<Block> blockTag(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Lodestone.id(id));
    }

    public static TagKey<Item> itemTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, Lodestone.id(id));
    }

    public static TagKey<DamageType> damageTypeTag(String id) {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, Lodestone.id(id));
    }

    private LodestoneTags() {
    }
}
