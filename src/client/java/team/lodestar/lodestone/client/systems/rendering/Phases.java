package team.lodestar.lodestone.client.systems.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderPhase;

public class Phases extends RenderPhase {
    public static final RenderPhase.Transparency ADDITIVE_TRANSPARENCY = new RenderPhase.Transparency("additive_transparency", () -> {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    });

    public static final RenderPhase.Transparency NORMAL_TRANSPARENCY = new RenderPhase.Transparency("normal_transparency", () -> {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
    });

    private Phases(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }
}
