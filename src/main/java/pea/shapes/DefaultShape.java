package pea.shapes;

import java.util.Random;

public abstract class DefaultShape implements Shape {
	private static final long serialVersionUID = -8593245581932501126L;

	protected final float[] genes;

	public DefaultShape(final int GENE_LENGTH) {
		genes = new float[GENE_LENGTH];
	}

	@Override
	public void mutate(final float MUTATION_RATE, final float MUTATION_AMOUNT, Random random) {
		for (int i = 0; i < genes.length; i++) {
			if (random.nextDouble() < MUTATION_RATE) {
				double change = random.nextDouble();
				genes[i] += change * MUTATION_AMOUNT * 2f - MUTATION_AMOUNT;
				if (genes[i] > 1) {
					genes[i] = 1;
				}
				if (genes[i] < 0) {
					genes[i] = 0;
				}
			}
		}
	}

	protected int getRelativeIndex(int index) {
		return index;
	}

	@Override
	public int compareTo(Shape shape) {
		return 0;
	}

	@Override
	public void setRandom(Random random) {
		for (int j = 0; j < genes.length; j++) {
			double gene = random.nextDouble();
			genes[j] = (float) gene;
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public float[] getGenes() {
		return genes;
	}

	@Override
	public void set(Shape other) {
		System.arraycopy(other.getGenes(), 0, genes, 0, other.getGenes().length);
	}
}
