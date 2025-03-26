package test.swing.model.sort;

import main.swing.model.sort.CountingSort;
import main.swing.model.sort.SortingStrategy;

public class CountingSortTest extends AbstractSortingTest {

    @Override
    protected SortingStrategy getSortingStrategy() {
        return new CountingSort();
    }

    @Override
    protected String getSortName() {
        return "CountingSort";
    }
}
