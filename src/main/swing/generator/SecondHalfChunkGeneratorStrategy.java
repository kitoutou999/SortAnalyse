package main.swing.generator;

/**
 * Stratégie de génération de morceaux pour la seconde moitié des données.
 */
public class SecondHalfChunkGeneratorStrategy implements GeneratorStrategy {

    private int n;

    /**
     * Constructeur de la classe.
     * 
     * @param n le paramètre utilisé pour la génération aléatoire.
     */
    public SecondHalfChunkGeneratorStrategy(int n) {
        this.n = n;
    }

    /**
     * Génère et trie la seconde moitié des données non triées.
     * 
     * @param unsortedData les données non triées.
     * @return les données triées avec la seconde moitié générée aléatoirement.
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        int halfSize = unsortedData.length / 2;
        int[] secondHalf = new int[halfSize];
        System.arraycopy(unsortedData, halfSize, secondHalf, 0, halfSize);

        RandomGeneratorStrategy randomGenerator = new RandomGeneratorStrategy(n);
        int[] randomSecondHalf = randomGenerator.generateSort(secondHalf);

        int[] sortedData = new int[unsortedData.length];
        System.arraycopy(unsortedData, 0, sortedData, 0, halfSize);
        System.arraycopy(randomSecondHalf, 0, sortedData, halfSize, halfSize);

        return sortedData;
    }
}
