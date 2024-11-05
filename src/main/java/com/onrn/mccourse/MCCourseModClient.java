package com.onrn.mccourse;

import com.onrn.mccourse.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MCCourseModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // - RenderLayer.getCutout()
        // 블록의 렌더링 레이어를 Cutout으로 설정합니다.
        // Cutout 레이어는 반투명한 블록을 렌더링할 때 사용되며,
        // 일반적으로 두께가 있는 블록이나 투명한 요소(예: 유리)와 같은 블록에 적합합니다.
        // 이 레이어에서는 블록이 특정한 투명 효과를 가질 수 있도록 처리됩니다.
        //
        // - 이 코드는 FLUORITE_DOOR 블록이 컷아웃 렌더 계층을 사용하여 렌더링되도록 지정합니다.
        // 이를 통해 해당 블록은 투명한 부분과 불투명한 부분을 정확히 처리하여,
        // 게임 내에서 문처럼 픽셀 단위의 투명한 영역이 제대로 표현
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLUORITE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLUORITE_TRAPDOOR, RenderLayer.getCutout());
    }
}
