package personal.catboardbeta.morebombs.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import personal.catboardbeta.morebombs.Morebombs;
import personal.catboardbeta.morebombs.common.entities.GrenadeEntity;
import personal.catboardbeta.morebombs.common.entities.TNT10xEntity;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {

    public static List<EntityType<?>> entities = new ArrayList<>();

    public static final EntityType<TNT10xEntity>  TNT10X  = createEntity("tnt_10x", EntityType.Builder.<TNT10xEntity>of(TNT10xEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).updateInterval(10).build("tnt_10x"));
    public static final EntityType<GrenadeEntity> GRENADE = createEntity("grenade", EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, EntityClassification.MISC).sized(1F, 1F).clientTrackingRange(4).updateInterval(10).build("grenade"));


    public static <E extends Entity, ET extends EntityType<E>> ET createEntity(String id, ET entityType) {
        entityType.setRegistryName(new ResourceLocation(Morebombs.MODID, id));
        entities.add(entityType);
        return entityType;
    }

    public static void init() {
    }
}
