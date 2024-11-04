package com.onrn.mccourse.datagen;

import com.onrn.mccourse.item.ModItems;
import com.onrn.mccourse.util.ModTags;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output,
            CompletableFuture<WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    /**
     * 새로운 태그 추가
     */
    @Override
    protected void configure(WrapperLookup wrapperLookup) {
        // resource/data/mccourse/tags/item/transformable_items.json
        getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.FLUORITE,
                        ModItems.RAW_FLUORITE)
                .add(Items.COAL,
                        Items.STICK);
    }
}
