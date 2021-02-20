package me.anvaru.hotelcalculator;

import me.anvaru.hotelcalculator.blocks.AptBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class HotelCalc {
    public static BlockPos addInDirection(BlockPos pos, Direction dir, int by) {
        return pos.offset(dir, by);
    }

    public static BlockPos addInDirection(BlockPos pos, Direction dir) {
        return addInDirection(pos, dir, 1);
    }

    public static BlockState getBlockInDirection(BlockPos pos, Direction dir, World world, int by) {
        return world.getBlockState(addInDirection(pos, dir, by));
    }

    public static BlockState getBlockInDirection(BlockPos pos, Direction dir, World world) {
        return getBlockInDirection(pos, dir, world, 1);
    }

    public static boolean getWindow(Apartment block, Direction dir) {
        if(dir.equals(Direction.UP) || dir.equals(Direction.DOWN)) {
            return false;
        }
        return getBlockInDirection(block.pos, dir, block.world).isAir();
    }

    public static int getWindows(Apartment block) {
        int windows = 0;
        for(Direction dir : Direction.values()) if (getWindow(block, dir)) windows++;
        return windows;
    }

    public static boolean getRoof(Apartment block) {
        return getBlockInDirection(block.pos, Direction.UP, block.world).isAir();
    }

    public static boolean getGround(Apartment block) {
        BlockState state = getBlockInDirection(block.pos, Direction.DOWN, block.world);
       return !state.isAir() && !state.getBlock().equals(BlockRegister.APT_BLOCK);
    }

    public static int getIncome(Apartment block) {
        final int w = block.getWindows();
        final boolean r = block.getRoof();
        if(r && w == 4) {
            return 600;
        } else if(w == 4) {
            return 500;
        } else if(r && w == 3) {
            return 300;
        } else if(w == 3) {
            return 250;
        } else if(r && w == 2) {
            return 200;
        } else if(w == 2) {
            return 175;
        } else if(r && w == 1) {
            return 150;
        } else if(w == 1) {
            return 125;
        } else {
            return 0;
        }
    }

    public static int getLoss(Apartment block) {
        return (getRoof(block) ? 10 : 0) + getWindows(block) * 5;
    }

    public static int getProfit(Apartment block) {
        return getIncome(block) - getLoss(block);
    }

    public static boolean isValid(Apartment block) {
        return block.getWindows() != 0;
    }
}
