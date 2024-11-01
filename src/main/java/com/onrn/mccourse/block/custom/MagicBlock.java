package com.onrn.mccourse.block.custom;

import com.onrn.mccourse.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * - 우클릭 시 소리 남
 * - 매직 블록 위에 특정 아이템을 올리면 다른 아이템으로 바뀜
 */
public class MagicBlock extends Block {

    public MagicBlock(Settings settings) {
        super(settings);
    }

    /**
     * 마우스 우클릭(또는 상호작용)했을 때 호출
     * - 작업대나 상자처럼 우클릭할 때 특정 GUI가 열리거나
     * - 블록의 상태가 변경되는 등의 행동 정의
     */
    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
            BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1f, 1f);
        return ActionResult.SUCCESS;
    }

    /**
     * 플레이어나 엔티티가 특정 블록위를 밟았을 때 호출
     * - 블록 위를 지나갈 때 발생하는 행동 정의
     * - 밟히거나 위를 걸을 때 효과줄 수 있음
     * - 블록 밟았을 때 속도 감소, 데미지 줌, 특정 효과 발생 등의 행동 구현
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        /**
         * 블록 위에 올려진 entity를 필터링함
         * - ItemEntity 인가?
         * - FLUORITE or RAW_FLUORITE or COAL 인가?
         */
        if(entity instanceof ItemEntity item) {
            if(isValidItem(item.getStack())) {
                item.setStack(new ItemStack(Items.DIAMOND, item.getStack().getCount()));
            }
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    private boolean isValidItem(ItemStack stack) {
        return stack.getItem() == ModItems.FLUORITE
                || stack.getItem() == ModItems.RAW_FLUORITE
                || stack.getItem() == Items.COAL;
    }
}
