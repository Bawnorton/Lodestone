package team.lodestar.lodestone.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import team.lodestar.lodestone.systems.block.LodestoneBlockSettings;

@Mixin(AbstractBlock.Settings.class)
public abstract class AbstractBlock$SettingsMixin {
    @WrapOperation(method = "copy", at = @At(value = "NEW", target = "net/minecraft/block/AbstractBlock$Settings"))
    private static AbstractBlock.Settings copy(Operation<AbstractBlock.Settings> original) {
        if(LodestoneBlockSettings.AS_LODESTONE.get()) {
            LodestoneBlockSettings.AS_LODESTONE.set(false);
            return new LodestoneBlockSettings();
        }
        return original.call();
    }
}
