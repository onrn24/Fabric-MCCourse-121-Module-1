package com.onrn.mccourse.datagen;

import com.onrn.mccourse.block.ModBlocks;
import com.onrn.mccourse.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        /**
         * - resources/assets/mccourse/blockstates/fluorite_block.json
         * {
         *   "variants": {
         *     "": {
         *       "model": "mccourse:block/fluorite_block"
         *     }
         *   }
         * }
         *
         * - resources/assets/mccourse/models/block/fluorite_block.json
         * {
         *   "parent": "minecraft:block/cube_all",
         *   "textures": {
         *     "all": "mccourse:block/fluorite_block"
         *   }
         * }
         */
        // 기본 블록 외에 계단, 반블록 등을 등록할 수 있도록 선언
        BlockStateModelGenerator.BlockTexturePool fluoriteTexturePool =
                blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.FLUORITE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_DEEPSLATE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_END_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLUORITE_NETHER_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MAGIC_BLOCK);

        // 위에서 선언한 pool을 가지고 파생되는 여러 블록 생성
        fluoriteTexturePool.stairs(ModBlocks.FLUORITE_STAIRS);
        fluoriteTexturePool.slab(ModBlocks.FLUORITE_SLAB);
        fluoriteTexturePool.button(ModBlocks.FLUORITE_BUTTON);
        fluoriteTexturePool.pressurePlate(ModBlocks.FLUORITE_PRESSURE_PLATE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        /**
         * - resources/assets/mccourse/models/item/fluorite.json
         * {
         *   "parent": "minecraft:item/generated",
         *   "textures": {
         *     "layer0": "mccourse:item/fluorite"
         *   }
         * }
         */
        itemModelGenerator.register(ModItems.FLUORITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_FLUORITE, Models.GENERATED);

        itemModelGenerator.register(ModItems.CHAINSAW, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRAWBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.STARLIGHT_ASHES, Models.GENERATED);
    }
}
