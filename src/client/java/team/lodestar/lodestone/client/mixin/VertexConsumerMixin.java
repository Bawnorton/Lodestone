package team.lodestar.lodestone.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import team.lodestar.lodestone.client.extender.VertexConsumerExtender;
import java.nio.ByteBuffer;

@Mixin(VertexConsumer.class)
public abstract class VertexConsumerMixin implements VertexConsumerExtender {
    @Unique
    private float lodestone$alpha = 1F;

    @Override
    public void lodestone$passAlpha(float alpha) {
        this.lodestone$alpha = alpha;
    }

    @ModifyArgs(method = "quad(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/model/BakedQuad;[FFFF[IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;vertex(FFFFFFFFFIIFFF)V"))
    private void usePassedAlpha(Args args, MatrixStack.Entry matrixEntry, BakedQuad quad, float[] brightnesses, float red, float green, float blue, int[] lights, int overlay, boolean useQuadColorData, @Local ByteBuffer byteBuffer) {
        if(useQuadColorData) {
            args.set(6, lodestone$alpha * (byteBuffer.get(15) & 255) / 255F);
        } else {
            args.set(6, lodestone$alpha);
        }
        lodestone$alpha = 1F;
    }
}
