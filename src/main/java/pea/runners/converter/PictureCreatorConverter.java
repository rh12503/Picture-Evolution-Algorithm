package pea.runners.converter;

import pea.canvas.g2d.Graphics2DCanvas;
import pea.ga.picture.AparapiPicture;
import pea.ga.picture.DefaultEvolvingPicture;
import pea.runners.util.PictureCreator;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class PictureCreatorConverter implements CommandLine.ITypeConverter<PictureCreator> {
    private static final Map<String, PictureCreator> pictureCreators = new HashMap<>();

    static {
        registerFactory("GPU", (count, color, shape) -> (w, h) -> new AparapiPicture(w, h, count, color, shape));
        registerFactory("CPU", (count, color, shape) -> (w, h) -> new DefaultEvolvingPicture(w, h, Graphics2DCanvas::new, count, color, shape));
    }


    public static void registerFactory(String name, PictureCreator creator) {
        pictureCreators.put(name, creator);
    }

    @Override
    public PictureCreator convert(String s) throws Exception {
        return get(s);
    }

    public static PictureCreator get(String s) {
        return pictureCreators.get(s.toUpperCase());
    }

}
