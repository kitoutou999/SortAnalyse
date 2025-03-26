package main.swing.generator;

import java.util.*;

/**
 * Représente un générateur de tri aléatoire basé sur l'entropie.
 * 
 * @author Tom David et Florian Pépin
 * @version 1.0
 */
public class SecondGeneratorStrategy<T> {

    private double entropy;
    private final Random random = new Random();

    /**
     * Constructeur de la classe SecondGeneratorStrategy.
     *
     * @param entropy le niveau d'entropie.
     */
    public SecondGeneratorStrategy(double entropy) {
        this.entropy = entropy;
    }

    /**
     * Arrondir un nombre à un certain nombre de décimales.
     *
     * @param value le nombre à arrondir.
     * @param decimals le nombre de décimales.
     * @return le nombre arrondi.
     */
    private double roundDecimal(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        double slide = value * factor;
        long rounded = Math.round(slide);
        return rounded / factor;
    }

    /**
     * Calculer l'entropie d'un tableau de données triées.
     * Via une comparaison entre le tableau trié et le tableau non trié.
     *
     * @param randomList le tableau de données triées.
     * @param unsortedData le tableau de données non triées.
     * @return l'entropie du tableau de données triées.
     */
   public static<T> int calculateEntropy(T[] randomList, T[] unsortedData) {
        if (randomList.length != unsortedData.length) {
            throw new IllegalArgumentException("Les tableaux ne sont pas de la même taille !");
        }
        int cnt = 0;
        for (int i = 0; i < randomList.length; i++) {
            if(!randomList[i].equals(unsortedData[i])) {
                cnt++;
            }
        }
        return (cnt * 100) / randomList.length;
    }

    /**
     * Calculer l'entropie d'un tableau de données triées.
     * Via une formule mathématique.
     *
     * @param x la probabilité de mélange.
     * @return l'entropie du tableau de données triées.
     */
    private double calculate(double x) {
        double logXBase2 = Math.log(x) / Math.log(2);
        double logOneMinusXBase2 = Math.log(1 - x) / Math.log(2);
        return -x * (logXBase2 - logOneMinusXBase2) - logOneMinusXBase2;
    }

    /**
     * Calculer la probabilité de mélange pour un certain niveau d'entropie.
     * Via une recherche dichotomique.
     *
     * @param entropy le niveau d'entropie.
     * @return un tableau contenant les deux probabilités de mélange.
     */
    private double[] disorder(double entropy) {
        double left = 0.001;
        double right = 0.999;
        double precision = 0.001;
        double[] result = new double[2]; //Les deux intersections de la courbe de newton.

        while (right - left > precision) {
            double mid = (left + right) / 2;
            double value = this.calculate(mid);

            if (Math.abs(value - entropy) < precision) {
                result[0] = mid;
                result[1] = 1 - mid;
                return result;
            }

            if (value < entropy) {
                left = mid;
            } else {
                right = mid;
            }
        }

        double bestX = (left + right) / 2;
        result[0] = bestX;
        result[1] = 1 - bestX;
        return result;
    }

    /**
     * Trier un tableau de données avec un certain niveau d'entropie.
     * Fonctionne avec n'importe quel nombre de symboles (binaire, ternaire, etc.)
     *
     * @param unsortedData le tableau de données à trier.
     * @param order indique l'ordre de tri (true pour utiliser la première probabilité, false pour la seconde).
     * @return le tableau de données partiellement mélangées selon le niveau d'entropie.
     */
    public T[] sortWithEntropy(T[] unsortedData, boolean order) {
        if (unsortedData == null || unsortedData.length <= 1 || entropy < 0 || entropy > 1) {
            return unsortedData;
        }

        double[] probabilities = this.disorder(entropy);
        int choice = order ? 0 : 1;
        double probability = this.roundDecimal(probabilities[choice], 3);

        int positionsToChange = (int) (probability * unsortedData.length);

        // Crée la liste d'indices melange
        List<Integer> availableIndices = new ArrayList<>(unsortedData.length);
        for (int i = 0; i < unsortedData.length; i++) {
            availableIndices.add(i);
        }

        Collections.shuffle(availableIndices, this.random);

        // Prend uniquement le nombre d'indices à changer dans la liste availableIndices
        int[] indicesToChange = new int[positionsToChange];
        for (int i = 0; i < positionsToChange; i++) {
            indicesToChange[i] = availableIndices.get(i);
        }

        // copie original
        T[] result = Arrays.copyOf(unsortedData, unsortedData.length);

        // Pour chaque indicesToChange, remplacer par une valeur différente
        int changedCount = 0;
        int index = 0;

        while (changedCount < positionsToChange && index < result.length) {
            int posToChange = indicesToChange[changedCount];
            T originalValue = result[posToChange];
            T newValue = result[index];

            // 2 valeurs sont different
            if (!newValue.equals(originalValue)) {
                result[posToChange] = newValue;
                changedCount++;
            }

            index++;

        }

        return result;
    }

    T[] generateSort(T[] unsortedData) {
        return sortWithEntropy(unsortedData, true);
    }

    public static void main(String[] args) {
        double[] entropyLevels = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        Integer[] sortedArray = new Integer[1000];
        for (int i = 0; i < sortedArray.length; i++) {
            sortedArray[i] = i ;
        }

        SecondGeneratorStrategy<Integer> generator ;

        System.out.println("Tableau original: " + Arrays.toString(sortedArray));

        for (double entropy : entropyLevels) {
            generator = new SecondGeneratorStrategy<>(entropy);

            double[] probabilities = generator.disorder(entropy);
            double disorder = generator.roundDecimal(probabilities[0], 3);
            Integer[] mixed = generator.sortWithEntropy(Arrays.copyOf(sortedArray, sortedArray.length), true);

            System.out.println("\nEntropie (calcul maths) : " + entropy);
            System.out.println("Desordre : " + disorder);
            System.out.println("Entropie (comparaison tableau) : " + SecondGeneratorStrategy.calculateEntropy(mixed, sortedArray) + "%");
        }
    }

}