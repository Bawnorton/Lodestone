package team.lodestar.lodestone.client.mixin.accessor;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.List;

@Mixin(ParticleManager.SimpleSpriteProvider.class)
public interface ParticleManager$SimpleSpriteProviderAccessor {
    @Accessor
    List<Sprite> getSprites();
}
