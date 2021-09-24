package personal.catboardbeta.morebombs.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import personal.catboardbeta.morebombs.core.ModEntities;
import personal.catboardbeta.morebombs.core.ModItems;

public class GrenadeEntity extends ProjectileItemEntity {

    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, World world){
        super(type, world);
    }

    public GrenadeEntity(World world, LivingEntity entity){
        super(ModEntities.GRENADE, entity, world);
    }

    public GrenadeEntity(World world, double x, double y, double z){
        super(ModEntities.GRENADE, x, y, z, world);
    }

    public Item getDefaultItem(){
        return ModItems.GRENADE;
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.EXPLOSION : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte rand) {
        if (rand == 3) {
            IParticleData iparticledata = this.getParticle();
            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(iparticledata, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }



    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.Mode.BREAK);    }

    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Explosion.Mode.BREAK);
        }
            this.remove();
        }
    }


