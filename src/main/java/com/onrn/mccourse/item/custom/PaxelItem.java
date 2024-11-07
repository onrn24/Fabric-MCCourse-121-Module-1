package com.onrn.mccourse.item.custom;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.onrn.mccourse.util.ModTags;
import com.onrn.mccourse.util.ModTags.Blocks;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

/**
 * extends MiningToolItem (ctrl + h) >> pickaxe, shovel, hoe, axe
 */
public class PaxelItem extends MiningToolItem {
    // from ShovelItem
    protected static final Map<Block, BlockState> PATH_STATES = Maps.<Block, BlockState>newHashMap(
            new Builder()
                    .put(net.minecraft.block.Blocks.GRASS_BLOCK, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .put(net.minecraft.block.Blocks.DIRT, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .put(net.minecraft.block.Blocks.PODZOL, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .put(net.minecraft.block.Blocks.COARSE_DIRT, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .put(net.minecraft.block.Blocks.MYCELIUM, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .put(net.minecraft.block.Blocks.ROOTED_DIRT, net.minecraft.block.Blocks.DIRT_PATH.getDefaultState())
                    .build()
    );
    // from AxeItem
    protected static final Map<Block, Block> STRIPPED_BLOCKS = new Builder<Block, Block>()
            .put(net.minecraft.block.Blocks.OAK_WOOD, net.minecraft.block.Blocks.STRIPPED_OAK_WOOD)
            .put(net.minecraft.block.Blocks.OAK_LOG, net.minecraft.block.Blocks.STRIPPED_OAK_LOG)
            .put(net.minecraft.block.Blocks.DARK_OAK_WOOD, net.minecraft.block.Blocks.STRIPPED_DARK_OAK_WOOD)
            .put(net.minecraft.block.Blocks.DARK_OAK_LOG, net.minecraft.block.Blocks.STRIPPED_DARK_OAK_LOG)
            .put(net.minecraft.block.Blocks.ACACIA_WOOD, net.minecraft.block.Blocks.STRIPPED_ACACIA_WOOD)
            .put(net.minecraft.block.Blocks.ACACIA_LOG, net.minecraft.block.Blocks.STRIPPED_ACACIA_LOG)
            .put(net.minecraft.block.Blocks.CHERRY_WOOD, net.minecraft.block.Blocks.STRIPPED_CHERRY_WOOD)
            .put(net.minecraft.block.Blocks.CHERRY_LOG, net.minecraft.block.Blocks.STRIPPED_CHERRY_LOG)
            .put(net.minecraft.block.Blocks.BIRCH_WOOD, net.minecraft.block.Blocks.STRIPPED_BIRCH_WOOD)
            .put(net.minecraft.block.Blocks.BIRCH_LOG, net.minecraft.block.Blocks.STRIPPED_BIRCH_LOG)
            .put(net.minecraft.block.Blocks.JUNGLE_WOOD, net.minecraft.block.Blocks.STRIPPED_JUNGLE_WOOD)
            .put(net.minecraft.block.Blocks.JUNGLE_LOG, net.minecraft.block.Blocks.STRIPPED_JUNGLE_LOG)
            .put(net.minecraft.block.Blocks.SPRUCE_WOOD, net.minecraft.block.Blocks.STRIPPED_SPRUCE_WOOD)
            .put(net.minecraft.block.Blocks.SPRUCE_LOG, net.minecraft.block.Blocks.STRIPPED_SPRUCE_LOG)
            .put(net.minecraft.block.Blocks.WARPED_STEM, net.minecraft.block.Blocks.STRIPPED_WARPED_STEM)
            .put(net.minecraft.block.Blocks.WARPED_HYPHAE, net.minecraft.block.Blocks.STRIPPED_WARPED_HYPHAE)
            .put(net.minecraft.block.Blocks.CRIMSON_STEM, net.minecraft.block.Blocks.STRIPPED_CRIMSON_STEM)
            .put(net.minecraft.block.Blocks.CRIMSON_HYPHAE, net.minecraft.block.Blocks.STRIPPED_CRIMSON_HYPHAE)
            .put(net.minecraft.block.Blocks.MANGROVE_WOOD, net.minecraft.block.Blocks.STRIPPED_MANGROVE_WOOD)
            .put(net.minecraft.block.Blocks.MANGROVE_LOG, net.minecraft.block.Blocks.STRIPPED_MANGROVE_LOG)
            .put(net.minecraft.block.Blocks.BAMBOO_BLOCK, net.minecraft.block.Blocks.STRIPPED_BAMBOO_BLOCK)
            .build();

    public PaxelItem(ToolMaterial material, Settings settings) {
        super(material, Blocks.PAXEL_MINEABLE, settings);
    }

    // from AxeItem, ShovelItem
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity playerEntity = context.getPlayer();

        if (shouldCancelStripAttempt(context)) {
            return ActionResult.PASS;
        } else {
            Optional<BlockState> optional = this.tryStrip(world, blockPos, playerEntity, world.getBlockState(blockPos));
            if (optional.isEmpty()) { // axe 사용하고 있지 않을 때 == shovel 사용 중
                if (context.getSide() != Direction.DOWN) {
                    BlockState blockState2 = (BlockState)PATH_STATES.get(blockState.getBlock());
                    BlockState blockState3 = null;
                    if (blockState2 != null && world.getBlockState(blockPos.up()).isAir()) {
                        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        blockState3 = blockState2;
                    } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean)blockState.get(CampfireBlock.LIT)) {
                        if (!world.isClient()) {
                            world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, blockPos, 0);
                        }

                        CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
                        blockState3 = blockState.with(CampfireBlock.LIT, Boolean.valueOf(false));
                    }

                    if (blockState3 != null) {
                        if (!world.isClient) {
                            world.setBlockState(blockPos, blockState3, Block.NOTIFY_ALL_AND_REDRAW);
                            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState3));
                            if (playerEntity != null) {
                                context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                            }
                        }

                        return ActionResult.success(world.isClient);
                    } else {
                        return ActionResult.PASS;
                    }
                }
                return ActionResult.PASS;
            } else {
                ItemStack itemStack = context.getStack();
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                }

                world.setBlockState(blockPos, (BlockState)optional.get(), Block.NOTIFY_ALL_AND_REDRAW);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, (BlockState)optional.get()));
                if (playerEntity != null) {
                    itemStack.damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
            }
        }

        return ActionResult.success(world.isClient);
    }

    // from AxeItem
    private static boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        return context.getHand().equals(Hand.MAIN_HAND) && playerEntity.getOffHandStack().isOf(
                Items.SHIELD) && !playerEntity.shouldCancelInteraction();
    }

    // from AxeItem
    private Optional<BlockState> tryStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state) {
        Optional<BlockState> optional = this.getStrippedState(state);
        if (optional.isPresent()) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(state);
            if (optional2.isPresent()) {
                world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(player, WorldEvents.BLOCK_SCRAPED, pos, 0);
                return optional2;
            } else {
                Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock()))
                        .map(block -> block.getStateWithProperties(state));
                if (optional3.isPresent()) {
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.syncWorldEvent(player, WorldEvents.WAX_REMOVED, pos, 0);
                    return optional3;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    // from AxeItem
    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable((Block)STRIPPED_BLOCKS.get(state.getBlock()))
                .map(block -> block.getDefaultState().with(PillarBlock.AXIS, (Direction.Axis)state.get(PillarBlock.AXIS)));
    }
}
