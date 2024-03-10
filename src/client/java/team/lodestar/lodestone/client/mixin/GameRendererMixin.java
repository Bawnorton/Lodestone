package team.lodestar.lodestone.client.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.lodestar.lodestone.client.events.types.ShaderEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import team.lodestar.lodestone.client.handlers.PostProcessHandler;
import team.lodestar.lodestone.client.handlers.RenderHandler;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @WrapOperation(
            method = "loadPrograms",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=rendertype_gui_ghost_recipe_overlay"
                    )
            )
    )
    private boolean invokeRegisterShadersEvent(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> instance, Object e, Operation<Boolean> original, ResourceFactory factory) throws IOException {
        boolean result = original.call(instance, e);
        ShaderEvents.REGISTER.invoker().register(factory, instance);
        return result;
    }

    @Inject(method = "onResized", at = @At("HEAD"))
    private void onResized(int width, int height, CallbackInfo ci) {
        RenderHandler.resize(width, height);
        PostProcessHandler.resize(width, height);
    }
}
