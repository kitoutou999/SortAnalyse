package main.swing.generator;

import main.swing.model.sort.*;
import main.swing.model.*;
import main.swing.utils.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Représente un générateur de fichiers JSON contenant les résultats de tri
 *
 * @author Tom David et Florian Pépin
 * @version 2.0
 */
public class SortingResultJsonGenerator<T> extends AbstractListenableModel {

    private static final int[] SIZES = {1000,10000};
    private static final String BASE_DIR = "src/main/web/resources/data/json/";
    private static int COUNTER = 0;
    private static final GeneratorStrategy[] GENERATORS = {
            new FirstHalfChunkGeneratorStrategy(100),
            new FirstHalfReverseGeneratorStrategy(),
            new RandomGeneratorStrategy(100),
            new ReverseGeneratorStrategy(),
            new SecondHalfChunkGeneratorStrategy(100),
            new SecondHalfReverseGeneratorStrategy(),
    };

    private static final SecondGeneratorStrategy[] GENERATORSWITHENTROPY = {
            new SecondGeneratorStrategy<>(0.1),
            new SecondGeneratorStrategy<>(0.5),
            new SecondGeneratorStrategy<>(1.0)
    };

    private static final double[] ENTROPY_LEVELS = {0.1, 0.5, 1.0};

    private static final SortingStrategy[] SORTS = {
            new BubbleSort(),
            new CocktailShakerSort(),
            new GnomeSort(),
            new HeapSort(),
            new InsertionSort(),
            new MergeSort(),
            new QuickSort(),
            new RadixSort(),
            new SelectionSort(),
            new ShellSort()
    };

    /**
     * Constructeur de la classe.
     */
    public SortingResultJsonGenerator() {
        super();
    }

    /**
     * Obtient la progression des tests en pourcentage.
     * 
     * @return la progression des tests.
     */
    public int getProgress() {
        return (COUNTER*100)/((GENERATORS.length + GENERATORSWITHENTROPY.length) * SORTS.length * SIZES.length);
    }

    /**
     * Exécute les tests de tri et génère les fichiers JSON des résultats.
     */
    public void executeTests() {
        SortingResultJsonGenerator.COUNTER = 0;
        int totalTests = (GENERATORS.length + GENERATORSWITHENTROPY.length) * SORTS.length * SIZES.length;

        System.out.println("=== DÉMARRAGE DES TESTS DE TRI ===");
        System.out.println("Nombre total de tests à exécuter: " + totalTests);
        System.out.println("-------------------------------------");

        // Première partie: tests avec les générateurs standards
        System.out.println("\n=== PARTIE 1: GÉNÉRATEURS STANDARDS ===");
        for (int i = 0; i < GENERATORS.length; i++) {
            String generatorName = GENERATORS[i].getClass().getSimpleName();

            String generatorDir = BASE_DIR + generatorName;
            new File(generatorDir).mkdirs(); // Crée le dossier contenant tous les generateurs.

            for (int size : SIZES) {

                String sizeDir = generatorDir + "/" + size;
                new File(sizeDir).mkdirs(); // Crée le dossier contenant toutes les tailles.
                int[] originalList = generateSortedList(size);

                for (int j = 0; j < SORTS.length; j++) {
                    String sortName = SORTS[j].getClass().getSimpleName();
                    System.out.print("    - Tri " + sortName + "... ");

                    // Applique le générateur et le tri
                    int[] generatedList = GENERATORS[i].generateSort(originalList.clone());

                    SortingList sortingList = new SortingList(SORTS[j], sortName, originalList, generatedList);

                    // Trie et mesure les performances
                    long startTime = System.nanoTime();
                    sortingList.sort();
                    long endTime = System.nanoTime();

                    double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("complété en %.2f ms - %d comparaisons\n",
                            executionTimeMs, sortingList.getComparisons());

                    this.saveResults(i, sizeDir, sortName, sortingList);
                }
                System.out.println("  > Tests terminés pour la taille " + size);
            }
            System.out.println(">> Tests terminés pour le générateur " + generatorName);
        }

        // Seconde partie: tests avec les générateurs avec entropie
        System.out.println("\n=== PARTIE 2: GÉNÉRATEURS AVEC ENTROPIE ===");
        for (int i = 0; i < GENERATORSWITHENTROPY.length; i++) {
            double entropyLevel = ENTROPY_LEVELS[i];

            // Définir un nom de dossier spécifique pour chaque niveau d'entropie
            String generatorDir = BASE_DIR + GENERATORSWITHENTROPY[i].getClass().getSimpleName()+entropyLevel;
            new File(generatorDir).mkdirs(); // Crée le dossier contenant tous les generateurs.
            SecondGeneratorStrategy<Integer> generator = GENERATORSWITHENTROPY[i];

            for (int size : SIZES) {
                System.out.println("  > Taille du tableau: " + size);

                String sizeDir = generatorDir + "/" + size;
                new File(sizeDir).mkdirs();

                Integer[] sortedArray = new Integer[size];
                for (int k = 0; k < sortedArray.length; k++) {
                    sortedArray[k] = k;
                }

                for (int j = 0; j < SORTS.length; j++) {
                    String sortName = SORTS[j].getClass().getSimpleName();
                    System.out.print("    - Tri " + sortName + "... ");

                    Integer[] generatedArray = generator.sortWithEntropy(sortedArray.clone(), true);

                    // Vérifier l'entropie réelle obtenue
                    int actualEntropy = SecondGeneratorStrategy.calculateEntropy(generatedArray, sortedArray);
                    System.out.print("(entropie réelle: " + actualEntropy + "%) ");

                    SortingList sortingList = new SortingList(SORTS[j], sortName, convertIntegerArray(sortedArray), convertIntegerArray(generatedArray));

                    long startTime = System.nanoTime();
                    sortingList.sort();
                    long endTime = System.nanoTime();

                    double executionTimeMs = (endTime - startTime) / 1_000_000.0;
                    System.out.printf("complété en %.2f ms - %d comparaisons\n",
                            executionTimeMs, sortingList.getComparisons());

                    this.saveResultsWithEntropy(i, ENTROPY_LEVELS[i], sizeDir, sortName, sortingList);
                }
                System.out.println("  > Tests terminés pour la taille " + size);
            }
            System.out.println(">> Tests terminés pour le générateur avec entropie " + entropyLevel);
        }

        System.out.println("\n=== TOUS LES TESTS SONT TERMINÉS ===");
        System.out.println("Nombre total de tests exécutés: " + COUNTER);
        System.out.println("Les résultats ont été enregistrés dans: " + BASE_DIR);
    }

    /**
     * Convertit un tableau d'Integer en tableau d'int.
     * 
     * @param array le tableau d'Integer.
     * @return le tableau d'int.
     */
    private int[] convertIntegerArray(Integer[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Génère une liste triée de la taille spécifiée.
     * 
     * @param size la taille de la liste.
     * @return la liste triée.
     */
    private int[] generateSortedList(int size) {
        int[] newList = new int[size];
        for (int i = 0; i < size; i++) {
            newList[i] = i + 1;
        }
        return newList;
    }

    /**
     * Sauvegarde les résultats des tests dans un fichier JSON.
     * 
     * @param index l'index du générateur.
     * @param sizeDir le répertoire de la taille.
     * @param sortName le nom du tri.
     * @param sortingList la liste de tri.
     */
    private void saveResults(int index, String sizeDir, String sortName, SortingList sortingList) {
        // Définit le chemin du fichier JSON
        String filePath = sizeDir + "/" + sortName + ".json";

        // Écrit les résultats dans un fichier JSON
        SortingDataWriter writer = new SortingDataWriter();
        writer.writeDataToJson(
                GENERATORS[index].getClass().getSimpleName(),
                sortName,
                sortingList.getSize(),
                sortingList.getComparisons(),
                sortingList.getArrayAccess(),
                sortingList.getSetOperations(),
                sortingList.getSwapOperations(),
                sortingList.getDelay(),
                sortingList.getPourcent()
        );
        SortingResultJsonGenerator.COUNTER++;
        int progress = getProgress();
        if (progress % 5 == 0) {  // Show progress every 5%
            System.out.println("Progression globale: " + progress + "%");
        }
        this.fireChange("json");
    }

    /**
     * Sauvegarde les résultats des tests avec entropie dans un fichier JSON.
     * 
     * @param index l'index du générateur.
     * @param entropy le niveau d'entropie.
     * @param sizeDir le répertoire de la taille.
     * @param sortName le nom du tri.
     * @param sortingList la liste de tri.
     */
    private void saveResultsWithEntropy(int index, double entropy, String sizeDir, String sortName, SortingList sortingList) {
        // Définit le chemin du fichier JSON
        String filePath = sizeDir + "/" + sortName + ".json";

        // Écrit les résultats dans un fichier JSON
        SortingDataWriter writer = new SortingDataWriter();
        writer.writeDataToJson(
                GENERATORSWITHENTROPY[index].getClass().getSimpleName() + entropy,
                sortName,
                sortingList.getSize(),
                sortingList.getComparisons(),
                sortingList.getArrayAccess(),
                sortingList.getSetOperations(),
                sortingList.getSwapOperations(),
                sortingList.getDelay(),
                sortingList.getPourcent()
        );
        SortingResultJsonGenerator.COUNTER++;
        int progress = getProgress();
        if (progress % 5 == 0) {  // Show progress every 5%
            System.out.println("Progression globale: " + progress + "%");
        }
        this.fireChange("json");
    }

    /**
     * Point d'entrée principal pour lancer le générateur de résultats JSON.
     * 
     * @param args les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("Lancement du générateur de résultats JSON pour les algorithmes de tri...");
        long startTime = System.currentTimeMillis();

        SortingResultJsonGenerator demo = new SortingResultJsonGenerator();
        demo.executeTests();

        long endTime = System.currentTimeMillis();
        double totalTimeSeconds = (endTime - startTime) / 1000.0;
        System.out.printf("\nTemps total d'exécution: %.2f secondes (%.2f minutes)\n",
                totalTimeSeconds, totalTimeSeconds/60);
    }
}
