package pea.runners.util;

import pea.canvas.CanvasFactory;
import pea.ga.ShapeFactory;
import pea.ga.picture.Picture;
import pea.ga.picture.PictureFactory;
import pea.util.SimpleColor;

public interface PictureCreator {
    public PictureFactory newPicture(int shapeCount, SimpleColor backgroundColor, ShapeFactory shapeFactory);
}
