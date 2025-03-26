package test.swing.model.sort;

import main.swing.model.sort.CycleSort;
import main.swing.model.sort.SortingStrategy;

public class CycleSortTest extends AbstractSortingTest {

    @Override
    protected SortingStrategy getSortingStrategy() {
        return new CycleSort();
    }

    @Override
    protected String getSortName() {
        return "CycleSort";
    }
}
