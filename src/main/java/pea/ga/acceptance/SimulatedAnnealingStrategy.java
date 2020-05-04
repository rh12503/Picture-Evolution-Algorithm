package pea.ga.acceptance;

import pea.ga.World;

public class SimulatedAnnealingStrategy implements AcceptanceStrategy {
    private double temperature;
    private final double decrease;

    public SimulatedAnnealingStrategy(double startingTemperature, double decrease) {
        this.temperature = startingTemperature;
        this.decrease = decrease;
    }

    public SimulatedAnnealingStrategy() {
        this(1, 0.999999);
    }

    @Override
    public boolean accept(double fitnessOld, double fitnessNew) {
        double fOld = 1 - fitnessOld;
        double fNew = 1 - fitnessNew;

        if (fNew < fOld) {
            return true;
        }

        return Math.random() < Math.exp((-(fNew - fOld)) / (temperature));
    }

    @Override
    public void update(World world) {
        temperature *= decrease;
    }

    public double getTemperature() {
        return temperature;
    }
}
