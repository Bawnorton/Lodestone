package team.lodestar.lodestone.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import team.lodestar.lodestone.client.extender.VertexConsumerExtender;
import java.nio.ByteBuffer;

@Mixin(VertexConsumer.class)
public interface VertexConsumerMixin extends VertexConsumerExtender {
    @Override
    default void lodestone$passAlpha(float alpha) {
        VertexConsumerExtender.lodestone$getAlpha.set(alpha);
    }

    @ModifyArgs(method = "quad(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/model/BakedQuad;[FFFF[IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(FFFFFFFFFIIFFF)V"))
    default void usePassedAlpha(Args args, MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, boolean useQuadColorData, @Local ByteBuffer byteBuffer) {
        float alpha = VertexConsumerExtender.lodestone$getAlpha.get();
        if(useQuadColorData) {
            args.set(6, alpha * (byteBuffer.get(15) & 255) / 255F);
        } else {
            args.set(6, alpha);
        }
        lodestone$passAlpha(1.0F);
    }
}
