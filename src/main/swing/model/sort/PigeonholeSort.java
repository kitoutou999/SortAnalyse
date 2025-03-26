package main.swing.model.sort;

import main.swing.model.*;
import java.util.ArrayList;

/**
 * Implémentation de Pigeonhole Sort.
 */
public class PigeonholeSort implements SortingStrategy {
    
    @Override
    public void sortingAlgorithm(SortingList sl) {
        pigeonholeSort(sl);
    }

    public void pigeonholeSort(SortingList sl) {
        int n = sl.getSize();
        int min = sl.getElement(0);
        int max = sl.getElement(0);

        for (int i = 1; i < n; i++) {
            if (sl.getElement(i) < min) min = sl.getElement(i);
            if (sl.getElement(i) > max) max = sl.getElement(i);
        }

        int range = max - min + 1;
        ArrayList<Integer>[] pigeonholes = new ArrayList[range];

        for (int i = 0; i < range; i++) {
            pigeonholes[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            pigeonholes[sl.getElement(i) - min].add(sl.getElement(i));
        }

        int index = 0;
        for (int i = 0; i < range; i++) {
            for (int num : pigeonholes[i]) {
                sl.set(index++, num);
            }
        }
    }
}
