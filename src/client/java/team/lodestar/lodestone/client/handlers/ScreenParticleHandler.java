package team.lodestar.lodestone.client.handlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;
import team.lodestar.lodestone.client.config.ClientConfig;
import team.lodestar.lodestone.client.mixin.accessor.HandledScreenAccessor;
import team.lodestar.lodestone.client.systems.particle.options.ScreenParticleOptions;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleHolder;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleItemStackKey;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleItemStackRetrievalKey;
import team.lodestar.lodestone.client.systems.particle.screen.ScreenParticleType;
import team.lodestar.lodestone.client.systems.particle.screen.base.ScreenParticle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A handler for screen particles.
 * Particles are spawned during rendering once per tick.
 * We also track all present ItemStacks on the screen to allow our particles to more optimally follow a given ItemStacks position
 * Use {@link ScreenParticleHandler#addParticle(ScreenParticleHolder, ScreenParticleOptions, double, double, double, double)} to create a screen particle, which will then be ticked.
 */
public class ScreenParticleHandler {

    /**
     * Item Stack Bound Particles are rendered just after an item stack in the inventory. They are ticked the same as other particles.
     * We use a pair create a boolean and the ItemStack as a key. The boolean sorts item particles based on if the ItemStack in question is in the hotbar or not.
     */
    public static final Map<ScreenParticleItemStackKey, ScreenParticleHolder> ITEM_PARTICLES = new HashMap<>();
    public static final Map<ScreenParticleItemStackRetrievalKey, ItemStack> ITEM_STACK_CACHE = new HashMap<>();
    public static final Collection<ScreenParticleItemStackRetrievalKey> ACTIVELY_ACCESSED_KEYS = new ArrayList<>();

    public static ScreenParticleHolder cachedItemParticles = null;
    public static int currentItemX, currentItemY;

    public static final Tessellator TESSELATOR = new Tessellator();
    public static boolean canSpawnParticles;

    public static boolean renderingHotbar;

    public static void tickParticles() {
        if (!ClientConfig.ENABLE_SCREEN_PARTICLES.getConfigValue()) {
            return;
        }

        ITEM_PARTICLES.values().forEach(ScreenParticleHolder::tick);
        ITEM_PARTICLES.values().removeIf(ScreenParticleHolder::isEmpty);

        ITEM_STACK_CACHE.keySet().removeIf(k -> !ACTIVELY_ACCESSED_KEYS.contains(k));
        ACTIVELY_ACCESSED_KEYS.clear();
        canSpawnParticles = true;
    }

    public static void renderTick() {
        canSpawnParticles = false;
    }

    public static void renderItemStackEarly(MatrixStack matrixStack, ItemStack stack, int x, int y) {
        if (!ClientConfig.ENABLE_SCREEN_PARTICLES.getConfigValue()) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && client.player != null) {
            if (client.isPaused()) {
                return;
            }
            if (!stack.isEmpty()) {
                List<ParticleEmitterHandler.ItemParticleSupplier> emitters = ParticleEmitterHandler.EMITTERS.get(stack.getItem());
                if (emitters != null) {
                    currentItemX = x + 8;
                    currentItemY = y + 8;


                    if (currentItemX == 8 && currentItemY == 8) {
                        final Matrix4f pose = matrixStack.peek().getPositionMatrix();
                        float xOffset = pose.m30();
                        float yOffset = pose.m31();
                        currentItemX += xOffset;
                        currentItemY += yOffset;
                    }
                    else if (!renderingHotbar && client.currentScreen instanceof HandledScreenAccessor handledScreenAccessor) {
                        currentItemX += handledScreenAccessor.getX();
                        currentItemY += handledScreenAccessor.getY();
                    }
                    for (ParticleEmitterHandler.ItemParticleSupplier emitter : emitters) {
                        renderParticles(spawnAndPullParticles(client.world, emitter, stack, false));
                        cachedItemParticles = spawnAndPullParticles(client.world, emitter, stack, true);
                    }
                }
            }
        }
    }

    public static ScreenParticleHolder spawnAndPullParticles(ClientWorld world, ParticleEmitterHandler.ItemParticleSupplier emitter, ItemStack stack, boolean isRenderedAfterItem) {
        ScreenParticleItemStackRetrievalKey cacheKey = new ScreenParticleItemStackRetrievalKey(renderingHotbar, isRenderedAfterItem, currentItemX, currentItemY);
        ScreenParticleHolder target = ITEM_PARTICLES.computeIfAbsent(new ScreenParticleItemStackKey(renderingHotbar, isRenderedAfterItem, stack), s -> new ScreenParticleHolder());
        pullFromParticleVault(cacheKey, stack, target, isRenderedAfterItem);
        if (canSpawnParticles) {
            if (isRenderedAfterItem) {
                emitter.spawnLateParticles(target, world, MinecraftClient.getInstance().getTickDelta(), stack, currentItemX, currentItemY);
            } else {
                emitter.spawnEarlyParticles(target, world, MinecraftClient.getInstance().getTickDelta(), stack, currentItemX, currentItemY);
            }
        }
        ACTIVELY_ACCESSED_KEYS.add(cacheKey);
        return target;
    }

    public static void pullFromParticleVault(ScreenParticleItemStackRetrievalKey cacheKey, ItemStack currentStack, ScreenParticleHolder target, boolean isRenderedAfterItem) {
        if (ITEM_STACK_CACHE.containsKey(cacheKey)) {
            ItemStack oldStack = ITEM_STACK_CACHE.get(cacheKey);
            if (oldStack != currentStack && oldStack.getItem().equals(currentStack.getItem())) {
                ScreenParticleItemStackKey oldKey = new ScreenParticleItemStackKey(renderingHotbar, isRenderedAfterItem, oldStack);
                ScreenParticleHolder oldParticles = ITEM_PARTICLES.get(oldKey);
                if (oldParticles != null) {
                    target.addFrom(oldParticles);
                }
                ITEM_STACK_CACHE.remove(cacheKey);
                ITEM_PARTICLES.remove(oldKey);
            }
        }
        ITEM_STACK_CACHE.put(cacheKey, currentStack);
    }

    public static void renderItemStackLate() {
        if (cachedItemParticles != null) {
            renderParticles(cachedItemParticles);
            cachedItemParticles = null;
        }
    }

    public static void renderParticles(ScreenParticleHolder screenParticleTarget) {
        if (false) {//TODO ClientConfig.ENABLE_SCREEN_PARTICLES.getConfigValue()
            return;
        }
        screenParticleTarget.particles.forEach((renderType, particles) -> {
            renderType.begin(TESSELATOR.getBuffer(), MinecraftClient.getInstance().getTextureManager());
            for (ScreenParticle next : particles) {
                next.render(TESSELATOR.getBuffer());
            }
            renderType.end(TESSELATOR);
        });
    }

    public static void clearParticles() {
        ITEM_PARTICLES.values().forEach(ScreenParticleHandler::clearParticles);
    }

    public static void clearParticles(ScreenParticleHolder screenParticleTarget) {
        screenParticleTarget.particles.values().forEach(List::clear);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ScreenParticleOptions> ScreenParticle addParticle(ScreenParticleHolder screenParticleTarget, T options, double x, double y, double xMotion, double yMotion) {
        MinecraftClient client = MinecraftClient.getInstance();
        ScreenParticleType<T> type = (ScreenParticleType<T>) options.type;
        ScreenParticle particle = type.provider.createParticle(client.world, options, x, y, xMotion, yMotion);
        List<ScreenParticle> list = screenParticleTarget.particles.computeIfAbsent(options.renderLayer, (a) -> new ArrayList<>());
        list.add(particle);
        return particle;
    }
}