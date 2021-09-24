package personal.catboardbeta.morebombs.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.entity.effect.LightningBoltEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TNTLightning extends Block {

    private World world;

    private BlockPos loc;
    private PlayerEntity owner;

    public TNTLightning(AbstractBlock.Properties p_i48309_1_) {
        super(p_i48309_1_);
    }

    public ActionResultType use(BlockState state, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        this.loc = blockPos;
        this.world = world;
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(state, world, blockPos, player, hand, result);
        } else {
            setOwner(player);
            LightningBoltEntity lbe = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
            BlockPos wts = whereToStrike();
            lbe.setPos(wts.getX(), wts.getY(), wts.getZ());
            world.addFreshEntity(lbe);
            world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
            if (!player.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, player, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(hand);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return ActionResultType.sidedSuccess(world.isClientSide);
        }
    }

    public void setOwner(PlayerEntity player) {
        owner = player;
    }

    public PlayerEntity getOwner() {
        return owner;
    }

    public BlockPos whereToStrike() {
        AxisAlignedBB aabb = new AxisAlignedBB(loc).inflate(7.5F);
        List<LivingEntity> t = world.getEntitiesOfClass(LivingEntity.class, aabb);
        int randEntity = new Random().nextInt(t.size());
        LivingEntity e = t.get(randEntity);

        BlockPos bp;
        if (!(e instanceof PlayerEntity)) {
            bp = new BlockPos(e.blockPosition());
        } else if (e == this.getOwner()) {
            bp = this.loc;
        } else {
            bp = new BlockPos(e.blockPosition());
        }

        return bp;
    }
}
