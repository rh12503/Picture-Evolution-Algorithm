package pea.runners.converter;

import javafx.scene.paint.Color;
import pea.util.SimpleColor;
import picocli.CommandLine;

public class SimpleColorConverter implements CommandLine.ITypeConverter<SimpleColor> {
    @Override
    public SimpleColor convert(String s) throws Exception {
        Color color = Color.valueOf(s);
        return new SimpleColor((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());
    }
}
