package ma.projet.dao;

import java.util.List;

public interface IDao<T, ID> {
    
    /**
     * Créer une nouvelle entité
     * @param entity l'entité à créer
     * @return l'entité créée avec son ID
     */
    T create(T entity);
    
    /**
     * Mettre à jour une entité existante
     * @param entity l'entité à mettre à jour
     * @return l'entité mise à jour
     */
    T update(T entity);
    
    /**
     * Supprimer une entité par son ID
     * @param id l'ID de l'entité à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean delete(ID id);
    
    /**
     * Trouver une entité par son ID
     * @param id l'ID de l'entité à rechercher
     * @return l'entité trouvée ou null si elle n'existe pas
     */
    T findById(ID id);
    
    /**
     * Récupérer toutes les entités
     * @return la liste de toutes les entités
     */
    List<T> findAll();
    
    /**
     * Vérifier si une entité existe par son ID
     * @param id l'ID de l'entité à vérifier
     * @return true si l'entité existe, false sinon
     */
    boolean exists(ID id);
}
