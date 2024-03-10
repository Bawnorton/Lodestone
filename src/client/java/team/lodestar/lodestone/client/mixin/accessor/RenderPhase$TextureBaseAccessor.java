package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import java.util.Optional;

@Mixin(RenderPhase.TextureBase.class)
public interface RenderPhase$TextureBaseAccessor {
    @Invoker
    Optional<Identifier> callGetId();
}
