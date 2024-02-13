package team.lodestar.lodestone.client.extender;

import java.util.concurrent.atomic.AtomicReference;

public interface VertexConsumerExtender {
    AtomicReference<Float> lodestone$getAlpha = new AtomicReference<>(1.0F);

    void lodestone$passAlpha(float alpha);
}
