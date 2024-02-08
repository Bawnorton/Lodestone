package com.bawnorton.silkstone.client.mixin;

import com.bawnorton.silkstone.client.event.RegisterShadersEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
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
    private boolean onRegisterShaders(List<Pair<ShaderProgram, Consumer<ShaderProgram>>> instance, Object e, Operation<Boolean> original, ResourceFactory factory) {
        boolean result = original.call(instance, e);
        RegisterShadersEvent.EVENT.invoker().registerShaders(factory, instance);
        return result;
    }
}
