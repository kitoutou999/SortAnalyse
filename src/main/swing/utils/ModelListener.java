package main.swing.utils;

/**
 * Représente un écouteur.
 * 
 * @author Florian Pépin
 * @version 1.0
 */
public interface ModelListener {
    
    void updatedModel(Object source, String eventType);
    
}
