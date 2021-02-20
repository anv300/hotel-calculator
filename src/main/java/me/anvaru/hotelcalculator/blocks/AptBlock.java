package me.anvaru.hotelcalculator.blocks;

import me.anvaru.hotelcalculator.Building;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AptBlock extends Block {
    public AptBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && player.getMainHandStack().getItem().equals(Items.STICK)) {
            Building building = new Building(pos, world);
//        System.out.println(building.blocks.size());
//        System.out.println(building.getProfit());
            String se = "\n|=====================|\n";
            StringBuilder b = new StringBuilder(se);
            b.append("\nhld: " + building.hld() + "%");
            b.append("\nog: " + building.og());
            b.append("\nOpen windows: " + building.getWindows());
            b.append("\nBlocks touching ground: " + building.getGround().size());
            b.append("\nHeight: " + building.getHeight());
            b.append("\nBlocks: " + building.blocks.size());
            b.append("\nRoofs: " + building.getRoofs());
            b.append("\nIncome: " + building.getIncome());
            b.append("\nLoss: " + building.getLoss());
            b.append("\nProfit: " + building.getProfit());
            if(!building.isValid()) {
                b.append("\nInvalid building! " + building.getInvalidReason().getString());
            }
            b.append(se);
            player.sendMessage(new LiteralText(b.toString()), false);
        }
        if(player.getMainHandStack().getItem().equals(Items.STICK)) {
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, world, pos, player,hand, hit);
        }
    }
}
