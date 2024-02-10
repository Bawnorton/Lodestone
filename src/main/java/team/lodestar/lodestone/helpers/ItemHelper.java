package team.lodestar.lodestone.helpers;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.mixin.accessor.EnchantmentHelperAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemHelper {
    public static List<ItemStack> copyWithNewCount(List<ItemStack> stacks, int newCount) {
        ArrayList<ItemStack> newStacks = new ArrayList<>();
        for (ItemStack stack : stacks) {
            ItemStack copy = stack.copy();
            copy.setCount(newCount);
            newStacks.add(copy);
        }
        return newStacks;
    }

    public static ItemStack copyWithNewCount(ItemStack stack, int newCount) {
        ItemStack newStack = stack.copy();
        newStack.setCount(newCount);
        return newStack;
    }

    public static <T extends LivingEntity> boolean damageItem(ItemStack stack, int amount, T entityIn, Consumer<T> onBroken) {
        if (!entityIn.getWorld().isClient() && (!(entityIn instanceof PlayerEntity player) || !(player).getAbilities().creativeMode)) {
            if (stack.isDamageable()) {
                if (stack.damage(amount, entityIn.getRandom(), entityIn instanceof ServerPlayerEntity player ? player : null)) {
                    onBroken.accept(entityIn);
                    Item item = stack.getItem();
                    stack.decrement(1);
                    if (entityIn instanceof PlayerEntity player) {
                        player.incrementStat(Stats.BROKEN.getOrCreateStat(item));
                    }

                    stack.setDamage(0);
                    return true;
                }

            }
        }
        return false;
    }

    public static <T extends Entity> Entity getClosestEntity(List<T> entities, Vec3d pos) {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;

        for (T entity : entities) {
            double newDistance = entity.squaredDistanceTo(pos.x, pos.y, pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance) {
                cachedDistance = newDistance;
                resultEntity = entity;
            }

        }
        return resultEntity;
    }

    public static List<ItemStack> nonEmptyStackList(ArrayList<ItemStack> stacks) {
        List<ItemStack> nonEmptyStacks = new ArrayList<>();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                nonEmptyStacks.add(stack);
            }
        }
        return nonEmptyStacks;
    }

    public static List<ItemStack> getEventResponders(LivingEntity attacker) {
        List<ItemStack> itemStacks = new ArrayList<>();
        ItemStack stack = attacker.getMainHandStack();
        if (stack.getItem() instanceof EventResponderItem) {
            itemStacks.add(stack);
        }
        attacker.getArmorItems().forEach(s -> {
            if (s.getItem() instanceof EventResponderItem) {
                itemStacks.add(s);
            }
        });
        return itemStacks;
    }

    public static void applyEnchantments(LivingEntity user, Entity target, ItemStack stack) {
        EnchantmentHelper.Consumer consumer = (enchantment, level) -> enchantment.onTargetDamaged(user, target, level);
        if (user != null) {
            EnchantmentHelperAccessor.invokeForEachEnchantment(consumer, user.getItemsEquipped());
        }
        if (user instanceof PlayerEntity) {
            EnchantmentHelperAccessor.invokeForEachEnchantment(consumer, stack);
        }
    }

    public static void giveItemToEntity(LivingEntity entity, ItemStack stack) {
        if (entity instanceof PlayerEntity player) {
            giveItemToPlayer(player, stack);
        } else {
            spawnItemOnEntity(entity, stack);
        }
    }

    public static void quietlyGiveItemToPlayer(PlayerEntity player, ItemStack stack) {
        giveItemToPlayer(player, stack, -1, false);
    }

    public static void spawnItemOnEntity(LivingEntity entity, ItemStack stack) {
        World world = entity.getEntityWorld();
        if(!stack.isEmpty() && !world.isClient()) {
            ItemEntity itemEntity = new ItemEntity(world, entity.getX(), entity.getY() + 0.5, entity.getZ(), stack);
            itemEntity.setPickupDelay(40);
            itemEntity.setVelocity(itemEntity.getVelocity().multiply(0, 1, 0));

            world.spawnEntity(itemEntity);
        }
    }

    public static void giveItemToPlayer(PlayerEntity player, @NotNull ItemStack stack) {
        giveItemToPlayer(player, stack, -1, true);
    }

    public static void giveItemToPlayer(PlayerEntity player, @NotNull ItemStack stack, int preferredSlot, boolean playSound) {
        if (stack.isEmpty()) return;

        PlayerInventory inventory = player.getInventory();
        World world = player.getWorld();

        int originalCount = stack.getCount();
        if (preferredSlot >= 0 && preferredSlot < inventory.size()) {
            inventory.insertStack(preferredSlot, stack);
        }

        if ((stack.isEmpty() || stack.getCount() < originalCount) && playSound) {
            world.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((world.random.nextFloat() - world.random.nextFloat()) * 0.7f + 1f) * 2f);
        }

        spawnItemOnEntity(player, stack);
    }
}