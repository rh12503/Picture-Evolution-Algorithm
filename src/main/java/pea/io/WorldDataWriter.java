package pea.io;

import pea.ga.World;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class WorldDataWriter implements World.Observer {

    private final static Logger LOGGER = Logger.getLogger(WorldDataWriter.class.getName());

    private int increment;
    private Path result;

    public WorldDataWriter(int increment, Path result) {
        this.increment = increment;
        this.result = result;
    }

    @Override
    public void update(World world) {
        if (world.getGeneration() % increment == 0) {
            System.out.println("Saved to: " + result.toString() + " | Generation: " + world.getGeneration() + " | Fitness: "
                    + world.getPictureFitness() + " | Time: " + world.getElapsedTime());
            try {
                WorldIO.write(result, world);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.severe("Error writing world to: " + result.toString());
                LOGGER.info(e.getMessage());
            } catch (ClassNotFoundException e) {
                LOGGER.severe("World class not found while writing world to: " + result.toString());
                LOGGER.info(e.getMessage());
            }

        }
    }
}
