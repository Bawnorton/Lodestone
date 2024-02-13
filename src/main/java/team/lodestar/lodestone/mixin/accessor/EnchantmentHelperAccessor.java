package team.lodestar.lodestone.mixin.accessor;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnchantmentHelper.class)
public interface EnchantmentHelperAccessor {
    @Invoker
    static void invokeForEachEnchantment(EnchantmentHelper.Consumer consumer, Iterable<ItemStack> stacks) {
        throw new AssertionError();
    }
}
