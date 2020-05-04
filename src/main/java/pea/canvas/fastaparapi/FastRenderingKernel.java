package pea.canvas.fastaparapi;


import com.aparapi.Kernel;
import com.aparapi.Range;

public class FastRenderingKernel extends Kernel {

    private static final float[] EMPTY_DATA = {-1};

    @Constant
    private float[] shapeData = null;
    @Constant
    private float[] backgroundData = null;
    @Constant
    private byte[] imageData = null;

    private Range range;
    //private short[] pixels = null;
    private final int width;
    private final int height;

    @Local
    private float[] differenceLocal;

    private float[] difference;

    private double totalDifference;

    private final DataProvider dataProvider;

    public FastRenderingKernel(final DataProvider dataProvider, final int width, final int height) {
        this.dataProvider = dataProvider;
        range = Range.create(width * height);
        this.width = width;
        this.height = height;
        differenceLocal = new float[range.getLocalSize(0)];
        difference = new float[range.getNumGroups(0)];
        setExplicit(true);
    }

    public void execute(byte[] imageData) {
        this.imageData = imageData;
        //this.pixels = pixelBuffer.getPixels();
        this.backgroundData = dataProvider.getBackgroundData();
        this.shapeData = dataProvider.getShapeData();
        put(backgroundData);
        put(shapeData);
        put(difference);
        if (shapeData.length == 0) {
            this.shapeData = EMPTY_DATA;
        }
        this.execute(range);
        get(difference);
        totalDifference = 0;
        for (int i = 0; i < difference.length; i++) {
            totalDifference += difference[i];
        }
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
        get(this.imageData);
        put(this.imageData);
    }

    @Override
    public void run() {
        int i = getGlobalId();

        float x = (i % width) + 0.5f;
        float y = (i / width) + 0.5f;
        float r = backgroundData[0];
        float g = backgroundData[1];
        float b = backgroundData[2];

        int j = 0;
        boolean go = true;
        while (j < shapeData.length && go) {
            int shapeType = (int) shapeData[j];
            if (shapeType != -1) {
                j++;
                boolean intersects = false;

                int in = j;
                j += 4;

                if (shapeType == 0) {
                    intersects = circle(x, y, shapeData[j], shapeData[j + 1], shapeData[j + 2]);
                    j += 3;
                } else if (shapeType == 1) {
                    intersects = rect(x, y, shapeData[j], shapeData[j + 1], shapeData[j + 2], shapeData[j + 3]);
                    j += 4;
                } else if (shapeType == 2) {
                    intersects = ellipse(x, y, shapeData[j], shapeData[j + 1], shapeData[j + 2], shapeData[j + 3]);
                    j += 4;
                } else if (shapeType == 3) {
                    intersects = line(x, y, shapeData[j], shapeData[j + 1], shapeData[j + 2], shapeData[j + 3], shapeData[j + 4] / 2f);
                    j += 5;
                } else if (shapeType == 4) {
                    int vertexCount = round(shapeData[j]);
                    int start = j + 1;
                    float lastX = shapeData[start + vertexCount - 1];
                    float lastY = shapeData[start + vertexCount - 1 + vertexCount];
                    boolean oddNodes = false;
                    for (int h = 0; h < vertexCount; h++) {
                        float vX = shapeData[start + h];
                        float vY = shapeData[start + h + vertexCount];
                        if ((vY < y && lastY >= y) || (lastY < y && vY >= y)) {
                            if (vX + (y - vY) / (lastY - vY) * (lastX - vX) < x) oddNodes = !oddNodes;
                        }
                        lastX = vX;
                        lastY = vY;
                    }
                    intersects = oddNodes;
                    j += 1 + vertexCount * 2;
                }

                if (intersects) {
                    float sA = shapeData[in + 3];
                    float rA = 1 - sA;

                    r = r * rA + shapeData[in] * sA;
                    g = g * rA + shapeData[in + 1] * sA;
                    b = b * rA + shapeData[in + 2] * sA;
                }
            } else {
                go = false;
            }
        }

        int pixelIndex = i * 3;
        float total = 0;
        float diff;
        diff = (byteToFloat(imageData[pixelIndex + 2]) - r);
        total += diff * diff;
        diff = (byteToFloat(imageData[pixelIndex + 1]) - g);
        total += diff * diff;
        diff = (byteToFloat(imageData[pixelIndex]) - b);
        total += diff * diff;

        //difference[i] = total;

        int lid = getLocalId(0);

        differenceLocal[lid] = total;
        localBarrier();

        if (lid == 0) {
            float sum = 0;
            for (int u = 0; u < getLocalSize(); u++) {

                sum += differenceLocal[u];
            }
            difference[getGroupId()] = sum;
        }
    }

    public boolean circle(float x, float y, float cX, float cY, float cR) {
        float dx = cX - x;
        float dy = cY - y;

        return dx * dx + dy * dy < cR;
    }

    public boolean line(float x, float y, float sX, float sY, float eX, float eY, float epsilon) {
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

    private boolean inEpsilon(float x, float y, float x2, float y2, float epsilon) {
        float dx = x - x2;
        float dy = y - y2;
        return dx * dx + dy * dy < epsilon * epsilon;
    }

    public boolean ellipse(float x, float y, float eX, float eY, float rX, float rY) {
        float dX = x - eX;
        float dY = y - eY;
        return (dX * dX) * (rY * rY) + (dY * dY) * (rX * rX) <= 1 * (rX * rX) * (rY * rY);
    }

    public boolean rect(float x, float y, float rX, float rY, float w, float h) {
        return x > rX && x < rX + w && y > rY && y < rY + h;
    }

    public float byteToFloat(byte color) {
        return ((float) ((int) color & 0xff)) / 255f;
    }

    public interface DataProvider {
        float[] getBackgroundData();

        float[] getShapeData();
    }

    public double getDifference() {
        return totalDifference;
    }
}