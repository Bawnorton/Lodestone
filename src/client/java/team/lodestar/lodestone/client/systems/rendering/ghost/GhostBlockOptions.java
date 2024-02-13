package team.lodestar.lodestone.client.systems.rendering.ghost;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.BlockPos;
//import team.lodestar.lodestone.handlers.PlacementAssistantHandler;
import java.awt.Color;
import java.util.function.Supplier;

public class GhostBlockOptions {

    protected final BlockState blockState;
    protected BlockPos blockPos;
    protected Supplier<Float> alphaSupplier;
    protected Supplier<Float> scaleSupplier;
    protected float red = 1, green = 1, blue = 1;
    protected RenderLayer renderLayer = RenderLayer.getTranslucent();

    private GhostBlockOptions(BlockState state) {
        this.blockState = state;
        this.blockPos = BlockPos.ORIGIN;
//        this.alphaSupplier = () -> PlacementAssistantHandler.getCurrentAlpha() * 0.75f;
        this.scaleSupplier = () -> 1.0F;
    }

    public static GhostBlockOptions create(BlockState state, BlockPos pos) {
        return new GhostBlockOptions(state).at(pos);
    }

    public static GhostBlockOptions create(BlockState state) {
        return new GhostBlockOptions(state);
    }

    public static GhostBlockOptions create(Block block, BlockPos pos) {
        return create(block.getDefaultState(), pos);
    }

    public static GhostBlockOptions create(Block block) {
        return create(block.getDefaultState());
    }

    public GhostBlockOptions at(BlockPos pos) {
        this.blockPos = pos;
        return this;
    }

    public GhostBlockOptions at(int x, int y, int z) {
        return at(new BlockPos(x, y, z));
    }

    public GhostBlockOptions withAlpha(Supplier<Float> alphaSupplier) {
        this.alphaSupplier = alphaSupplier;
        return this;
    }

    public GhostBlockOptions withScale(Supplier<Float> scaleSupplier) {
        this.scaleSupplier = scaleSupplier;
        return this;
    }

    public GhostBlockOptions withColor(Color color) {
        return withColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
    }

    public GhostBlockOptions withColor(float r, float g, float b) {
        this.red = r;
        this.green = g;
        this.blue = b;
        return this;
    }

    public GhostBlockOptions withRenderLayer(RenderLayer renderLayer) {
        this.renderLayer = renderLayer;
        return this;
    }
}