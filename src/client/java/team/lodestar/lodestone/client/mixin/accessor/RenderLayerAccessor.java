package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.class)
public interface RenderLayerAccessor {
    @Accessor
    boolean isTranslucent();
}
