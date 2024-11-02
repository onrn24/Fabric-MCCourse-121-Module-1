package com.onrn.mccourse.item.custom;

import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

/**
 * Advanced Item
 * - extends Item
 *   - middle mouse button
 *   - control left clicking
 *   - ...
 */
public class ChainsawItem extends Item {

    public ChainsawItem(Settings settings) {
        super(settings);
    }

    /**
     * 블록에 아이템을 사용했을 때
     */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        // meaning : we are on the server >>  only happens server -side.
        if(!world.isClient()) {
            /**
             * we want to check
             * is the block that we're right now right -clicking actually logs?
             * 마우스 우클릭한 블록이 실제로 로깅되고 있는가?
             *
             * BlockTags.LOGS : 나무 블록 그룹화 태그
             */
            if(world.getBlockState(context.getBlockPos()).isIn(BlockTags.LOGS)) {
                // break
                // context.getPlayer >> 어떤 플레이어, 어떤 엔티티가 이 블록을 파괴했는지
                world.breakBlock(context.getBlockPos(), true, context.getPlayer());

                // 아이템 사용 후 내구도 손상 주기
                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> {
                                // Objects.requireNonNull() 은 굳이 필요하지는 않음 >> warning 제거용
                                Objects.requireNonNull(context.getPlayer())
                                        .sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND);
                        });
            }
        }

        // 아이템을 들고 용광로(furnace)를 클릭한다면 용광로를 여는 메서드도 호출될 것이다.
        // 이때 무엇을 사용하고 싶은지, 무엇을 하고 싶은지 알기 위해서
        // ActionResult가 필요하다.
        return ActionResult.CONSUME;
    }

    /**
     * 아이템에 설명(tooltip) 추가
     */
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip,
            TooltipType type) {
        /**
         * 키 Shift 누르는 지 검사
         */
        if(!Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.mccourse.chainsaw.tooltip.shift"));
        } else {
            // '\n' 대신 tooltip.add()를 나눠서 줄 바꿈을 한다.
            tooltip.add(Text.translatable("tooltip.mccourse.chainsaw.tooltip.1"));
            tooltip.add(Text.translatable("tooltip.mccourse.chainsaw.tooltip.2"));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
