package com.bawnorton.silkstone.client;

import com.bawnorton.silkstone.client.registry.SilkstoneClientRegistries;
import net.fabricmc.api.ClientModInitializer;

public class SilkstoneClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		SilkstoneClientRegistries.bootstrap();
	}
}