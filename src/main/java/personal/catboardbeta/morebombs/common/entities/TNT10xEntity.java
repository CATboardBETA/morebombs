package personal.catboardbeta.morebombs.common.entities;

import net.minecraft.entity.Entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import personal.catboardbeta.morebombs.core.ModEntities;
import personal.catboardbeta.morebombs.util.ExplosionFactory;

import javax.annotation.Nullable;

import personal.catboardbeta.morebombs.util.ExplosionFactory.ExplosionType;

public class TNT10xEntity extends Entity {

    private LivingEntity owner;

    public TNT10xEntity(EntityType<? extends TNT10xEntity> type, World world) {
        super(type, world);
    }

    public TNT10xEntity(World world, double x, double y, double z, @Nullable LivingEntity lighter) {
        this(ModEntities.TNT10X, world);
        this.setPos(x, y, z);
        double d0 = world.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = lighter;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        LOGGER.info("Entered TNT10xEntity::tick()");

        ExplosionFactory es = new ExplosionFactory(this.level, this);
        es.setCoords(this.getOnPos()).setExplosionType(ExplosionType.CLEAR).setRadius(4);
        es.explode();
        this.remove();
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {

    }
}
