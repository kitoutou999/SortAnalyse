package main.swing.model.sort;

import main.swing.model.*;

/**
 * Implémentation de Cycle Sort.
 */
public class CycleSort implements SortingStrategy {
    
    @Override
    public void sortingAlgorithm(SortingList sl) {
        cycleSort(sl);
    }

    public void cycleSort(SortingList sl) {
        int n = sl.getSize();

        for (int cycleStart = 0; cycleStart < n - 1; cycleStart++) {
            int item = sl.getElement(cycleStart);
            int pos = cycleStart;

            for (int i = cycleStart + 1; i < n; i++) {
                if (sl.getElement(i) < item) {
                    pos++;
                }
            }

            if (pos == cycleStart) {
                continue;
            }

            while (item == sl.getElement(pos)) {
                pos++;
            }

            if (pos != cycleStart) {
                int temp = sl.getElement(pos);
                sl.set(pos, item);
                item = temp;
            }

            while (pos != cycleStart) {
                pos = cycleStart;

                for (int i = cycleStart + 1; i < n; i++) {
                    if (sl.getElement(i) < item) {
                        pos++;
                    }
                }

                while (item == sl.getElement(pos)) {
                    pos++;
                }

                if (item != sl.getElement(pos)) {
                    int temp = sl.getElement(pos);
                    sl.set(pos, item);
                    item = temp;
                }
            }
        }
    }
}
