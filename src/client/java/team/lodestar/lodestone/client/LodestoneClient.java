package team.lodestar.lodestone.client;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;
import team.lodestar.lodestone.Lodestone;
import team.lodestar.lodestone.client.config.ClientConfig;
import team.lodestar.lodestone.client.registry.LodestoneClientRegistries;

public class LodestoneClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LodestoneClientRegistries.bootstrap();

		ForgeConfigRegistry.INSTANCE.register(Lodestone.MOD_ID, ModConfig.Type.CLIENT, ClientConfig.SPEC);
	}
}