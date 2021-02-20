package me.anvaru.hotelcalculator;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class Apartment {
    public final BlockState state;
    public final BlockPos pos;
    public final World world;

    public Apartment(BlockPos pos, World world) {
//        this.state = world.getBlockState(pos);
//        this.pos = pos;
//        this.world = world;
        this(pos, world, world.getBlockState(pos));
    }

    public Apartment(BlockPos pos, World world, BlockState state) {
        this.state = state;
        this.pos = pos;
        this.world = world;
    }

    public boolean getWindow(Direction dir) {
        return HotelCalc.getWindow(this, dir);
    }

    public int getWindows() {
        return HotelCalc.getWindows(this);
    }

    public boolean getRoof() {
        return HotelCalc.getRoof(this);
    }

    public boolean getGround() {
        return HotelCalc.getGround(this);
    }

    public int getIncome() {
        return HotelCalc.getIncome(this);
    }

    public int getLoss() {
        return HotelCalc.getLoss(this);
    }

    public int getProfit() {
        return HotelCalc.getProfit(this);
    }

    public boolean isValid() {
        return HotelCalc.isValid(this);
    }

    @Nullable
    public Apartment getConnected(Direction dir) {
        BlockPos pos1 = HotelCalc.addInDirection(pos, dir);
        BlockState state = world.getBlockState(pos1);
//        System.out.println(state.getBlock().toString());
        if(state.getBlock().equals(BlockRegister.APT_BLOCK)) {
            return new Apartment(pos1, world);
        } else {
            return null;
        }
    }

    public HashMap<Direction, Apartment> getConnected(List<Direction> blacklist) {
        HashMap<Direction, Apartment> dirs = new HashMap<>();
        for(Direction direction : Direction.values()) {
            if(blacklist.contains(direction)) continue;
            Apartment connected = getConnected(direction);
//            System.out.println(connected == null);
            if(connected != null) {
                dirs.put(direction, connected);
            }
        }
        return dirs;
    }
}
