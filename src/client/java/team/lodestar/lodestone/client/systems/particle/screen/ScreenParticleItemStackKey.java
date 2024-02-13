package team.lodestar.lodestone.client.systems.particle.screen;

import net.minecraft.item.ItemStack;

public record ScreenParticleItemStackKey(boolean isHotBarItem, boolean isRenderedAfterItem, ItemStack stack) {
}
