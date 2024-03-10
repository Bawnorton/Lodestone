package team.lodestar.lodestone.dummyclient;

import org.jetbrains.annotations.Nullable;
import java.lang.invoke.MethodHandle;
import java.util.Optional;

public final class RenderLayerReference {
    private static final String INTERMEDIARY_NAME = "net.minecraft.class_1921";

    @Nullable
    private static MethodHandle getCutoutMipped;

    private final Object actual;

    static {
        ClientClassReference.getClass(INTERMEDIARY_NAME).ifPresent(referenceClass -> {
            ClientClassReference.getStaticMethod(referenceClass, "method_23579", "()Lnet/minecraft/class_1921;", referenceClass).ifPresent(handle -> getCutoutMipped = handle);
        });
    }

    public RenderLayerReference(Object actual) {
        this.actual = actual;
    }

    public static Optional<RenderLayerReference> getCutoutMipped() {
        if (getCutoutMipped == null) return Optional.empty();

        try {
            return Optional.of(new RenderLayerReference(getCutoutMipped.invoke()));
        } catch (Throwable ignored) {
            return Optional.empty();
        }
    }

    public Object getActual() {
        return actual;
    }
}
