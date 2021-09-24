package personal.catboardbeta.morebombs.core;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import personal.catboardbeta.morebombs.Morebombs;
import personal.catboardbeta.morebombs.common.blocks.TNT10x;
import personal.catboardbeta.morebombs.common.blocks.TNTLightning;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static List<Block> blocks = new ArrayList<>();

    public static Block TNT10X = createBlock(new TNT10x(
             AbstractBlock.Properties.of(
                    Material.EXPLOSIVE)
                     .instabreak()
                     .sound(SoundType.GRASS)),
            "tnt_10x");

    public static Block TNTLightning = createBlock(new TNTLightning(
            AbstractBlock.Properties.of(
                    Material.EXPLOSIVE)
                    .instabreak()
                    .sound(SoundType.GRASS)),
            "tnt_lightning");

    public static Block createBlock(Block block, String id) {
        block.setRegistryName(new ResourceLocation(Morebombs.MODID, id));
        blocks.add(block);
        return block;
    }

    public static void init() {
        // empty
    }
}
