package team.lodestar.lodestone.dummyclient;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Supplier;

public abstract class ClientClassReference {
    private static final Supplier<Boolean> onClient = () -> FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    private static final MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
    private static final MethodHandles.Lookup ownerLookup = MethodHandles.lookup();
    private static final Map<Class<?>, MethodHandles.Lookup> lookupCache = new WeakHashMap<>();

    public static Optional<Class<?>> getClass(String intermediaryClassName) {
        if (!onClient.get()) return Optional.empty();

        try {
            return Optional.of(Class.forName(resolver.mapClassName("intermediary", intermediaryClassName)));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static Optional<MethodHandle> getStaticMethod(Class<?> reference, String intermediaryMethodName, String methodDescriptor, Class<?> returnType, Class<?>... parameterTypes) {
        if (!onClient.get()) return Optional.empty();

        String intermediaryClassName = resolver.unmapClassName("intermediary", reference.getName());
        String methodName = resolver.mapMethodName("intermediary", intermediaryClassName, intermediaryMethodName, methodDescriptor);
        MethodHandles.Lookup lookup = getLookup(reference);
        try {
            return Optional.ofNullable(lookup.findStatic(reference, methodName, MethodType.methodType(returnType, parameterTypes)));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            return Optional.empty();
        }
    }

    public static Optional<VarHandle> getStaticField(Class<?> reference, String intermediaryFieldName, String fieldDescriptor, Class<?> fieldType) {
        if (!onClient.get()) return Optional.empty();

        String intermediaryClassName = resolver.unmapClassName("intermediary", reference.getName());
        String fieldName = resolver.mapFieldName("intermediary", intermediaryClassName, intermediaryFieldName, fieldDescriptor);
        MethodHandles.Lookup lookup = getLookup(reference);
        try {
            return Optional.ofNullable(lookup.findStaticVarHandle(reference, fieldName, fieldType));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }

    private static MethodHandles.Lookup getLookup(Class<?> reference) {
        return lookupCache.computeIfAbsent(reference, k -> {
            try {
                return MethodHandles.privateLookupIn(k, ownerLookup);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
