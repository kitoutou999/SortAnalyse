package main.swing.generator;

/**
 * Représente un générateur de tri partiellement mélangé où la première moitié
 * du tableau est mélangée aléatoirement.
 * Implémente l'interface GeneratorStrategy.
 */
public class FirstHalfChunkGeneratorStrategy implements GeneratorStrategy {

    private int n;

    /**
     * Constructeur de FirstHalfChunkGeneratorStrategy.
     *
     * @param n pourcentage de mélange de la première moitié du tableau.
     */
    public FirstHalfChunkGeneratorStrategy(int n) {
        this.n = n;
    }

    /**
     * Génère un tableau partiellement trié où la première moitié est mélangée aléatoirement.
     *
     * @param unsortedData le tableau d'entrée à trier partiellement.
     * @return un nouveau tableau où la première moitié est mélangée aléatoirement et
     *         la seconde moitié reste triée.
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        int halfSize = unsortedData.length / 2;
        int[] firstHalf = new int[halfSize];
        System.arraycopy(unsortedData, 0, firstHalf, 0, halfSize);

        RandomGeneratorStrategy randomGenerator = new RandomGeneratorStrategy(n);
        int[] randomFirstHalf = randomGenerator.generateSort(firstHalf);

        int[] sortedData = new int[unsortedData.length];
        System.arraycopy(randomFirstHalf, 0, sortedData, 0, halfSize);
        System.arraycopy(unsortedData, halfSize, sortedData, halfSize, unsortedData.length - halfSize);

        return sortedData;
    }
}