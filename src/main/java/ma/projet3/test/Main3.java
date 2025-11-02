package ma.projet3.test;

import ma.projet3.beans.Femme;
import ma.projet3.beans.Homme;
import ma.projet3.beans.Mariage;
import ma.projet3.service.impl.FemmeService;
import ma.projet3.service.impl.HommeService;
import ma.projet3.service.impl.MariageService;
import ma.projet3.util.HibernateUtil; // To ensure EntityManagerFactory is properly closed

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main3 {
    public static void main(String[] args) throws ParseException {
        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // --- 1. Créer 10 femmes et 5 hommes ---
            System.out.println("--- 1. Creating 10 women and 5 men ---");
            Homme h1 = new Homme(); h1.setNom("SAFI SAID"); h1.setSexe("homme"); h1.setDateNaissance(sdf.parse("01/01/1980")); hommeService.create(h1);
            Homme h2 = new Homme(); h2.setNom("AHMED ALI"); h2.setSexe("homme"); h2.setDateNaissance(sdf.parse("05/03/1975")); hommeService.create(h2);
            Homme h3 = new Homme(); h3.setNom("KARIM IDRISSI"); h3.setSexe("homme"); h3.setDateNaissance(sdf.parse("10/06/1982")); hommeService.create(h3);
            Homme h4 = new Homme(); h4.setNom("YOUSSEF EL HOUARI"); h4.setSexe("homme"); h4.setDateNaissance(sdf.parse("15/09/1990")); hommeService.create(h4);
            Homme h5 = new Homme(); h5.setNom("MOHAMED AMINE"); h5.setSexe("homme"); h5.setDateNaissance(sdf.parse("20/11/1970")); hommeService.create(h5);

            Femme f1 = new Femme(); f1.setNom("SALHA RAMI"); f1.setSexe("femme"); f1.setDateNaissance(sdf.parse("03/05/1990")); femmeService.create(f1);
            Femme f2 = new Femme(); f2.setNom("FATIMA ZAHRA"); f2.setSexe("femme"); f2.setDateNaissance(sdf.parse("04/11/1988")); femmeService.create(f2);
            Femme f3 = new Femme(); f3.setNom("AICHA JABRI"); f3.setSexe("femme"); f3.setDateNaissance(sdf.parse("01/02/1978")); femmeService.create(f3);
            Femme f4 = new Femme(); f4.setNom("NOURA LAKHDAR"); f4.setSexe("femme"); f4.setDateNaissance(sdf.parse("07/07/1985")); femmeService.create(f4);
            Femme f5 = new Femme(); f5.setNom("ZINEB CHERKAOUI"); f5.setSexe("femme"); f5.setDateNaissance(sdf.parse("12/12/1992")); femmeService.create(f5);
            Femme f6 = new Femme(); f6.setNom("HANA EL KADIRI"); f6.setSexe("femme"); f6.setDateNaissance(sdf.parse("09/09/1983")); femmeService.create(f6);
            Femme f7 = new Femme(); f7.setNom("SAMIRA KARIMI"); f7.setSexe("femme"); f7.setDateNaissance(sdf.parse("25/04/1981")); femmeService.create(f7);
            Femme f8 = new Femme(); f8.setNom("WAFA ALAOUI"); f8.setSexe("femme"); f8.setDateNaissance(sdf.parse("04/11/2000")); femmeService.create(f8);
            Femme f9 = new Femme(); f9.setNom("LEILA BELKACEM"); f9.setSexe("femme"); f9.setDateNaissance(sdf.parse("18/03/1995")); femmeService.create(f9);
            Femme f10 = new Femme(); f10.setNom("CHOUROUK BENNANI"); f10.setSexe("femme"); f10.setDateNaissance(sdf.parse("22/08/1972")); femmeService.create(f10);
            System.out.println("--- Done creating 10 women and 5 men ---\n");

            // Some marriages for testing complex queries:
            // h1: married f1 (ended), f2 (ended), f3 (current)
            Mariage m1 = new Mariage(); m1.setMarie(h1); m1.setFemme(f1); m1.setDateMariage(sdf.parse("03/03/2010")); m1.setDateFin(sdf.parse("01/01/2015")); mariageService.create(m1);
            Mariage m2 = new Mariage(); m2.setMarie(h1); m2.setFemme(f2); m2.setDateMariage(sdf.parse("02/02/2016")); m2.setDateFin(sdf.parse("01/01/2018")); mariageService.create(m2);
            Mariage m3 = new Mariage(); m3.setMarie(h1); m3.setFemme(f3); m3.setDateMariage(sdf.parse("05/05/2019")); mariageService.create(m3);

            // h2: married f4, f5 (both current, for "mariés deux fois")
            Mariage m4 = new Mariage(); m4.setMarie(h2); m4.setFemme(f4); m4.setDateMariage(sdf.parse("10/10/2000")); mariageService.create(m4);
            Mariage m5 = new Mariage(); m5.setMarie(h2); m5.setFemme(f5); m5.setDateMariage(sdf.parse("12/12/2005")); mariageService.create(m5);

            // h3: married f6, f7 (both current)
            Mariage m6 = new Mariage(); m6.setMarie(h3); m6.setFemme(f6); m6.setDateMariage(sdf.parse("15/07/2012")); mariageService.create(m6);
            Mariage m7 = new Mariage(); m7.setMarie(h3); m7.setFemme(f7); m7.setDateMariage(sdf.parse("20/09/2018")); mariageService.create(m7);

            // h4: married f8 (current)
            Mariage m8 = new Mariage(); m8.setMarie(h4); m8.setFemme(f8); m8.setDateMariage(sdf.parse("01/01/2020")); mariageService.create(m8);

            // h5: married f9, f10 (both current)
            Mariage m9 = new Mariage(); m9.setMarie(h5); m9.setFemme(f9); m9.setDateMariage(sdf.parse("02/02/1998")); mariageService.create(m9);
            Mariage m10 = new Mariage(); m10.setMarie(h5); m10.setFemme(f10); m10.setDateMariage(sdf.parse("04/04/2004")); mariageService.create(m10);

            // Additional marriages for h1 for the "quatre femmes" criteria (if needed for testing)
            // Assuming h1 had previous marriages to f4 and f5 that ended before 2010.
            Mariage m11 = new Mariage(); m11.setMarie(h1); m11.setFemme(f4); m11.setDateMariage(sdf.parse("01/01/2000")); m11.setDateFin(sdf.parse("31/12/2002")); mariageService.create(m11);
            Mariage m12 = new Mariage(); m12.setMarie(h1); m12.setFemme(f5); m12.setDateMariage(sdf.parse("01/01/2003")); m12.setDateFin(sdf.parse("31/12/2005")); mariageService.create(m12);
            // Now h1 has been married to f1, f2, f3, f4, f5 (5 distinct women in total)
            // Let's create another homme to easily test the '4 femmes' criteria in a date range for a recent period
            Homme h6 = new Homme(); h6.setNom("Hommes 4 Femmes"); h6.setSexe("homme"); h6.setDateNaissance(sdf.parse("01/01/1960")); hommeService.create(h6);
            Mariage m_h6_f1 = new Mariage(); m_h6_f1.setMarie(h6); m_h6_f1.setFemme(f1); m_h6_f1.setDateMariage(sdf.parse("01/01/2020")); mariageService.create(m_h6_f1);
            Mariage m_h6_f2 = new Mariage(); m_h6_f2.setMarie(h6); m_h6_f2.setFemme(f2); m_h6_f2.setDateMariage(sdf.parse("01/02/2020")); mariageService.create(m_h6_f2);
            Mariage m_h6_f3 = new Mariage(); m_h6_f3.setMarie(h6); m_h6_f3.setFemme(f3); m_h6_f3.setDateMariage(sdf.parse("01/03/2020")); mariageService.create(m_h6_f3);
            Mariage m_h6_f4 = new Mariage(); m_h6_f4.setMarie(h6); m_h6_f4.setFemme(f4); m_h6_f4.setDateMariage(sdf.parse("01/04/2020")); mariageService.create(m_h6_f4);
            Mariage m_h6_f5 = new Mariage(); m_h6_f5.setMarie(h6); m_h6_f5.setFemme(f5); m_h6_f5.setDateMariage(sdf.parse("01/05/2020")); mariageService.create(m_h6_f5);


            // --- 2. Afficher la liste des femmes ---
            System.out.println("--- 2. List of All Women ---");
            femmeService.findAll().forEach(f -> System.out.println("ID: " + f.getId() + ", Nom: " + f.getNom() + ", Date Naissance: " + sdf.format(f.getDateNaissance())));
            System.out.println("-------------------------------------------\n");

            // --- 3. Afficher la femme la plus âgée ---
            System.out.println("--- 3. Oldest Woman ---");
            Femme oldestFemme = femmeService.findAll().stream()
                    .min((f_a, f_b) -> f_a.getDateNaissance().compareTo(f_b.getDateNaissance()))
                    .orElse(null);
            if (oldestFemme != null) {
                System.out.println("Nom: " + oldestFemme.getNom() + ", Date Naissance: " + sdf.format(oldestFemme.getDateNaissance()));
            } else {
                System.out.println("No women found.");
            }
            System.out.println("-------------------------------------------\n");

            // --- 4. Afficher les épouses d'un homme donné ---
            System.out.println("--- 4. Wives of a Specific Man (e.g., SAFI SAID) within a date range ---");
            Date rangeStartDate = sdf.parse("01/01/2015");
            Date rangeEndDate = sdf.parse("31/12/2020");
            List<Femme> epousesSafi = hommeService.findEpousesBetweenDates(h1, rangeStartDate, rangeEndDate);
            if (!epousesSafi.isEmpty()) {
                System.out.println("Epouses of " + h1.getNom() + " between " + sdf.format(rangeStartDate) + " and " + sdf.format(rangeEndDate) + ":");
                epousesSafi.forEach(f -> System.out.println("- " + f.getNom() + " (ID: " + f.getId() + ")"));
            } else {
                System.out.println("No wives found for " + h1.getNom() + " in this period.");
            }
            System.out.println("-------------------------------------------\n");

            // --- 5. Afficher le nombre d'enfants d'une femme donnée entre deux dates ---
            System.out.println("--- 5. Number of Children for a Specific Woman (e.g., SALHA RAMI) between two dates ---");
            // NOTE: This assumes an 'Enfant' entity and linking for Femme.
            // As per previous discussion, the current model doesn't explicitly have an 'Enfant' entity,
            // so this call will currently rely on a conceptual `enfant` table in the native query.
            // You would need to populate an 'Enfant' table in your DB for accurate results.
            Date childBirthStartDate = sdf.parse("01/01/2000");
            Date childBirthEndDate = sdf.parse("31/12/2023");
            int enfantsSalha = femmeService.getNombreEnfants(f1, childBirthStartDate, childBirthEndDate);
            System.out.println("Nombre d'enfants pour " + f1.getNom() + " nés entre " + sdf.format(childBirthStartDate) + " et " + sdf.format(childBirthEndDate) + ": " + enfantsSalha);
            System.out.println("-------------------------------------------\n");


            // --- 6. Afficher les femmes mariées au moins deux fois ---
            System.out.println("--- 6. Women Married at Least Twice ---");
            long countWomenMarriedTwice = femmeService.getNombreFemmesMarieesAuMoinsDeuxFois();
            System.out.println("Nombre de femmes distinctes mariées au moins deux fois: " + countWomenMarriedTwice);
            // To display their names, you'd need a more complex query or filter `findAll()`
            // (Not explicitly asked to list names, just the count)
            System.out.println("-------------------------------------------\n");

            // --- 7. Afficher le nombre d'hommes mariés à quatre femmes entre deux dates ---
            System.out.println("--- 7. Men Married to 4+ Women (with at least one marriage in date range) ---");
            Date marriageCriteriaStartDate = sdf.parse("01/01/2019");
            Date marriageCriteriaEndDate = sdf.parse("31/12/2023");
            List<Object[]> hommesFourFemmes = mariageService.getNombreHommesMariesFourFemmesBetweenDates(marriageCriteriaStartDate, marriageCriteriaEndDate);

            if (!hommesFourFemmes.isEmpty()) {
                System.out.println("Hommes mariés à 4+ femmes (avec mariage entre " + sdf.format(marriageCriteriaStartDate) + " et " + sdf.format(marriageCriteriaEndDate) + "):");
                for (Object[] result : hommesFourFemmes) {
                    System.out.println("ID: " + result[0] + ", Nom: " + result[1] + ", Nombre de femmes distinctes: " + result[2]);
                }
            } else {
                System.out.println("Aucun homme trouvé correspondant aux critères.");
            }
            System.out.println("-------------------------------------------\n");


            // --- 8. Afficher les mariages d'un homme donné avec tous les détails (femme, dates, nombre d'enfants) ---
            System.out.println("--- 8. Marriage Details for a Specific Man (e.g., SAFI SAID) ---");
            List<Map<String, Object>> mariagesH1Details = mariageService.getHommesMariesWithDetails(h1);
            if (!mariagesH1Details.isEmpty()) {
                System.out.println("Mariages pour " + h1.getNom() + ":");
                for (Map<String, Object> details : mariagesH1Details) {
                    System.out.println("  - Femme: " + details.get("Femme") +
                            ", Date Debut: " + sdf.format((Date) details.get("Date Debut")) +
                            (details.get("Date Fin") != null ? ", Date Fin: " + sdf.format((Date) details.get("Date Fin")) : ", Date Fin: N/A") +
                            ", Nbr Enfants: " + details.get("Nbr Enfants"));
                }
            } else {
                System.out.println("Aucun mariage trouvé pour " + h1.getNom() + ".");
            }
            System.out.println("-------------------------------------------\n");


        } finally {
            // Close the EntityManagerFactory when the application shuts down
            HibernateUtil.shutdown();
            System.out.println("Hibernate EntityManagerFactory closed.");
        }
    }
}