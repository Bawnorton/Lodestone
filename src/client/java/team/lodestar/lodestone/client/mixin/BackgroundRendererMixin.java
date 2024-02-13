package team.lodestar.lodestone.client.mixin;

import team.lodestar.lodestone.client.events.types.FogEvents;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogShape(Lnet/minecraft/client/render/FogShape;)V", shift = At.Shift.AFTER))
    private static void invokePostFogEvent(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local BackgroundRenderer.FogData fogData) {
        FogEvents.RENDER_FOG_EVENT.invoker().onRenderFog(fogData.fogStart, fogData.fogEnd, fogData.fogShape);
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1))
    private static void invokeFogColourEvent(Args args) {
        FogEvents.FOG_COLOUR_EVENT.invoker().onFogColour(args.get(0), args.get(1), args.get(2));
    }
}
