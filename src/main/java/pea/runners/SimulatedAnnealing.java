package pea.runners;

import pea.ga.ShapeFactory;
import pea.ga.World;
import pea.ga.acceptance.SimulatedAnnealingStrategy;
import pea.ga.picture.DefaultEvolvingPicture;
import pea.io.WorldImageWriter;
import pea.runners.converter.PictureCreatorConverter;
import pea.runners.converter.ShapeFactoryConverter;
import pea.runners.converter.SimpleColorConverter;
import pea.runners.util.PictureCreator;
import pea.util.PreviewWindow;
import pea.util.SimpleColor;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import static picocli.CommandLine.Option;

@Command(name = "Run Simulated Annealing PEA", mixinStandardHelpOptions = true, version = "SARunner 1.0")
public class SimulatedAnnealing implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(SimulatedAnnealing.class.getName());

    @Option(names = {"-i", "-image"}, description = "The image file to input for the algorithm", required = true)
    private File imageFile;

    @Option(names = {"-r", "-result"}, description = "The result path to save the image to", required = true)
    private Path resultPath;

    @Option(names = {"-ot", "-outputTime"}, description = "How often an output image should be written in seconds")
    private float outputTime = 120;

    @Option(names = {"-computeMethod", "-cm"}, description = "The compute method to used for the genetic algorithm. Either GPU or CPU")
    private PictureCreator computeMethod = PictureCreatorConverter.get("CPU");

    // Simulated Annealing Strategy

    @Option(names = {"-temperature", "-t"}, description = "The mutation rate of the shapes")
    private double startingTemperature = 2;
    @Option(names = {"-decrease", "-d"}, description = "The mutation rate of the shapes")
    private double decrease = 0.999999;

    // World Builder options
    @Option(names = {"-mutationRate", "-mr"}, description = "The mutation rate of the shapes")
    private float mutationRate = World.Builder.DEFAULT_MUTATION_RATE;

    @Option(names = {"-mutationAmount", "-ma"}, description = "The amount the shapes are mutated")
    private float mutationAmount = World.Builder.DEFAULT_MUTATION_AMOUNT;

    // Picture Builder options
    @Option(names = {"-background", "-b"}, description = "The background color")
    private SimpleColor backgroundColor = DefaultEvolvingPicture.Builder.DEFAULT_BACKGROUND_COLOR;
    @Option(names = {"-shapeCount", "-sc"}, description = "The number the shapes used in the picture")
    private int shapeCount = DefaultEvolvingPicture.Builder.DEFAULT_SHAPE_COUNT;
    @Option(names = {"-shape", "-s"}, description = "The type of shape to use")
    private ShapeFactory shapeFactory = DefaultEvolvingPicture.Builder.DEFAULT_SHAPE_FACTORY;

    @Option(names = {"-preview", "-p"}, description = "Whether to open a preview window")
    private boolean preview;

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new SimulatedAnnealing());
        commandLine.registerConverter(SimpleColor.class, new SimpleColorConverter());
        commandLine.registerConverter(ShapeFactory.class, new ShapeFactoryConverter());
        commandLine.registerConverter(PictureCreator.class, new PictureCreatorConverter());
        commandLine.execute(args);
    }

    @Override
    public void run() {
        final BufferedImage image;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            LOGGER.severe("Error reading image");
            return;
        }
        World world = new World.Builder(image)
                .mutationAmount(mutationAmount)
                .mutationRate(mutationRate)
                .pictureFactory(computeMethod.newPicture(shapeCount, backgroundColor, shapeFactory)).acceptanceStrategy(new SimulatedAnnealingStrategy(startingTemperature, decrease))
                .build();
        world.setRandom();
        if (preview) {
            world.registerObserver(new PreviewWindow(world));
        }
        world.registerObserver(new WorldImageWriter(outputTime, resultPath));
        while (true) {
            world.step();
        }
    }
}
