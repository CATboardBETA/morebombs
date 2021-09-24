package personal.catboardbeta.morebombs.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class LandMine extends Block {

    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);

    public LandMine(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            world.explode(entity, pos.getX(), pos.getY(), pos.getZ(), 2.0F, Explosion.Mode.BREAK);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
            entity.hurt(DamageSource.badRespawnPointExplosion(), 5.0F);
            super.stepOn(world, pos, entity);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

}
