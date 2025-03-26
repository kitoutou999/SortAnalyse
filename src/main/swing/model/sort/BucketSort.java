package main.swing.model.sort;

import main.swing.model.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Implémentation de Bucket Sort.
 */
public class BucketSort implements SortingStrategy {
    
    @Override
    public void sortingAlgorithm(SortingList sl) {
        bucketSort(sl);
    }

    public void bucketSort(SortingList sl) {
        int n = sl.getSize();
        if (n <= 0) return;

        // Trouver min et max pour ajuster les indices des buckets
        int min = sl.getElement(0);
        int max = sl.getElement(0);

        for (int i = 1; i < n; i++) {
            if (sl.getElement(i) > max) max = sl.getElement(i);
            if (sl.getElement(i) < min) min = sl.getElement(i);
        }

        // Nombre optimal de buckets
        int bucketCount = (int) Math.sqrt(n);
        bucketCount = Math.max(bucketCount, 1); // Assurer au moins un bucket

        // Initialisation des buckets
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Distribution des éléments dans les buckets
        for (int i = 0; i < n; i++) {
            int value = sl.getElement(i);
            int bucketIndex = (value - min) * (bucketCount - 1) / (max - min + 1);
            buckets[bucketIndex].add(value);
        }

        // Trier chaque bucket individuellement
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            Collections.sort(buckets[i]); // Utilisation de Insertion Sort implicite
            for (int num : buckets[i]) {
                sl.set(index++, num);
            }
        }
    }
}
