package com.onrn.mccourse.item;

import com.onrn.mccourse.MCCourseMod;
import com.onrn.mccourse.item.custom.ChainsawItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * 모든 아이템이 등록될 클래스(be registered)
 * * MCCourseMod 에서 호출됨
 *
 * 아직 텍스처(texture)가 없음 > 인게임에서 두 색상의 패턴으로 표시됨 > resource/assets/mccourse 에 추가해야 함
 */
public class ModItems {
    // 새로운 아이템 생성
    // 등록하는 아이템과과 textures/item/~.png을 매치시켜야 함
    //   >> 이를 models/item/~.json이 담당함
    public static final Item FLUORITE = registerItem("fluorite", new Item(new Settings()));
    public static final Item RAW_FLUORITE = registerItem("raw_fluorite", new Item(new Settings()));
    // maxDamage() >> 내구도 32
    public static final Item CHAINSAW = registerItem("chainsaw",
            new ChainsawItem(new Settings().maxDamage(32)));
    //
    public static final Item STRAWBERRY = registerItem("strawberry",
            new Item(new Settings().food(ModFoodComponents.STRAWBERRY)));
    /**
     * 새로운 연료용 아이템 : starlight_ashes
     */
    public static final Item STARLIGHT_ASHES = registerItem("starlight_ashes", new Item(new Settings()));

    /**
     * Registry에 새로운 아이템을 등록하기 위한 메서드
     */
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MCCourseMod.MOD_ID, name), item);
    }

    /**
     * static 아이템을 등록하기 때문에 static method로 선언
     */
    public static void registerModItems() {
        MCCourseMod.LOGGER.info("Registering Mod Items for " + MCCourseMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customIngredients);
    }

    /**
     * 이미 존재하는 아이템 그룹에 새로운 아이템들을 추가하기
     */
    private static void customIngredients(FabricItemGroupEntries entries) {
        entries.add(FLUORITE);
        entries.add(RAW_FLUORITE);
    }
}
