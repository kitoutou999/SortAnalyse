package main.swing.generator;

/**
 * Interface définissant une stratégie de génération de tableaux partiellement triés.
 * Chaque implémentation de cette interface fournit une méthode spécifique pour
 * générer un tableau selon un modèle de tri ou de mélange particulier.
 */
public interface GeneratorStrategy {

    /**
     * Génère un tableau partiellement ou entièrement trié/mélangé selon
     * la stratégie implémentée.
     *
     * @param unsortedData le tableau d'entrée à traiter.
     * @return un nouveau tableau traité selon la stratégie de génération.
     */
    int[] generateSort(int[] unsortedData);

}