package ma.projet3.service;

import ma.projet3.beans.Mariage;
import ma.projet3.beans.Homme;
import ma.projet3.beans.Femme;
import java.util.List;
import java.util.Map;
import java.util.Date; // Don't forget to import Date

public interface IMariageService {
    Mariage create(Mariage o);
    void delete(Mariage o);
    Mariage update(Mariage o);
    List<Mariage> findAll();
    Mariage findById(Long id);

    // Specific methods as per exercise
    List<Object[]> getNombreHommesMariesFourFemmesBetweenDates(Date startDate, Date endDate); // Added date parameters
    List<Map<String, Object>> getHommesMariesWithDetails(Homme homme); // Changed to take a specific Homme
}