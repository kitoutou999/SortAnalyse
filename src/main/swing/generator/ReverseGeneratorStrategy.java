package main.swing.generator;

/**
 * Stratégie de génération qui inverse l'ordre des éléments d'un tableau.
 */
public class ReverseGeneratorStrategy implements GeneratorStrategy {

    /**
     * Génère un tableau trié en ordre inverse.
     *
     * @param unsortedData le tableau de données non triées
     * @return un nouveau tableau avec les éléments en ordre inverse
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        int size = unsortedData.length;
        int[] temp = new int[size];
        for(int i = 0 ;i<size;i++){
            temp[i]=unsortedData[size-i-1];
        }
        return temp;
    }

}
