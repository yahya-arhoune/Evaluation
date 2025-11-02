package ma.projet3.service;

import ma.projet3.beans.Homme;
import ma.projet3.beans.Femme;
import java.util.Date; // Don't forget to import Date
import java.util.List;

public interface IHommeService {
    Homme create(Homme o);
    void delete(Homme o);
    Homme update(Homme o);
    List<Homme> findAll();
    Homme findById(Long id);

    /**
     * Ajoute une méthode pour afficher les épouses d'un homme entre deux dates.
     * This method retrieves all *distinct* wives of a given man whose marriage started within the specified date range.
     * @param homme The man whose wives are to be retrieved.
     * @param startDate The start date for filtering marriage dates.
     * @param endDate The end date for filtering marriage dates.
     * @return A list of Femme objects who were married to the Homme within the date range.
     */
    List<Femme> findEpousesBetweenDates(Homme homme, Date startDate, Date endDate);
}