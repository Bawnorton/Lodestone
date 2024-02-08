package com.bawnorton.silkstone.client.mixin;

import com.bawnorton.silkstone.client.event.ScreenEvents;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow @Final private List<Element> children;

    @Shadow protected abstract <T extends Element> T addDrawableChild(T drawableElement);

    @Shadow protected abstract void remove(Element child);

    @Inject(method = {
            "init(Lnet/minecraft/client/MinecraftClient;II)V",
            "clearAndInit"
    }, at = @At("TAIL"))
    private void invokePostInit(CallbackInfo ci) {
        ScreenEvents.POST_INIT.invoker().onPostInit((Screen) (Object) this, children, this::addDrawableChild, this::remove);
    }
}
