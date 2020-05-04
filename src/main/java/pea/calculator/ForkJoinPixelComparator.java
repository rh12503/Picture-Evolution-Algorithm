package pea.calculator;

import pea.pixelbuffer.PixelBufferByte;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPixelComparator implements PixelComparator<PixelBufferByte> {

    @Override
    public double calculateDifference(PixelBufferByte a, PixelBufferByte b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Byte arrays must be the same length");
        }

        ForkJoinPool pool = ForkJoinPool.commonPool();
        DifferenceTask task = new DifferenceTask(a.getPixels(), b.getPixels(), 0, a.size());
        pool.execute(task);
        return task.join();
    }

    public static class DifferenceTask extends RecursiveTask<Long> {
        public static final int THRESHOLD = 1000;

        private final byte[] a;
        private final byte[] b;
        private final int start;
        private final int end;

        public DifferenceTask(byte[] a, byte[] b, int start, int end) {
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end-start > THRESHOLD) {
                int index = Math.round((end + start) / 2);
                DifferenceTask one = new DifferenceTask(a, b, start, index);
                DifferenceTask two = new DifferenceTask(a, b, index, end);
                invokeAll(one, two);
                return one.join() + two.join();

            } else {
                long diff = 0;
                for (int i = start; i < end; i++) {
                    int d = Math.abs((a[i] & 0xff) - (b[i] & 0xff));
                    diff += d * d;
                }
                return diff;
            }
        }
    }
}
