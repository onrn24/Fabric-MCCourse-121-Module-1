package com.onrn.mccourse.block;

import com.onrn.mccourse.MCCourseMod;
import com.onrn.mccourse.block.custom.MagicBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WoodType;
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
    //
    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            new MagicBlock(AbstractBlock.Settings.create().strength(1f).requiresTool()));
    //
    public static final Block FLUORITE_STAIRS = registerBlock("fluorite_stairs",
            new StairsBlock(ModBlocks.FLUORITE_BLOCK.getDefaultState(),
                    AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block FLUORITE_SLAB = registerBlock("fluorite_slabs",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    // pressTicks : 버튼이 활성 상태(누른 상태)로 유지되는 시간(틱 단위)을 나타냅니다.
    //   이 값은 Minecraft에서 게임 틱을 기준으로 측정되며,
    //   버튼을 누른 후 버튼이 다시 비활성화되기까지 걸리는 시간을 정의합니다.
    // 예를 들어, pressTicks 값이 20이면 버튼을 누른 후 약 1초 동안 활성 상태로 유지됩니다(1초 = 20틱).
    // 이 설정을 통해 버튼을 누른 후에 얼마나 오랫동안 신호가 유지될지를 조정할 수 있으며,
    // 이는 레드스톤 회로나 다른 상호작용 요소에 중요한 역할을 합니다.
    public static final Block FLUORITE_BUTTON = registerBlock("fluorite_button",
            new ButtonBlock(BlockSetType.IRON, 10, AbstractBlock.Settings.create().requiresTool()));
    public static final Block FLUORITE_PRESSURE_PLATE = registerBlock("fluorite_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.create().requiresTool()));
    //
    public static final Block FLUORITE_FENCE = registerBlock("fluorite_fence",
            new FenceBlock(AbstractBlock.Settings.create().requiresTool()));
    public static final Block FLUORITE_FENCE_GATE = registerBlock("fluorite_fence_gate",
            new FenceGateBlock(WoodType.ACACIA, AbstractBlock.Settings.create().requiresTool()));
    public static final Block FLUORITE_WALL = registerBlock("fluorite_wall",
            new WallBlock(AbstractBlock.Settings.create().requiresTool()));
    // - BlockSetType.IRON으로 설정해서 우클릭으로 열 수 없다.
    // 버튼, 레버, 압력판, 레드스톤 회로로 열 수 있다.
    public static final Block FLUORITE_DOOR = registerBlock("fluorite_door",
            new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().requiresTool().nonOpaque()));
    public static final Block FLUORITE_TRAPDOOR = registerBlock("fluorite_trapdoor",
            new TrapdoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().requiresTool().nonOpaque()));

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
