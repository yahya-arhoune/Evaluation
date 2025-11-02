package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeProduitService lcps = new LigneCommandeProduitService();

        // ----------------------------------------------------
        // TEST 1: Create Categories, Products, Commands, LigneCommandeProduits
        System.out.println("--- Test 1: Creating Data ---");
        Categorie cat1 = new Categorie("CAT001", "Electronics");
        Categorie cat2 = new Categorie("CAT002", "Office Supplies");

        cs.create(cat1);
        cs.create(cat2);

        Produit p1 = new Produit("REF001", "Laptop", 12000.0f, cat1);
        Produit p2 = new Produit("REF002", "Mouse", 150.0f, cat1);
        Produit p3 = new Produit("REF003", "Keyboard", 300.0f, cat1);
        Produit p4 = new Produit("REF004", "Printer", 2000.0f, cat1);
        Produit p5 = new Produit("REF005", "Pens", 50.0f, cat2);

        ps.create(p1);
        ps.create(p2);
        ps.create(p3);
        ps.create(p4);
        ps.create(p5);

        Date date1 = new Date(); // Today
        Date date2 = new Date(date1.getTime() - (1000L * 60 * 60 * 24 * 5)); // 5 days ago
        Date date3 = new Date(date1.getTime() + (1000L * 60 * 60 * 24 * 5)); // 5 days later (for date range test)

        Commande cmd1 = new Commande(date1);
        Commande cmd2 = new Commande(date2);

        cms.create(cmd1);
        cms.create(cmd2);

        lcps.create(new LigneCommandeProduit(1, p1, cmd1));
        lcps.create(new LigneCommandeProduit(2, p2, cmd1));
        lcps.create(new LigneCommandeProduit(1, p3, cmd1)); // Commande 1 has 3 products
        lcps.create(new LigneCommandeProduit(3, p4, cmd2));
        lcps.create(new LigneCommandeProduit(5, p5, cmd2)); // Commande 2 has 2 products

        System.out.println("Data created successfully.");
        System.out.println("----------------------------------------------------");

        // ----------------------------------------------------
        // TEST 2: Display Products by Category
        System.out.println("--- Test 2: Products in Electronics Category ---");
        List<Produit> electronicsProducts = ps.findProduitsByCategorie(cat1);
        if (electronicsProducts != null) {
            electronicsProducts.forEach(System.out::println);
        }
        System.out.println("----------------------------------------------------");

        // ----------------------------------------------------
        // TEST 3: Display Ordered Products between two dates (Example: 2023-03-10 to 2023-03-20)
        System.out.println("--- Test 3: Products Ordered Between Dates (Hardcoded example) ---");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2023-01-01"); // Adjust these dates
            Date endDate = sdf.parse("2024-12-31");   // to match your test data creation
            // For now, let's use the dates we just created, assuming today is within the range
            List<LigneCommandeProduit> orderedProducts = ps.findProduitsCommandesBetweenDates(date2, date1); // Between 5 days ago and today
            if (orderedProducts != null) {
                System.out.println("Produits commandés entre " + sdf.format(date2) + " et " + sdf.format(date1) + ":");
                orderedProducts.forEach(lcp ->
                        System.out.println("  Commande ID: " + lcp.getCommande().getId() +
                                ", Date: " + sdf.format(lcp.getCommande().getDate()) +
                                ", Produit: " + lcp.getProduit().getDesignation() +
                                ", Référence: " + lcp.getProduit().getReference() +
                                ", Quantité: " + lcp.getQuantite())
                );
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------------------------------");

        // ----------------------------------------------------
        // TEST 4: Display Ordered Products in a given Command (Example: Command ID 1)
        System.out.println("--- Test 4: Products in Command ID " + cmd1.getId() + " ---");
        List<LigneCommandeProduit> cmd1Products = ps.findProduitsByCommande(cmd1.getId());
        if (cmd1Products != null) {
            System.out.println("Commande : " + cmd1.getId() + "    Date : " + new SimpleDateFormat("dd MMM yyyy").format(cmd1.getDate()));
            System.out.println("Liste des produits :");
            System.out.printf("%-10s %-10s %s%n", "Référence", "Prix", "Quantité");
            for (LigneCommandeProduit lcp : cmd1Products) {
                System.out.printf("%-10s %-10.2f %d%n",
                        lcp.getProduit().getReference(),
                        lcp.getProduit().getPrix(),
                        lcp.getQuantite());
            }
        }
        System.out.println("----------------------------------------------------");

        // ----------------------------------------------------
        // TEST 5: Display Products with price > 100 DH (using NamedQuery)
        System.out.println("--- Test 5: Products with Price > 100 DH ---");
        List<Produit> expensiveProducts = ps.findProduitsByPrixGreaterThan(100.0f);
        if (expensiveProducts != null) {
            expensiveProducts.forEach(System.out::println);
        }
        System.out.println("----------------------------------------------------");


        // Shutdown Hibernate
        HibernateUtil.shutdown();
    }
}