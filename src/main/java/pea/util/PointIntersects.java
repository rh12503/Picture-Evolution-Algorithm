package pea.util;

public class PointIntersects {
    public static boolean circle(float x, float y, float cX, float cY, float cR) {
        float dx = cX - x;
        float dy = cY - y;

        return dx * dx + dy * dy < cR * cR;
    }

    public static boolean line(float x, float y, float sX, float sY, float eX, float eY, float epsilon) {
        final float xDiff = eX - sX;
        final float yDiff = eY - sY;
        float length2 = xDiff * xDiff + yDiff * yDiff;
        if (length2 == 0) {
            return inEpsilon(sX, sY, x, y, epsilon);
        }
        float t = ((x - sX) * (eX - sX) + (y - sY) * (eY - sY)) / length2;
        if (t < 0) {
            return inEpsilon(sX, sY, x, y, epsilon);
        }
        if (t > 1) {
            return inEpsilon(eX, eY, x, y, epsilon);
        }
        return inEpsilon(sX + t * (eX - sX), sY + t * (eY - sY), x, y, epsilon);
    }

    private static boolean inEpsilon(float x, float y, float x2, float y2, float epsilon) {
        float dx = x - x2;
        float dy = y - y2;
        return dx * dx + dy * dy < epsilon * epsilon;
    }

    public static boolean ellipse(float x, float y, float eX, float eY, float rX, float rY) {
        float dX = x - eX;
        float dY = y - eY;
        return (dX * dX) * (rY * rY) + (dY * dY) * (rX * rX) <= 1 * (rX * rX) * (rY * rY);
    }

    public static boolean polygon(float x, float y, float[] vertices, int start, int vertexCount) {
        float lastX = vertices[start + vertexCount - 1];
        float lastY = vertices[start + vertexCount - 1 + vertexCount];
        boolean oddNodes = false;
        for (int i = 0; i < vertexCount; i++) {
            float vX = vertices[start + i];
            float vY = vertices[start +  i + vertexCount];
            if ((vY < y && lastY >= y) || (lastY < y && vY >= y)) {
                if (vX + (y - vY) / (lastY - vY) * (lastX - vX) < x) oddNodes = !oddNodes;
            }
            lastX = vX;
            lastY = vY;
        }
        return oddNodes;
    }

    public static boolean rect(float x, float y, float rX, float rY, float w, float h) {
        return x > rX && x < rX + w && y > rY && y < rY + h;
    }
}
