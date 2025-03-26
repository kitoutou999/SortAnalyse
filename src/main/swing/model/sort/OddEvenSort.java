package main.swing.model.sort;

import main.swing.model.*;

/**
 * Implémentation de Odd-Even Sort.
 */
public class OddEvenSort implements SortingStrategy {
    
    @Override
    public void sortingAlgorithm(SortingList sl) {
        oddEvenSort(sl);
    }

    public void oddEvenSort(SortingList sl) {
        int n = sl.getSize();
        boolean sorted = false;

        while (!sorted) {
            sorted = true;

            for (int i = 1; i < n - 1; i += 2) {
                if (sl.getElement(i) > sl.getElement(i + 1)) {
                    sl.swap(i, i + 1);
                    sorted = false;
                }
            }

            for (int i = 0; i < n - 1; i += 2) {
                if (sl.getElement(i) > sl.getElement(i + 1)) {
                    sl.swap(i, i + 1);
                    sorted = false;
                }
            }
        }
    }
}
