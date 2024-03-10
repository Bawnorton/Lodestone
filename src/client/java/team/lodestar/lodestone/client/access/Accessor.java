package team.lodestar.lodestone.client.access;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.RenderLayer;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.client.mixin.accessor.ParticleManager$SimpleSpriteProviderAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderLayer$MultiPhaseParametersAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderPhase$ShaderProgramAccessor;
import team.lodestar.lodestone.client.mixin.accessor.RenderPhase$TextureBaseAccessor;
import team.lodestar.lodestone.client.systems.rendering.LodestoneRenderLayer;

/**
 * Helper class to reduce the line length when casting to long accessors.
 * Yes I could call the accessors something else that is shorter, or omit the enclosing class, but I like consistency and clarity.
 */
public final class Accessor {
    public static @NotNull RenderLayer$MultiPhaseParametersAccessor of(RenderLayer.MultiPhaseParameters parameters) {
        return (RenderLayer$MultiPhaseParametersAccessor) (Object) parameters;
    }

    public static @NotNull RenderLayer$MultiPhaseAccessor of(RenderLayer.MultiPhase multiPhase) {
        return (RenderLayer$MultiPhaseAccessor) (Object) multiPhase;
    }

    public static RenderPhase$ShaderProgramAccessor of(RenderLayer.ShaderProgram shaderProgram) {
        return (RenderPhase$ShaderProgramAccessor) shaderProgram;
    }

    public static RenderPhase$TextureBaseAccessor of(RenderLayer.TextureBase textureBase) {
        return (RenderPhase$TextureBaseAccessor) textureBase;
    }

    public static ParticleManager$SimpleSpriteProviderAccessor of(ParticleManager.SimpleSpriteProvider spriteProvider) {
        return (ParticleManager$SimpleSpriteProviderAccessor) spriteProvider;
    }

    public static RenderLayer$MultiPhaseAccessor of(LodestoneRenderLayer renderLayer) {
        return (RenderLayer$MultiPhaseAccessor) renderLayer;
    }

    private Accessor() {
    }
}
