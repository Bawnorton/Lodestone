package team.lodestar.lodestone.client.systems;

import net.minecraft.client.MinecraftClient;

public class ClientTickCounter {
    public static long ticksInGame = 0L;
    public static float tickDelta = 0.0F;

    public static float getTotal() {
        return (float) ticksInGame + tickDelta;
    }

    public static void renderTick(float tickDelta) {
        ClientTickCounter.tickDelta = tickDelta;
    }

    public static void clientTick() {
        ++ticksInGame;
        tickDelta = 0.0F;
    }
}
