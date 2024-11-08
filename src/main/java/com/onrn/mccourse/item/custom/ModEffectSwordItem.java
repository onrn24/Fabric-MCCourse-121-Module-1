package com.onrn.mccourse.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffectSwordItem extends SwordItem {
    // 이 레지스트리 항목은 StatusEffect를 Minecraft에 추가하거나 조작할 때 필요하며,
    // 레지스트리를 통해 새로운 효과를 정의하거나, 기존 효과와 상호작용할 수 있도록 해줍니다.
    private final RegistryEntry<StatusEffect> effect;

    public ModEffectSwordItem(ToolMaterial toolMaterial, Settings settings, RegistryEntry<StatusEffect> effect) {
        super(toolMaterial, settings);
        this.effect = effect;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // duration 200 tick -> 10 s
        target.addStatusEffect(new StatusEffectInstance(effect, 200, 1), attacker);
        return super.postHit(stack, target, attacker);
    }
}
