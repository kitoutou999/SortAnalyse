package test.swing.model.sort;

import main.swing.model.sort.PigeonholeSort;
import main.swing.model.sort.SortingStrategy;

public class PigeonholeSortTest extends AbstractSortingTest {

    @Override
    protected SortingStrategy getSortingStrategy() {
        return new PigeonholeSort();
    }

    @Override
    protected String getSortName() {
        return "PigeonholeSort";
    }
}
