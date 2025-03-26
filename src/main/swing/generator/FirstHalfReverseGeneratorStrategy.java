package main.swing.generator;

/**
 * Représente un générateur de tri partiellement inversé où la première moitié
 * du tableau est en ordre inversé.
 * Implémente l'interface GeneratorStrategy.
 */
public class FirstHalfReverseGeneratorStrategy implements GeneratorStrategy {

    /**
     * Génère un tableau partiellement trié où la première moitié est en ordre inversé.
     *
     * @param unsortedData le tableau d'entrée à traiter.
     * @return un nouveau tableau où la première moitié est inversée et
     *         la seconde moitié reste dans l'ordre original.
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        int size = unsortedData.length;
        int[] temp = new int[size];

        for(int i = 0 ;i<size/2;i++){
            temp[i]=unsortedData[size/2-i-1];
        }
        for(int i = size/2 ;i<size;i++){
            temp[i]=unsortedData[i];
        }
        return temp;
    }
}