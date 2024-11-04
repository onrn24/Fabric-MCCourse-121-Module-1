package com.onrn.mccourse.datagen;

import com.onrn.mccourse.MCCourseMod;
import com.onrn.mccourse.block.ModBlocks;
import com.onrn.mccourse.item.ModItems;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

public class ModRecipeGenerator extends FabricRecipeProvider {

    public ModRecipeGenerator(FabricDataOutput output,
            CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> FLUORITE_SMELTABLES = List.of(ModItems.RAW_FLUORITE,
                ModBlocks.FLUORITE_ORE, ModBlocks.FLUORITE_DEEPSLATE_ORE,
                ModBlocks.FLUORITE_END_ORE, ModBlocks.FLUORITE_NETHER_ORE);

        /**
         * - resources/data/mccourse/recipe/fluorite_from_smelting_raw_fluorite.json
         * - resources/data/mccourse/recipe/fluorite_from_blasting_raw_fluorite.json
         */
        // - experience : 경험치
        // - exporter : 아이템, 블록, 또는 기타 게임 요소를 정의하고 이를 게임에서 사용하기 위해
        //              내보내는 과정에서 "exporter"라는 용어가 사용
        offerSmelting(exporter, FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE, 0.2f
                , 200, "fluorite");
        offerBlasting(exporter, FLUORITE_SMELTABLES, RecipeCategory.MISC, ModItems.FLUORITE, 0.2f
                , 100, "fluorite");

        // - resources/data/mccourse/recipe/fluorite.json
        // - resources/data/mccourse/recipe/fluorite_block.json
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS,
                ModItems.FLUORITE, RecipeCategory.DECORATIONS, ModBlocks.FLUORITE_BLOCK);

        // - resources/data/mccourse/recipe/~.json
        // 새로운 커스텀 레시피 등록
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                .pattern("FFF")
                .pattern("FSF")
                .pattern("FFF")
                .input('F', ModItems.FLUORITE)
                .input('S', Blocks.STONE)
                .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
                .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                .offerTo(exporter);
        /**
         * - json 파일로 만들어질 때 이름 >> raw_fluorite.json로 만들어짐
         * - 그럼 아래의 레시피랑 이름이 겹쳐서 오류가 발생함
         *
         * ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
         *                 .pattern("SSS")
         *                 .pattern("SFS")
         *                 .pattern("SSS")
         *                 .input('F', ModItems.FLUORITE)
         *                 .input('S', Blocks.STONE)
         *                 .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
         *                 .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
         *                 .offerTo(exporter);
         */
        // 그래서 offerTo(exporter, Identifier.of()); 추가
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_FLUORITE)
                .pattern("SSS")
                .pattern("SFS")
                .pattern("SSS")
                .input('F', ModItems.FLUORITE)
                .input('S', Blocks.STONE)
                .criterion(hasItem(Blocks.STONE), conditionsFromItem(Blocks.STONE))
                .criterion(hasItem(ModItems.FLUORITE), conditionsFromItem(ModItems.FLUORITE))
                .offerTo(exporter, Identifier.of(MCCourseMod.MOD_ID, "raw_fluorite_2"));


    }
}
