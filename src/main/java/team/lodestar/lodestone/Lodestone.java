package team.lodestar.lodestone;

import team.lodestar.lodestone.registry.LodestoneRegistries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lodestone implements ModInitializer {
	public static final String MOD_ID = "lodestone";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LodestoneRegistries.bootstrap();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}