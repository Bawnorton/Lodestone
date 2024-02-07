package com.bawnorton.silkstone;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Silkstone implements ModInitializer {
	public static final String MOD_ID = "silkstone";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("This is gonna be a wild ride and I think I've bitten off more than I can chew.");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}