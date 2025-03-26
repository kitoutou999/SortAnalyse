package test.swing.model.sort;

import main.swing.model.sort.OddEvenSort;
import main.swing.model.sort.SortingStrategy;

public class OddEvenSortTest extends AbstractSortingTest {

    @Override
    protected SortingStrategy getSortingStrategy() {
        return new OddEvenSort();
    }

    @Override
    protected String getSortName() {
        return "OddEvenSort";
    }
}
