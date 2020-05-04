package pea.ga.acceptance;

import pea.ga.World;

import java.io.Serializable;

public interface AcceptanceStrategy extends World.Observer, Serializable {
    boolean accept(double fitnessOld, double fitnessNew);
}
