package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Optional;
import java.util.function.Supplier;

@Mixin(RenderPhase.ShaderProgram.class)
public interface RenderPhase$ShaderProgramAccessor {
    @Accessor
    Optional<Supplier<ShaderProgram>> getSupplier();
}
