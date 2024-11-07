package com.onrn.mccourse.item;

import com.google.common.base.Suppliers;
import com.onrn.mccourse.util.ModTags;
import com.onrn.mccourse.util.ModTags.Blocks;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

/**
 * 새로운 ToolMaterial 추가
 * - vanilla : wood, stone, iron, diamond, gold, netherite
 * - mod :
 */
public enum ModToolMaterials implements ToolMaterial {
    /**
     * 새로운 물질을 추가하려거든 아래의 FLUORITE처럼 선언하면 됨
     * 물질들 구분은 ','로 하고, 마지막에 ';'
     *
     * - inverseTag : 특정 도구 재질(enum 값)에 대해 비효율적으로 작동하는 블록 태그를 정의하는 데 사용
     * - BlockTags.INCORRECT_FOR_WOODEN_TOOL :
     *   - 나무 도구에 적합하지 않은 블록들을 포함하는 태그입니다.
     *   이 태그는 나무 도구(예: 나무 곡괭이, 나무 도끼 등)가 비효율적으로 작동하거나 제대로 사용되지 않는 블록을 정의하는 데 사용
     *   - 예를 들어, 나무 곡괭이는 돌이나 철 같은 단단한 블록을 캐기에는 적합하지 않기 때문에
     *   이 태그에 포함된 블록에서는 나무 곡괭이가 정상적인 속도로 작동하지 않거나 블록을 제대로 캘 수 없습니다.
     *   이를 통해 플레이어가 특정 도구와 블록 조합을 사용할 때 효과적인 플레이 경험을 제공
     *
     * 플로라이트를 커스텀 도구 소재로 만들어 철과 동일한 티어로 설정합니다.
     * 이렇게 하면 needs_iron_tool 태그에 추가된 블록을 플로라이트로도 채굴할 수 있습니다.
     * 이를 위해 JSON 파일을 생성하고, 직접 태그를 추가
     * (네더라이트보다 높은 티어로 설정할 수는 없음. 이후 버전들에서 추가될 가능성은 있음)
     */
    FLUORITE(Blocks.INCORRECT_FOR_FLUORITE_TOOL, 1500, 7.0F, 2.0F, 22,
            () -> Ingredient.ofItems(ModItems.FLUORITE));

    private final TagKey<Block> inverseTag;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(
            final TagKey<Block> inverseTag,
            final int itemDurability,
            final float miningSpeed,
            final float attackDamage,
            final int enchantability,
            final Supplier<Ingredient> repairIngredient
    ) {
        this.inverseTag = inverseTag;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getInverseTag() {
        return this.inverseTag;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}
