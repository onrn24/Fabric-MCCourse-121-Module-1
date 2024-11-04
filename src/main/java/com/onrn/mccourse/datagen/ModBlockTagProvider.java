package com.onrn.mccourse.datagen;

import com.onrn.mccourse.block.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output,
            CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * 새로운 태그 추가
     */
    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        // resource/data/minecraft/tags/block/mineable/pickaxe.json
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.FLUORITE_BLOCK,
                        ModBlocks.FLUORITE_ORE,
                        ModBlocks.FLUORITE_DEEPSLATE_ORE,
                        ModBlocks.FLUORITE_END_ORE,
                        ModBlocks.FLUORITE_NETHER_ORE);
        // resource/data/minecraft/tags/block/needs_diamond_tool.json
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FLUORITE_END_ORE,
                        ModBlocks.FLUORITE_NETHER_ORE);
        // resource/data/minecraft/tags/block/needs_iron_tool.json
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.FLUORITE_DEEPSLATE_ORE);
    }
}
