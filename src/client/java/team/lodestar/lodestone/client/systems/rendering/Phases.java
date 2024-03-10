package team.lodestar.lodestone.client.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderPhase;
import team.lodestar.lodestone.client.registry.custom.LodestoneRenderLayerRegistry;

public class Phases extends RenderPhase {
    public static final RenderPhase.Transparency ADDITIVE_TRANSPARENCY = new RenderPhase.Transparency("additive_transparency", () -> {
        RenderSystem.depthMask(false);
        LodestoneRenderLayerRegistry.ADDITIVE_FUNCTION.run();
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    });

    public static final RenderPhase.Transparency NORMAL_TRANSPARENCY = new RenderPhase.Transparency("normal_transparency", () -> {
        RenderSystem.depthMask(false);
        LodestoneRenderLayerRegistry.TRANSPARENT_FUNCTION.run();
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    });

    private Phases(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }
}
