package personal.catboardbeta.morebombs.util;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;

import static personal.catboardbeta.morebombs.Morebombs.LOGGER;

public class ExplosionFactory {

    public enum ExplosionType {
        CLEAR, BREAK
    }

    private int radius;
    private ExplosionType type;
    private int x;
    private int y;
    private int z;

    private final World world;
    private Entity tileEntity;

    public ExplosionFactory(World world, Entity tile) {
        LOGGER.info("Morebombs: Initiated the ExplosionFactory");
        this.world = world;
        this.setEntity(tile);

        this.x = (int) tileEntity.getX();
        this.y = (int) tileEntity.getY();
        this.z = (int) tileEntity.getZ();
    }

    public ExplosionFactory explode() {
        LOGGER.info("Entered ExplosionFactory::explode()");
        Set<BlockPos> blocksToBlow = toBlow();
        if (tileEntity.level instanceof ServerWorld) {
            LOGGER.info("Entered ExplosionFactory::isClientSide");
            for (BlockPos block : blocksToBlow) {
                LOGGER.info("Entered ExplosionFactory::forloop");
                switch (this.type) {
                    case CLEAR:
                        LOGGER.info("Is Case Clear");
                        world.destroyBlock(block, true, this.getEntity());
                        break;

                    case BREAK:
                        LOGGER.info("Is Case Break");
                        world.destroyBlock(block, true, this.getEntity());
                        BlockState bs = world.getBlockState(block);
                        List<ItemStack> blockDrops = new ArrayList<>();
                        blockDrops.add(bs.getBlock().asItem().getDefaultInstance());
                        for (ItemStack stack : blockDrops) {
                            world.addFreshEntity(new ItemEntity(world, block.getX(), block.getY(), block.getZ(), stack));
                        }
                        break;

                    default:
                        LOGGER.error("Morebombs: Explosion at (" + x + ", " + y + ", " + z + ") has invalid ExplosionType." +
                                "Valid types: " + Arrays.toString(ExplosionType.values()));
                        break;
                }
            }
        }
        return this;
    }

    private Set<BlockPos> toBlow() {
        Set<BlockPos> blocksToBlow = Sets.newHashSet();

        for (int i = x - radius; i <= x + radius; ++i) {
            for (int j = y - radius; j <= y + radius; ++j) {
                for (int k = z - radius; k <= z + radius; ++k) {

                    BlockPos pos = this.getEntity().getEntity().blockPosition();
                    BlockPos block = pos.offset(i, j, k);

                    if (!isDistanceTooMuch(pos, block, radius)) {
                        blocksToBlow.add(block);
                    }
                }
            }
        }
        LOGGER.info(blocksToBlow);

        return blocksToBlow;
    }

    private boolean isDistanceTooMuch(BlockPos startPos, BlockPos endPos, int allowedDistance) {
        int startPosX = startPos.getX();
        int startPosY = startPos.getY();
        int startPosZ = startPos.getZ();

        int endPosX = endPos.getX();
        int endPosY = endPos.getY();
        int endPosZ = endPos.getZ();

        return distance3d(startPosX, startPosY, startPosZ, endPosX, endPosY, endPosZ) <= allowedDistance;
    }

    private int distance3d(int x1, int y1, int z1, int x2, int y2, int z2) {
        return MathHelper.fastFloor(
                Math.sqrt(
                        MathHelper.square(x1 - x2) +
                        MathHelper.square(y1 - y2) +
                        MathHelper.square(z1 - z2)
                ));
    }

    public int round(float x) {
        if (x < MathHelper.fastFloor(x) + 0.5F) {
            return MathHelper.fastFloor(x);
        }
        return MathHelper.ceil(x);
    }

//    private static void handleExplosionDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> dropPositionArray, ItemStack stack, BlockPos pos) {
//        int i = dropPositionArray.size();
//
//        for(int j = 0; j < i; ++j) {
//            Pair<ItemStack, BlockPos> pair = dropPositionArray.get(j);
//            ItemStack itemstack = pair.getFirst();
//            if (ItemEntity.areMergable(itemstack, stack)) {
//                ItemStack itemstack1 = ItemEntity.merge(itemstack, stack, 16);
//                dropPositionArray.set(j, Pair.of(itemstack1, pair.getSecond()));
//                if (stack.isEmpty()) {
//                    return;
//                }
//            }
//        }
//        dropPositionArray.add(Pair.of(stack, pos));
//    }

    //
    // *****************************************************************************************************************
    // Getters/Setters

    public int getRadius() {
        return radius;
    }

    public ExplosionFactory setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public int getX() {
        return x;
    }

    public ExplosionFactory setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public ExplosionFactory setY(int y) {
        this.y = y;
        return this;
    }

    public int getZ() {
        return z;
    }

    public ExplosionFactory setZ(int z) {
        this.z = z;
        return this;
    }

    public ExplosionFactory setCoords(BlockPos coords) {
        this.x = coords.getX();
        this.y = coords.getY();
        this.z = coords.getZ();
        return this;
    }

    public BlockPos getCoords() {
        return new BlockPos(x, y, z);
    }

    public ExplosionType getExplosionType() {
        return type;
    }

    public ExplosionFactory setExplosionType(ExplosionType type) {
        this.type = type;
        return this;
    }

    public Entity getEntity() {
        return tileEntity;
    }

    public ExplosionFactory setEntity(Entity tileEntity) {
        this.tileEntity = tileEntity;
        return this;
    }
}