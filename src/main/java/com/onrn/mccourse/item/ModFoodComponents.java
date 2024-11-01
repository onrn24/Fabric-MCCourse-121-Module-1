package com.onrn.mccourse.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {

    /**
     * nutrition : 영양 수치 >> 배고픔 게이지 채움
     * saturationModifier : 포화도 >> 배고픔 게이지가 줄어들기 전에 얼마나 오래 유지될 지
     * statusEffect : 상태 효과
     * - new StatusEffectInstance(StatusEffects.LUCK, 200) : 행운 효과, 200틱(10초)
     * - 0.25f (chance) : 25%의 확률로 행운 효과 부여
     */
    public static final FoodComponent STRAWBERRY =
            new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 200), 0.25f).build();
}
