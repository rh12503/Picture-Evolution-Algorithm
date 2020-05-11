package pea.ga.acceptance;

import pea.ga.World;

public class HillClimbingStrategy implements AcceptanceStrategy {
    @Override
    public boolean accept(double fitnessOld, double fitnessNew) {
        return fitnessNew > fitnessOld;
    }

    @Override
    public void update(World world) {
    }
}
