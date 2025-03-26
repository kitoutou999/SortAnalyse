package main.swing.generator;

/**
 * Stratégie de génération inversée pour la seconde moitié des données.
 */
public class SecondHalfReverseGeneratorStrategy implements GeneratorStrategy {

    /**
     * Génère et trie la seconde moitié des données non triées en ordre inverse.
     * 
     * @param unsortedData les données non triées.
     * @return les données avec la seconde moitié inversée.
     */
    @Override
    public int[] generateSort(int[] unsortedData) {
        int size = unsortedData.length;
        int[] temp = new int[size];
        
        for(int i = 0 ;i<size/2;i++){
            temp[i]=unsortedData[i];
        }
        
        for(int i = 0 ;i<size/2;i++){
            temp[i+size/2]=unsortedData[size-i-1];
        }
        return temp;
    }

}
