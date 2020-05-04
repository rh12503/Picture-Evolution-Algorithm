package pea.main;

import pea.canvas.DrawingCanvas;
import pea.canvas.aparapi.AparapiCanvas;
import pea.canvas.g2d.Graphics2DCanvas;
import pea.util.SimpleColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ExperimentRun {
    public static void main(String[] args) {
        AparapiCanvas CLCanvas = new AparapiCanvas(100, 100);
        renderTest(CLCanvas);
        System.out.println(saveImage(CLCanvas.toImage(), "CL"));

        Graphics2DCanvas g2dCanvas = new Graphics2DCanvas(100, 100);
        renderTest(g2dCanvas);
        System.out.println(saveImage(g2dCanvas.toImage(), "g2d"));
    }

    private static boolean saveImage(BufferedImage canvas, String name) {
        try {
            ImageIO.write(canvas, "png", new File(name + ".png"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static void renderTest(DrawingCanvas canvas) {

        //int d = Math.min(canvas.getWidth(), canvas.getHeight());

        Random random = new Random(777);
        canvas.begin();
        canvas.background(new SimpleColor(1, 0, 0));
        for (int i = 0; i < 100; i++) {
            canvas.fill(new SimpleColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()));
            //canvas.circle(random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight(), random.nextFloat() * canvas.getHeight() / 2f);
            canvas.fill(new SimpleColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()));
            //canvas.rect(random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight(), random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight());

            canvas.fill(new SimpleColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()));
            //canvas.ellipse(random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight(), random.nextFloat() * canvas.getWidth() / 2f, random.nextFloat() * canvas.getHeight() / 2f);

            canvas.fill(new SimpleColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()));
            //canvas.line(random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight(), random.nextFloat() * canvas.getWidth(), random.nextFloat() * canvas.getHeight(), 20);

            canvas.fill(new SimpleColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()));
            float[] x = new float[5];
            float[] y = new float[5];
            for (int j = 0; j < x.length; j++) {
                x[j] = random.nextFloat() * canvas.getWidth();
                y[j] = random.nextFloat() * canvas.getHeight();
            }
           // canvas.polygon(x, y);

        }
        canvas.end();
    }
}
