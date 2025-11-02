package ma.projet3.service;

import ma.projet3.beans.Femme;
import java.util.Date; // Don't forget to import Date
import java.util.List;

public interface IFemmeService {
    Femme create(Femme o);
    void delete(Femme o);
    Femme update(Femme o);
    List<Femme> findAll();
    Femme findById(Long id);

    int getNombreEnfants(Femme femme, Date startDate, Date endDate);

    /**
     * Une méthode pour exécuter une requête native retournant le nombre de femmes mariées au moins deux fois.
     * This counts the number of distinct women who have had at least two marriages.
     * @return The count of distinct women who have been married two or more times.
     */
    long getNombreFemmesMarieesAuMoinsDeuxFois();
}