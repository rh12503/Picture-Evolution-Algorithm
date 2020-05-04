package pea.runners.converter;

import pea.ga.ShapeFactory;
import pea.shapes.*;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactoryConverter implements CommandLine.ITypeConverter<ShapeFactory> {
    private static final Map<String, ShapeFactory> shapeFactories = new HashMap<>();

    static {
        registerFactory("circle", Circle::new);
        registerFactory("ellipse", Ellipse::new);
        registerFactory("circle", Circle::new);
        registerFactory("rectangle", Rectangle::new);
        registerFactory("square", Square::new);
        registerFactory("stroke", Stroke::new);
    }

    @Override
    public ShapeFactory convert(String s) throws Exception {
        if (s.contains("polygon")) {
            String num = s.replace("polygon", "");
            int vertices = Integer.valueOf(num);
            return (ShapeFactory) () -> new Polygon(vertices);
        }

        return shapeFactories.get(s.toLowerCase());
    }

    public static void registerFactory(String name, ShapeFactory factory) {
        shapeFactories.put(name, factory);
    }
}