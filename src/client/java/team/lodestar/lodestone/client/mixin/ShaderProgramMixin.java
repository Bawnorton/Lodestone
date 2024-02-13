package team.lodestar.lodestone.client.mixin;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import team.lodestar.lodestone.client.systems.rendering.shader.ExtendedShaderProgram;

@Mixin(value = ShaderProgram.class, priority = 1500)
public abstract class ShaderProgramMixin {
    @Shadow @Final private String name;

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;<init>(Ljava/lang/String;)V"))
    private String rewriteExtendedShaderProgramIds(String id) {
        return ((Object) this) instanceof ExtendedShaderProgram ? FabricShaderProgram.rewriteAsId(id, name) : id;
    }
}
