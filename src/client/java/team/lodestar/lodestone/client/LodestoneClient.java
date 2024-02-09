package team.lodestar.lodestone.client;

import team.lodestar.lodestone.client.registry.LodestoneClientRegistries;
import net.fabricmc.api.ClientModInitializer;

public class LodestoneClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LodestoneClientRegistries.bootstrap();
	}
}