package team.lodestar.lodestone.client.systems.particle.renderlayer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import team.lodestar.lodestone.client.registry.custom.LodestoneShaderRegistry;
import java.util.function.Supplier;

public interface LodestoneScreenParticleRenderLayer {
    LodestoneScreenParticleRenderLayer ADDITIVE = new LodestoneScreenParticleRenderLayer() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.setShader(LodestoneShaderRegistry.SCREEN_PARTICLE.getInstance());
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        }

        @Override
        public void end(Tessellator tessellator) {
            tessellator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }
    };

    LodestoneScreenParticleRenderLayer TRANSPARENT = new LodestoneScreenParticleRenderLayer() {
        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(LodestoneShaderRegistry.SCREEN_PARTICLE.getInstance());
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        }

        @Override
        public void end(Tessellator tesselator) {
            tesselator.end();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
        }
    };

    LodestoneScreenParticleRenderLayer LUMITRANSPARENT = new LodestoneScreenParticleRenderLayer() {
        @Override
        public void begin(BufferBuilder builder, TextureManager manager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            Supplier<ShaderProgram> program = LodestoneShaderRegistry.SCREEN_PARTICLE.getInstance();
            RenderSystem.setShader(program);
            program.get().getUniformOrDefault("LumiTransparency").set(1f);
            RenderSystem.setShaderTexture(0, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
            RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
            builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        }

        @Override
        public void end(Tessellator tessellator) {
            tessellator.draw();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            Supplier<ShaderProgram> program = LodestoneShaderRegistry.SCREEN_PARTICLE.getInstance();
            program.get().getUniformOrDefault("LumiTransparency").set(0f);
        }
    };
    
    void begin(BufferBuilder bufferBuilder, TextureManager textureManager);

    void end(Tessellator tessellator);
}
