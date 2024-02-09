package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderLayer.MultiPhaseParameters.class)
public interface RenderLayer$MultiPhaseParametersAccessor {
    @Accessor
    RenderPhase.ShaderProgram getProgram();

    @Accessor
    RenderPhase.Transparency getTransparency();

    @Accessor
    RenderPhase.TextureBase getTexture();

    @Accessor
    RenderPhase.Cull getCull();

    @Accessor
    RenderPhase.Lightmap getLightmap();
}
