package pea.util;

import java.io.Serializable;

public class SimpleColor implements Serializable {
    public float r = 0;
    public float g = 0;
    public float b = 0;
    public float a = 0;

    public SimpleColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public SimpleColor(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public SimpleColor() {
        this(0, 0, 0);
    }

    @Override
    public String toString() {
        return "r: "+ r + ", g: " + g + ", b: " + b + ", a: " + a;
    }
}
