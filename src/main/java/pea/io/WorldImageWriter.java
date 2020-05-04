package pea.io;

import pea.ga.World;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

public class WorldImageWriter implements World.Observer {

    private final static Logger LOGGER = Logger.getLogger(WorldImageWriter.class.getName());

    private final float increment;
    private final File result;
    private long lastUpdatedTime;

    public WorldImageWriter(float increment, Path result) {
        this.increment = increment * 1000f;
        this.result = result.toFile();
        lastUpdatedTime = System.currentTimeMillis();
    }

    @Override
    public void update(World world) {
        if (System.currentTimeMillis() - lastUpdatedTime > increment) {
            System.out.println("Saved to: " + result.toString() + " | Generation: " + world.getGeneration() + " | Fitness: "
                    + world.getPictureFitness() + " | Time: " + world.getElapsedTime());
            try {
                ImageIO.write(world.getImage(), "png", result);
            } catch (IOException e) {
                LOGGER.severe("Error writing world to: " + result.toString());
                LOGGER.info(e.getMessage());
            }
            lastUpdatedTime = System.currentTimeMillis();
        }
    }
}
