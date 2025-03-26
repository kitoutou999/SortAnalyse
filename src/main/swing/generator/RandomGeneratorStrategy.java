package main.swing.generator;

import java.util.*;

/**
 * Stratégie de génération qui mélange aléatoirement un pourcentage des éléments d'un tableau.
 */
public class RandomGeneratorStrategy implements GeneratorStrategy {
    private int n;

    /**
     * Constructeur de RandomGeneratorStrategy.
     *
     * @param n le pourcentage d'éléments à mélanger
     */
    public RandomGeneratorStrategy(int n) {
        this.n = n;
    }

    /**
     * Génère un tableau trié en mélangeant aléatoirement un pourcentage des éléments.
     *
     * @param unsortedData le tableau de données non triées
     * @return un nouveau tableau avec un pourcentage des éléments mélangés
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        Random random = new Random();
        int[] result = Arrays.copyOf(unsortedData, unsortedData.length);
        int entropy = (unsortedData.length * n) / 100;

        Set<Integer> movedIndices = new HashSet<>();

        while (movedIndices.size() < entropy) {
            int index1 = random.nextInt(result.length);
            if (movedIndices.contains(index1)) continue;

            int index2 = random.nextInt(result.length);
            if (movedIndices.contains(index2)) continue;

            int temp = result[index1];
            result[index1] = result[index2];
            result[index2] = temp;

            movedIndices.add(index1);
            movedIndices.add(index2);
        }

        return result;
    }
}
