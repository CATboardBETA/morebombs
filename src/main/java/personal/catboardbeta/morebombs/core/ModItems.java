package personal.catboardbeta.morebombs.core;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import personal.catboardbeta.morebombs.Morebombs;
import personal.catboardbeta.morebombs.common.items.GrenadeItem;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final ItemGroup BOMB_TAB = new ItemGroup(Morebombs.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.GRENADE);
        }
        
        @Override
        public boolean canScroll() {
            return true;
        }

        @Override
        public ResourceLocation getBackgroundImage() {
            return new ResourceLocation("minecraft", "textures/gui/container/creative_inventory/tab_items.png");
        }
    };
    
    public static List<Item> items = new ArrayList<>();

    // blocks
    public static Item TNT10x        = createItem(new BlockItem(ModBlocks.TNT10X,        new Item.Properties().tab(BOMB_TAB)), Registry.BLOCK.getKey(ModBlocks.TNT10X));
    public static Item TNT_LIGHTNING = createItem(new BlockItem(ModBlocks.TNT_LIGHTNING, new Item.Properties().tab(BOMB_TAB)), Registry.BLOCK.getKey(ModBlocks.TNT_LIGHTNING));
    public static Item LAND_MINE     = createItem(new BlockItem(ModBlocks.LAND_MINE,     new Item.Properties().tab(BOMB_TAB)), Registry.BLOCK.getKey(ModBlocks.LAND_MINE));
    public static Item PIG_TNT       = createItem(new BlockItem(ModBlocks.PIG_TNT,       new Item.Properties().tab(BOMB_TAB)), Registry.BLOCK.getKey(ModBlocks.PIG_TNT));
    public static Item FUSE          = createItem(new BlockItem(ModBlocks.FUSE,          new Item.Properties().tab(BOMB_TAB)), Registry.BLOCK.getKey(ModBlocks.FUSE));

    // grenades
    public static Item GRENADE = createItem(new GrenadeItem(new Item.Properties().tab(BOMB_TAB)), "grenade");

    // crafting
    public static Item EXPLOSIVE_CORE          = createItem(new Item(new Item.Properties().tab(BOMB_TAB)), "explosive_core");
    public static Item EXPLOSIVE_CORE_ELECTRIC = createItem(new Item(new Item.Properties().tab(BOMB_TAB)), "explosive_core_electric");
    public static Item GRENADE_PIN             = createItem(new Item(new Item.Properties().tab(BOMB_TAB)), "grenade_pin");

    public static Item createItem(Item item, String id) {
        return createItem(item, new ResourceLocation(Morebombs.MODID, id));
    }
    
    public static Item createItem(Item item, ResourceLocation id) {
        item.setRegistryName(id);
        items.add(item);
        return item;
    }

    public static void init() {
        // empty
    }
}
