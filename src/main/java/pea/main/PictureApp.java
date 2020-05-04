package pea.main;

import pea.canvas.aparapi.AparapiCanvas;
import pea.canvas.fastaparapi.FastAparapiCanvas;
import pea.canvas.g2d.Graphics2DCanvas;
import pea.ga.Image;
import pea.ga.World;
import pea.ga.acceptance.HillClimbingStrategy;
import pea.ga.acceptance.SimulatedAnnealingStrategy;
import pea.ga.picture.AparapiPicture;
import pea.ga.picture.DefaultEvolvingPicture;
import pea.ga.picture.Picture;
import pea.io.WorldDataWriter;
import pea.io.WorldImageWriter;
import pea.pixelbuffer.PixelBufferShort;
import pea.shapes.Ellipse;
import pea.shapes.Rectangle;
import pea.util.ImageUtil;
import pea.util.SimpleColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class PictureApp {
    private final static Logger LOGGER = Logger.getLogger(PictureApp.class.getName());

    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File(args[0]));

        /*Supplier<Random> randomSupplier = new Supplier<Random>() {
            Random random = new Random(777);

            @Override
            public Random get() {
                return random;
            }
        };*/

        World world = new World.Builder(image).pictureFactory((w, h) -> new AparapiPicture.Builder(w, h).shapeFactory(Rectangle::new).build()).acceptanceStrategy(new SimulatedAnnealingStrategy(2, 0.999999)).build();
        world.setRandom();

        world.registerObserver(new WorldImageWriter(120, Paths.get("result.png")));
        //world.registerObserver(new WorldDataWriter(250, Paths.get("result.ser")));
        //new PreviewWindow(world);

        while (true) {
            world.step();
        }
    }
}
