package com.onrn.mccourse.util;

import com.onrn.mccourse.item.custom.HammerItem;
import java.util.HashSet;
import java.util.Set;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class HammerUsageEvent implements PlayerBlockBreakEvents.Before{
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/c23d117dcd3b3b3408a138716b15507f709494cd/src/main/java/cofh/core/event/AreaEffectEvents.java
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    /**
     * 블록이 캐지는 모든 순간 해당 메서드가 호출됨
     * -> 이는 무한 루프에 빠질 가능성이 있다.
     * -> HARVESTED_BLOCKS와 메서드 내 로직을 통해서 무한 루프에 빠지지 않도록 조건문을 건다.
     * -> if(HARVESTED_BLOCKS.contains(pos)
     */
    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos,
            BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack mainHandItem = player.getMainHandStack();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayerEntity serverPlayer) {
            if(HARVESTED_BLOCKS.contains(pos)) {
                return true;
            }

            // 부셔야 하는 좌표들을 받아옴
            // range 1 -> -1, 0, 1로 세 칸을 의미
            // range 2 -> -2, -1, 0, 1, 2
            for(BlockPos position : HammerItem.getBlocksToBeDestroyed(1, pos, serverPlayer)) {
                // - isCorrectForDrops() : 도구가 특정 블록을 캘 때 적절한 아이템을 드롭하는지 검증하여,
                // 도구와 블록 간의 상호작용을 관리하고,
                // 블록 드롭에 필요한 도구 요구 사항을 적용하는 데 중요한 역할
                if(pos == position || !hammer.isCorrectForDrops(mainHandItem, world.getBlockState(position))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(position);
                // 해당 작업(mining) 시 beforeBlockBreak() 가 다시 호출되는데
                // 이때 중복을 없애기 위해 HARVESTED_BLOCKS를 검사한다.
                serverPlayer.interactionManager.tryBreakBlock(position);
                // 삭제하지 않으면 해당 좌표의 블록은 다음부터 없앨 수 없음
                HARVESTED_BLOCKS.remove(position);
            }
        }

        return true;
    }
}
