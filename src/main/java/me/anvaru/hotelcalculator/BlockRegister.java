package me.anvaru.hotelcalculator;

import me.anvaru.hotelcalculator.blocks.AptBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class BlockRegister {
//    public static final Block APT_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final Block APT_BLOCK = reg("apt_block", new AptBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f)));

    public static final String MODID = HotelCalculator.MODID;

    private static Block reg(String name, Block block, BlockItem blockItem) {
        Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier(MODID, name), blockItem);
        return block;
    }

    private static Block reg(String name, Block block) {
        return reg(name, block, new BlockItem(block, new FabricItemSettings().group(ItemGroup.MISC)));
    }

    public static void reg() {}
}
