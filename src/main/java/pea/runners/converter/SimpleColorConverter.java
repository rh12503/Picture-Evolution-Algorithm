package pea.runners.converter;

import pea.util.SimpleColor;
import picocli.CommandLine;

import java.awt.*;
import java.lang.reflect.Field;

public class SimpleColorConverter implements CommandLine.ITypeConverter<SimpleColor> {
    @Override
    public SimpleColor convert(String s) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Color color;
       Field field = Class.forName("java.awt.Color").getField(s);
        color = (Color) field.get(null);
        return new SimpleColor((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue());
    }
}
