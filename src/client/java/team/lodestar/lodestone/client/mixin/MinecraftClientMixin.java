package team.lodestar.lodestone.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import team.lodestar.lodestone.client.events.types.ClientRenderEvents;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(FJZ)V"))
    private void onRender(GameRenderer instance, float tickDelta, long startTime, boolean tick, Operation<Void> original) {
        ClientRenderEvents.START_RENDER_TICK_EVENT.invoker().onRenderTick(tickDelta);
        original.call(instance, tickDelta, startTime, tick);
        ClientRenderEvents.END_RENDER_TICK_EVENT.invoker().onRenderTick(tickDelta);
    }
}
