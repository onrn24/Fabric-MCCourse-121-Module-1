package com.onrn.mccourse.datagen;

import com.onrn.mccourse.block.ModBlocks;
import com.onrn.mccourse.item.ModItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {

    public ModLootTableGenerator(FabricDataOutput dataOutput,
            CompletableFuture<WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    /**
     * drops (드랍 아이템) 추가
     */
    @Override
    public void generate() {
        // resources/data/mccourse/loot_table/blocks/~.json

        // 블록을 캐면 그대로 드랍됨
        addDrop(ModBlocks.FLUORITE_BLOCK);
        addDrop(ModBlocks.MAGIC_BLOCK);

        // 블록을 캐면 새로운 아이템이 드랍됨
        // ex) FLUORITE_ORE >> RAW_FLUORITE
        addDrop(ModBlocks.FLUORITE_ORE, oreDrops(ModBlocks.FLUORITE_ORE, ModItems.RAW_FLUORITE));
        // 최소 개수, 최대 개수 설정 가능
        addDrop(ModBlocks.FLUORITE_DEEPSLATE_ORE,
                multipleOreDrops(ModBlocks.FLUORITE_ORE, ModItems.RAW_FLUORITE, 2,5));
        addDrop(ModBlocks.FLUORITE_END_ORE,
                multipleOreDrops(ModBlocks.FLUORITE_ORE, ModItems.RAW_FLUORITE,4, 8));
        addDrop(ModBlocks.FLUORITE_NETHER_ORE,
                multipleOreDrops(ModBlocks.FLUORITE_ORE, ModItems.RAW_FLUORITE, 1, 7));

        addDrop(ModBlocks.FLUORITE_STAIRS);
        addDrop(ModBlocks.FLUORITE_SLAB, slabDrops(ModBlocks.FLUORITE_SLAB));

        addDrop(ModBlocks.FLUORITE_BUTTON);
        addDrop(ModBlocks.FLUORITE_PRESSURE_PLATE);

        addDrop(ModBlocks.FLUORITE_WALL);
        addDrop(ModBlocks.FLUORITE_FENCE);
        addDrop(ModBlocks.FLUORITE_FENCE_GATE);
        // 문은 셀 두 칸을 차지하고 있어서 별도의 doorDrops 사용
        addDrop(ModBlocks.FLUORITE_DOOR, doorDrops(ModBlocks.FLUORITE_DOOR));
        addDrop(ModBlocks.FLUORITE_TRAPDOOR);
    }

    /**
     * - BlockLootTableGeneratoe >> copperOreDrops 메서드 복사
     * - 드랍되는 item에 대해서 최소 개수, 최대 개수 설정
     */
    public LootTable.Builder multipleOreDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops)))
                                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }
}
