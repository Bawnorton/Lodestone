package team.lodestar.lodestone.systems.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import team.lodestar.lodestone.dummyclient.RenderLayerReference;
import team.lodestar.lodestone.handlers.ThrowawayBlockDataHandler;
import team.lodestar.lodestone.systems.datagen.LodestoneDatagenBlockData;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;


@SuppressWarnings({"unused", "UnusedReturnValue"})
public class LodestoneBlockSettings extends Settings {
    public static final ThreadLocal<Boolean> AS_LODESTONE = ThreadLocal.withInitial(() -> false);

    public static LodestoneBlockSettings of() {
        return new LodestoneBlockSettings();
    }
    
    public static LodestoneBlockSettings copy(AbstractBlock block) {
        AS_LODESTONE.set(true);
        return (LodestoneBlockSettings) Settings.copy(block);
    }

    public LodestoneBlockSettings addThrowawayData(UnaryOperator<LodestoneThrowawayBlockData> function) {
        ThrowawayBlockDataHandler.THROWAWAY_DATA_CACHE.put(this, function.apply(new LodestoneThrowawayBlockData()));
        return this;
    }

    public LodestoneThrowawayBlockData getThrowawayData() {
        return ThrowawayBlockDataHandler.THROWAWAY_DATA_CACHE.get(this);
    }

    public LodestoneBlockSettings setCutoutRenderLayer() {
        Optional<RenderLayerReference> reference = RenderLayerReference.getCutoutMipped();
        if (reference.isPresent()) {
            return setRenderLayer(() -> reference::get);
        }
        return this;
    }

    public LodestoneBlockSettings setRenderLayer(Supplier<Supplier<RenderLayerReference>> renderLayer) {
        return addThrowawayData(data -> data.setRenderLayer(renderLayer));
    }

    public LodestoneBlockSettings addDatagenData(UnaryOperator<LodestoneDatagenBlockData> function) {
        ThrowawayBlockDataHandler.DATAGEN_DATA_CACHE.put(this, function.apply(new LodestoneDatagenBlockData()));
        return this;
    }
    
    public LodestoneDatagenBlockData getDatagenData() {
        return ThrowawayBlockDataHandler.DATAGEN_DATA_CACHE.get(this);
    }
    
    public LodestoneBlockSettings addTag(TagKey<Block> tag) {
        addDatagenData(data -> data.addTag(tag));
        return this;
    }
    
    @SafeVarargs
    public final LodestoneBlockSettings addTags(TagKey<Block>... tags) {
        addDatagenData(data -> data.addTags(tags));
        return this;
    }
    
    public LodestoneBlockSettings hasInheritedLoot() {
        addDatagenData(LodestoneDatagenBlockData::hasInheritedLoot);
        return this;
    }
    
    public LodestoneBlockSettings needsPickaxe() {
        addDatagenData(LodestoneDatagenBlockData::needsPickaxe);
        return this;
    }
    
    public LodestoneBlockSettings needsAxe() {
        addDatagenData(LodestoneDatagenBlockData::needsAxe);
        return this;
    }
    
    public LodestoneBlockSettings needsShovel() {
        addDatagenData(LodestoneDatagenBlockData::needsShovel);
        return this;
    }
    
    public LodestoneBlockSettings needsHoe() {
        addDatagenData(LodestoneDatagenBlockData::needsHoe);
        return this;
    }
    
    public LodestoneBlockSettings needsStone() {
        addDatagenData(LodestoneDatagenBlockData::needsStone);
        return this;
    }
    
    public LodestoneBlockSettings needsIron() {
        addDatagenData(LodestoneDatagenBlockData::needsIron);
        return this;
    }
    
    public LodestoneBlockSettings needsDiamond() {
        addDatagenData(LodestoneDatagenBlockData::needsDiamond);
        return this;
    }

    @Override
    public LodestoneBlockSettings mapColor(DyeColor color) {
        return (LodestoneBlockSettings) super.mapColor(color);
    }

    @Override
    public LodestoneBlockSettings mapColor(MapColor color) {
        return (LodestoneBlockSettings) super.mapColor(color);
    }

    @Override
    public LodestoneBlockSettings mapColor(Function<BlockState, MapColor> mapColorProvider) {
        return (LodestoneBlockSettings) super.mapColor(mapColorProvider);
    }

    @Override
    public LodestoneBlockSettings noCollision() {
        return (LodestoneBlockSettings) super.noCollision();
    }

    @Override
    public LodestoneBlockSettings nonOpaque() {
        return (LodestoneBlockSettings) super.nonOpaque();
    }

    @Override
    public LodestoneBlockSettings slipperiness(float slipperiness) {
        return (LodestoneBlockSettings) super.slipperiness(slipperiness);
    }

    @Override
    public LodestoneBlockSettings velocityMultiplier(float velocityMultiplier) {
        return (LodestoneBlockSettings) super.velocityMultiplier(velocityMultiplier);
    }

    @Override
    public LodestoneBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
        return (LodestoneBlockSettings) super.jumpVelocityMultiplier(jumpVelocityMultiplier);
    }

    @Override
    public LodestoneBlockSettings sounds(BlockSoundGroup soundGroup) {
        return (LodestoneBlockSettings) super.sounds(soundGroup);
    }

    @Override
    public LodestoneBlockSettings luminance(ToIntFunction<BlockState> luminance) {
        return (LodestoneBlockSettings) super.luminance(luminance);
    }

    @Override
    public LodestoneBlockSettings strength(float hardness, float resistance) {
        return (LodestoneBlockSettings) super.strength(hardness, resistance);
    }

    @Override
    public LodestoneBlockSettings breakInstantly() {
        return (LodestoneBlockSettings) super.breakInstantly();
    }

    @Override
    public LodestoneBlockSettings strength(float strength) {
        return (LodestoneBlockSettings) super.strength(strength);
    }

    @Override
    public LodestoneBlockSettings ticksRandomly() {
        return (LodestoneBlockSettings) super.ticksRandomly();
    }

    @Override
    public LodestoneBlockSettings dynamicBounds() {
        return (LodestoneBlockSettings) super.dynamicBounds();
    }

    @Override
    public LodestoneBlockSettings dropsNothing() {
        return (LodestoneBlockSettings) super.dropsNothing();
    }

    @Override
    public LodestoneBlockSettings dropsLike(Block source) {
        return (LodestoneBlockSettings) super.dropsLike(source);
    }

    @Override
    public LodestoneBlockSettings burnable() {
        return (LodestoneBlockSettings) super.burnable();
    }

    @Override
    public LodestoneBlockSettings liquid() {
        return (LodestoneBlockSettings) super.liquid();
    }

    @Override
    public LodestoneBlockSettings solid() {
        return (LodestoneBlockSettings) super.solid();
    }
    
    @Override
    public LodestoneBlockSettings pistonBehavior(PistonBehavior pistonBehavior) {
        return (LodestoneBlockSettings) super.pistonBehavior(pistonBehavior);
    }

    @Override
    public LodestoneBlockSettings air() {
        return (LodestoneBlockSettings) super.air();
    }

    @Override
    public LodestoneBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
        return (LodestoneBlockSettings) super.allowsSpawning(predicate);
    }

    @Override
    public LodestoneBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
        return (LodestoneBlockSettings) super.solidBlock(predicate);
    }

    @Override
    public LodestoneBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
        return (LodestoneBlockSettings) super.suffocates(predicate);
    }

    @Override
    public LodestoneBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
        return (LodestoneBlockSettings) super.blockVision(predicate);
    }

    @Override
    public LodestoneBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
        return (LodestoneBlockSettings) super.postProcess(predicate);
    }

    @Override
    public LodestoneBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
        return (LodestoneBlockSettings) super.emissiveLighting(predicate);
    }

    @Override
    public LodestoneBlockSettings requiresTool() {
        return (LodestoneBlockSettings) super.requiresTool();
    }

    @Override
    public LodestoneBlockSettings hardness(float hardness) {
        return (LodestoneBlockSettings) super.hardness(hardness);
    }

    @Override
    public LodestoneBlockSettings resistance(float resistance) {
        return (LodestoneBlockSettings) super.resistance(resistance);
    }

    @Override
    public LodestoneBlockSettings offset(AbstractBlock.OffsetType offsetType) {
        return (LodestoneBlockSettings) super.offset(offsetType);
    }

    @Override
    public LodestoneBlockSettings noBlockBreakParticles() {
        return (LodestoneBlockSettings) super.noBlockBreakParticles();
    }

    @Override
    public LodestoneBlockSettings requires(FeatureFlag... features) {
        return (LodestoneBlockSettings) super.requires(features);
    }

    @Override
    public LodestoneBlockSettings instrument(Instrument instrument) {
        return (LodestoneBlockSettings) super.instrument(instrument);
    }

    @Override
    public LodestoneBlockSettings replaceable() {
        return (LodestoneBlockSettings) super.replaceable();
    }
}
