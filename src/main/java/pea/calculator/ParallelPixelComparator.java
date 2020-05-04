package pea.calculator;

import pea.pixelbuffer.PixelBufferByte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelPixelComparator implements PixelComparator<PixelBufferByte> {

    private final int threads;
    private List<Callable<Long>> tasks;

    public ParallelPixelComparator(int threads) {
        this.threads = threads;

        tasks = new ArrayList<>();
    }

    @Override
    public double calculateDifference(PixelBufferByte a, PixelBufferByte b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Byte arrays must be the same length");
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        final int length = a.size();

        tasks.clear();
        int lastIndex = 0;
        int increment = (int) Math.round(Math.ceil((float) length / threads));
        while (lastIndex < length) {
            int newIndex = Math.min(lastIndex + increment, length);
            tasks.add(new ByteComparator(a.getPixels(), b.getPixels(), lastIndex, newIndex));
            lastIndex = newIndex;
        }


        while (true) {
            List<Future<Long>> result = null;
            long diff = 0;
            try {
                result = threadPool.invokeAll(tasks);

                for (Future<Long> f : result) {
                    diff += f.get();
                }
            } catch (InterruptedException | ExecutionException e) {
                continue;
            }

            threadPool.shutdown();
            return diff;
        }
    }

    private static class ByteComparator implements Callable<Long> {

        private byte[] a;
        private byte[] b;
        private int start;
        private int end;

        public ByteComparator(byte[] a, byte[] b, int start, int end) {
            this.a = a;
            this.b = b;
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() throws Exception {
            long diff = 0;
            for (int i = start; i < end; i++) {
                int d = Math.abs((a[i] & 0xff) - (b[i] & 0xff));
                diff += d * d;
            }
            return diff;
        }
    }
}
