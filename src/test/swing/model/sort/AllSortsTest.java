package test.swing.model.sort;

public class AllSortsTest {

    public static void main(String[] args) {
        boolean allTestsPassed = runTestsAndReturnStatus();
        
        System.out.println("==================================================");
        if (allTestsPassed) {
            System.out.println("🎉 Tous les tests pour toutes les stratégies de tri sont validés avec succès !");
        } else {
            System.out.println("❌ Certains tests de tri ont échoué. Veuillez vérifier les détails ci-dessus.");
        }
        System.out.println("==================================================");
    }

    // 🚀 Nouvelle méthode pour permettre l'appel depuis AllTestsLauncher
    public static boolean runTestsAndReturnStatus() {
        System.out.println("==================================================");
        System.out.println("       Lancement de tous les tests de tri         ");
        System.out.println("==================================================");

        boolean allTestsPassed = true;
        
        allTestsPassed &= new BubbleSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new CocktailShakerSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new GnomeSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new HeapSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new InsertionSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new MergeSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new QuickSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new RadixSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new SelectionSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new ShellSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new TimSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new BucketSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new CountingSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new OddEvenSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new CycleSortTest().runTestsAndReturnStatus();
        allTestsPassed &= new PigeonholeSortTest().runTestsAndReturnStatus();

        return allTestsPassed;
    }
}
