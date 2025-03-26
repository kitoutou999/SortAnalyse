package test.swing.model.sort;

import main.swing.model.sort.BucketSort;
import main.swing.model.sort.SortingStrategy;

public class BucketSortTest extends AbstractSortingTest {

    @Override
    protected SortingStrategy getSortingStrategy() {
        return new BucketSort();
    }

    @Override
    protected String getSortName() {
        return "BucketSort";
    }
}
