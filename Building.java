package me.anvaru.hotelcalculator;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Building {
    // first = root

    public final List<Apartment> blocks;

    public Building(List<Apartment> blocks) {
        this.blocks = blocks;
    }

    public Building(BlockPos pos, World world) {
        Apartment first = new Apartment(pos, world);
//        blocks.add(first);
        blocks = getConnected(first);
        if(blocks.stream().noneMatch(apartment -> apartment.pos.equals(first.pos))) {
            blocks.add(first);
        }
//        for(Apartment block : blocks) {
//            System.out.println(block.pos);
//        }
//        System.out.println(getGround().size());
    }

    private static List<Apartment> getConnected(Apartment first) {
        return getConnected(new ArrayList<>(), new HashMap<>(), first);
    }

    private static List<Apartment> getConnected(List<Apartment> apts, HashMap<Apartment, List<Direction>> ignore, Apartment apt) {
        HashMap<Direction, Apartment> a = apt.getConnected(ignore.getOrDefault(apt, new ArrayList<>()));
//        System.out.println(apts);
        for(Direction b : a.keySet()) {
            Apartment c = a.get(b);
//            System.out.println(apts.contains(c));
            if(apts.stream().anyMatch(apartment -> apartment.pos.equals(c.pos))) continue;
            List<Direction> d = ignore.getOrDefault(c, new ArrayList<>());
            d.add(b.getOpposite());
            ignore.put(c, d);
            apts.add(c);
            getConnected(apts, ignore, c);
        }
        return apts;
    }

    public Building(Apartment first) {
        this(first.pos, first.world);
    }

    public boolean areBlocksValid() {
        return blocks.stream().allMatch(Apartment::isValid);
    }

    public Text getInvalidReason() {
        if(!areBlocksValid()) {
            return new LiteralText("Some blocks don't have windows");
        } else if(getGround().size() <= 0) {
            return new LiteralText("There are no blocks touching the ground");
        }
        return null;
    }

    public boolean isValid() {
        return areBlocksValid() && getGround().size() > 0;
    }

    public List<Apartment> getGround() {
        return blocks.stream().filter(Apartment::getGround).collect(Collectors.toList());
    }

    public int getIncome() {
        return getIncomeForApts();
    }

    public double getLoss() {
        return getLossForApts() + getSpaceLoss() + getHeightLoss();
    }

    public double getProfit() {
        return getIncome() - getLoss();
    }

    public int getIncomeForApts() {
        return blocks.stream().mapToInt(Apartment::getIncome).sum();
    }

    public int getLossForApts() {
        return blocks.stream().mapToInt(Apartment::getLoss).sum();
    }

    public int og() {
        return (int) Math.pow(getGround().size(), 2);
    }

    public int getSpaceLoss() {
        return og() * 400;
    }

    public double getHeightLoss() {
//        System.out.println("hld" + hld());
//        System.out.println("og" + og());
//        System.out.println("heightloss" + (og()*(hld()/100.0f)));
        // sadly cuz of computers being horrible at decimals, the height loss gets messed up and makes everything ugly,
        // but im too lazy to fix it lol
        return getSpaceLoss()*(hld()/100.0f);
    }

    public double hld() {
        int height = getHeight();
//        System.out.println("height" + height);
        if(height <= 10) {
            return 50;
        } else if(height <= 20) {
            return 1000;
        } else if(height <= 30) {
            return 2000;
        } else if(height <= 40) {
            return 3000;
        } else {
            return 5000;
        }
    }

    public IntStream getHeights() {
        return blocks.stream().mapToInt(apt -> apt.pos.getY());
    }

    public int getMaxHeight() {
        return getHeights().max().getAsInt();
    }

    public int getMinHeight() {
        return getHeights().min().getAsInt();
    }

    public int getHeight() {
        return getMaxHeight() - getMinHeight() + 1;
    }

    public int getWindows() {
        return blocks.stream().mapToInt(Apartment::getWindows).sum();
    }

    public int getRoofs() {
        return (int) blocks.stream().filter(Apartment::getRoof).count();
    }
}
