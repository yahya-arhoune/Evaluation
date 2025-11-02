package ma.projet2.test;

import ma.projet2.classes.*;
import ma.projet2.service.*;
import ma.projet2.util.HibernateUtil2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        // Initialize services for Exercise 2
        ChefProjetService cps = new ChefProjetService();
        ProjetService ps = new ProjetService();
        EmployeService es = new EmployeService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // ----------------------------------------------------
        // TEST 1: Create Data for Projet Management
        System.out.println("--- Test 1: Creating Data for Projet Management ---");
        try {
            // ChefProjet
            ChefProjet cp1 = new ChefProjet("Dupont", "Jean", "0612345678");
            cps.create(cp1);

            // Projets
            Date p1_startDate = sdf.parse("2013-01-14");
            Date p1_endDate = sdf.parse("2013-05-30");
            Projet proj1 = new Projet("Gestion de stock", p1_startDate, p1_endDate, cp1); // ID 4 in example
            ps.create(proj1);

            Date p2_startDate = sdf.parse("2024-03-01");
            Date p2_endDate = sdf.parse("2024-08-31");
            Projet proj2 = new Projet("CRM Development", p2_startDate, p2_endDate, cp1);
            ps.create(proj2);

            // Employes
            Employe emp1 = new Employe("Martin", "Sophie", "0711223344");
            Employe emp2 = new Employe("Dubois", "Paul", "0655667788");
            es.create(emp1);
            es.create(emp2);

            // Taches for Projet 1
            Tache t1 = new Tache("Mise en place DB", sdf.parse("2013-02-01"), sdf.parse("2013-02-10"), 1500.0, proj1); // Analyse
            Tache t2 = new Tache("Developpement FrontEnd", sdf.parse("2013-02-15"), sdf.parse("2013-03-10"), 2500.0, proj1); // Design
            Tache t3 = new Tache("Developpement Backend", sdf.parse("2013-03-15"), sdf.parse("2013-04-20"), 3000.0, proj1); // Implementation
            Tache t4 = new Tache("Tests", sdf.parse("2013-04-25"), sdf.parse("2013-05-15"), 1000.0, proj1); // Tests

            ts.create(t1);
            ts.create(t2);
            ts.create(t3);
            ts.create(t4);

            // Taches for Projet 2
            Tache t5 = new Tache("CRM Requirements", sdf.parse("2024-03-05"), sdf.parse("2024-03-15"), 800.0, proj2);
            Tache t6 = new Tache("CRM Design", sdf.parse("2024-03-20"), sdf.parse("2024-04-10"), 1800.0, proj2);
            ts.create(t5);
            ts.create(t6);

            // EmployeTache (Assignments with real dates)
            ets.create(new EmployeTache(sdf.parse("2024-03-05"), sdf.parse("2024-03-14"), emp1, t5)); // emp1 did CRM Requirements for proj2
            // No real dates for t6 yet, to test "tasks with real dates" queries

            System.out.println("Data created successfully!");

            // ----------------------------------------------------
            // TEST 2: Display Planned Tasks for a Project (Projet 1: Gestion de stock)
            System.out.println("\n--- Test 2: Planned Tasks for Projet 'Gestion de stock' (ID " + proj1.getId() + ") ---");
            List<Tache> plannedTaches = ps.findPlannedTachesByProjet(proj1);
            if (plannedTaches != null && !plannedTaches.isEmpty()) {
                System.out.println("Projet: " + proj1.getNom() + " (ID: " + proj1.getId() + ")");
                System.out.println("Nom des tâches planifiées:");
                for (Tache tache : plannedTaches) {
                    System.out.println("  - " + tache.getNom() + " (Début: " + sdf.format(tache.getDateDebut()) + ", Fin: " + sdf.format(tache.getDateFin()) + ")");
                }
            } else {
                System.out.println("No planned tasks found for this project.");
            }

            // ----------------------------------------------------
            // TEST 3: Display Realized Tasks with Real Dates for a Project (Projet 1: Gestion de stock)
            System.out.println("\n--- Test 3: Realized Tasks with Real Dates for Projet 'Gestion de stock' (ID " + proj1.getId() + ") ---");
            List<EmployeTache> realizedTachesProj1 = ps.findRealizedTachesWithRealDatesByProjet(proj1);
            if (realizedTachesProj1 != null && !realizedTachesProj1.isEmpty()) {
                System.out.println("Projet: " + proj1.getNom() + " (ID: " + proj1.getId() + ")");
                System.out.printf("%-5s %-30s %-15s %-15s %-15s %-15s\n", "ID", "Tâche", "Employé", "Date Début Réelle", "Date Fin Réelle", "Prix");
                for (EmployeTache et : realizedTachesProj1) {
                    System.out.printf("%-5d %-30s %-15s %-15s %-15s %.2f DH\n",
                            et.getId(),
                            et.getTache().getNom(),
                            et.getEmploye().getNom(),
                            sdf.format(et.getDateDebutReal()),
                            sdf.format(et.getDateFinReal()),
                            et.getTache().getPrix());
                }
            } else {
                System.out.println("No realized tasks with real dates found for this project.");
            }

            // ----------------------------------------------------
            // TEST 4: Display Realized Tasks by an Employee (Emp1: Martin Sophie)
            System.out.println("\n--- Test 4: Realized Tasks by Employe 'Martin Sophie' (ID " + emp1.getId() + ") ---");
            List<EmployeTache> emp1RealizedTaches = es.findTachesRealiseesByEmploye(emp1);
            if (emp1RealizedTaches != null && !emp1RealizedTaches.isEmpty()) {
                System.out.println("Employé: " + emp1.getNom() + " " + emp1.getPrenom() + " (ID: " + emp1.getId() + ")");
                System.out.printf("%-5s %-30s %-15s %-15s %-15s\n", "ID", "Tâche", "Projet", "Date Début Réelle", "Date Fin Réelle");
                for (EmployeTache et : emp1RealizedTaches) {
                    System.out.printf("%-5d %-30s %-15s %-15s %-15s\n",
                            et.getId(),
                            et.getTache().getNom(),
                            et.getTache().getProjet().getNom(),
                            sdf.format(et.getDateDebutReal()),
                            sdf.format(et.getDateFinReal()));
                }
            } else {
                System.out.println("No realized tasks found for this employee.");
            }

            // ----------------------------------------------------
            // TEST 5: Display Projects "Managed" by an Employee (Emp1: Martin Sophie)
            System.out.println("\n--- Test 5: Projects where Employe 'Martin Sophie' (ID " + emp1.getId() + ") has participated ---");
            List<Projet> emp1Projets = es.findProjetsManagedByEmploye(emp1);
            if (emp1Projets != null && !emp1Projets.isEmpty()) {
                System.out.println("Employé: " + emp1.getNom() + " " + emp1.getPrenom() + " (ID: " + emp1.getId() + ")");
                System.out.println("Projets où l'employé a participé:");
                for (Projet p : emp1Projets) {
                    System.out.println("  - " + p.getNom() + " (ID: " + p.getId() + ")");
                }
            } else {
                System.out.println("No projects found where this employee has participated.");
            }


            // ----------------------------------------------------
            // TEST 6: Display Tasks with Price Greater Than 1000 DH (Using NamedQuery)
            System.out.println("\n--- Test 6: Tasks with Price Greater Than 1000 DH ---");
            List<Tache> expensiveTaches = ts.findTachesByPrixGreaterThan(1000.0);
            if (expensiveTaches != null && !expensiveTaches.isEmpty()) {
                System.out.printf("%-5s %-30s %-15s %-10s\n", "ID", "Tâche", "Projet", "Prix (DH)");
                for (Tache tache : expensiveTaches) {
                    System.out.printf("%-5d %-30s %-15s %.2f\n",
                            tache.getId(),
                            tache.getNom(),
                            tache.getProjet() != null ? tache.getProjet().getNom() : "N/A",
                            tache.getPrix());
                }
            } else {
                System.out.println("No tasks found with a price greater than 1000 DH.");
            }

            // ----------------------------------------------------
            // TEST 7: Display Realized Tasks Between Two Dates
            System.out.println("\n--- Test 7: Realized Tasks Between 2013-02-01 and 2013-05-01 ---");
            Date searchStartDate = sdf.parse("2013-02-01");
            Date searchEndDate = sdf.parse("2013-05-01");
            List<EmployeTache> tachesBetweenDates = ts.findTachesRealiseesBetweenDates(searchStartDate, searchEndDate);
            if (tachesBetweenDates != null && !tachesBetweenDates.isEmpty()) {
                System.out.printf("%-5s %-30s %-15s %-15s %-15s\n", "ID", "Tâche", "Employé", "Date Début Réelle", "Date Fin Réelle");
                for (EmployeTache et : tachesBetweenDates) {
                    System.out.printf("%-5d %-30s %-15s %-15s %-15s\n",
                            et.getId(),
                            et.getTache().getNom(),
                            et.getEmploye().getNom(),
                            sdf.format(et.getDateDebutReal()),
                            sdf.format(et.getDateFinReal()));
                }
            } else {
                System.out.println("No realized tasks found between " + sdf.format(searchStartDate) + " and " + sdf.format(searchEndDate) + ".");
            }


        } catch (ParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
        } finally {
            HibernateUtil2.shutdown();
            System.out.println("\nHibernate SessionFactory closed.");
        }
    }
}