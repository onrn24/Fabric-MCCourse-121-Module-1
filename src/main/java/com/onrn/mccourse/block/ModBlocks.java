package com.onrn.mccourse.block;

import com.onrn.mccourse.MCCourseMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    // AbstractBlock >> 계단, 슬래브(반블록) 등 다양한 특성을 가진 블록을 구현하기 위한 클래스
    // 현재는 아무것도 떨어뜨리지 않음(Custom Blocks) >> 별도의 작업이 필요함
    // .requiresTool() >> 이 블록을 캐기 위해서는 특정 도구가 필요하다고 선언
    //                 >> 근데 현재 그 도구에 대한 정보가 없어서 캘 수 없음
    public static final Block FLUORITE_BLOCK = registerBlock("fluorite_block",
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK)
                    .strength(4f).requiresTool()));
    // ExperienceDroppingBlock >> 원본 블록이 아닌 물질이 드랍됨
    // UniformIntProvider >> 균일하게 min ~ max 개의 물질을 드랍
    public static final Block FLUORITE_ORE = registerBlock("fluorite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                    AbstractBlock.Settings.create().strength(4f).requiresTool()));
    public static final Block FLUORITE_DEEPSLATE_ORE = registerBlock("fluorite_deepslate_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6),
                    AbstractBlock.Settings.create().strength(6f).requiresTool()));
    //
    public static final Block FLUORITE_END_ORE = registerBlock("fluorite_end_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 4),
                    AbstractBlock.Settings.create().strength(4f).requiresTool()));
    public static final Block FLUORITE_NETHER_ORE = registerBlock("fluorite_nether_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6),
                    AbstractBlock.Settings.create().strength(6f).requiresTool()));

    /**
     * Registries.ITEM으로 먼저 등록한 뒤에
     * Registries.BLOCK으로 등록
     *
     * block만 등록하면 spawn 가능하지만
     * ** item 등록을 하지 않으면 인벤토리(inventory)에 가질 수가 없음 >> ModItemGroups.java
     */
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MCCourseMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MCCourseMod.MOD_ID, name),
                new BlockItem(block, new Settings()));
    }

    public static void registerModBlocks() {
        MCCourseMod.LOGGER.info("Registering Mod Blocks for " + MCCourseMod.MOD_ID);
    }
}
