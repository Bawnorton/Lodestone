package team.lodestar.lodestone.client.mixin.accessor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import team.lodestar.lodestone.client.systems.rendering.renderlayer.RenderLayerProvider;
import java.util.function.BiFunction;

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

    @Accessor
    RenderLayer.OutlineMode getOutlineMode();

    @Accessor
    ImmutableList<RenderPhase> getPhases();
}
