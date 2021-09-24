package personal.catboardbeta.morebombs.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class TNTPig extends Block {

    private World world;
    private BlockPos loc;
    private PlayerEntity owner;

    public TNTPig(AbstractBlock.Properties p_i48309_1_) {
        super(p_i48309_1_);
    }

    public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        this.world = world;
        this.loc = blockPos;
        this.owner = player;

        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(state, world, blockPos, player, hand, result);
        } else {
            this.owner = player;
            PigEntity pig = new PigEntity(EntityType.PIG, world);
            pig.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            world.addFreshEntity(pig);
            world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 1);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, (items) -> {
                        items.broadcastBreakEvent(hand);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        }
    }
}
