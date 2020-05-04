package pea.ga.acceptance;

import pea.ga.World;

public class HillClimbingStrategy implements AcceptanceStrategy {
    @Override
    public boolean accept(double fitnessOld, double fitnessNew) {
        System.out.println("new " + fitnessNew + " old " + fitnessOld);
        //return fitnessNew > fitnessOld;
        return true;
    }

    @Override
    public void update(World world) {
    }
}
