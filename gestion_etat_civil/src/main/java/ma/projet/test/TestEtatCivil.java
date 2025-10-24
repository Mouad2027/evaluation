package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestEtatCivil {

    // Les services dont nous avons besoin
    private static HommeService hommeService = new HommeService();
    private static FemmeService femmeService = new FemmeService();
    private static MariageService mariageService = new MariageService();

    // Format de date
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        try {
            System.out.println("🚀 DÉMARRAGE DE L'APPLICATION ÉTAT CIVIL\n");

            // 1. Créer 10 femmes et 5 hommes
            System.out.println("1. 📝 CRÉATION DES PERSONNES");
            creerPersonnes();

            // 2. Afficher la liste des femmes
            System.out.println("\n2. 👩 LISTE DES FEMMES");
            afficherFemmes();

            // 3. Afficher la femme la plus âgée
            System.out.println("\n3. 👵 FEMME LA PLUS ÂGÉE");
            afficherFemmeLaPlusAgee();

            // 4. Afficher les épouses d'un homme donné
            System.out.println("\n4. 💑 ÉPOUSES D'UN HOMME ENTRE DEUX DATES");
            afficherEpousesHomme(1L);

            // 5. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("\n5. 👶 NOMBRE D'ENFANTS D'UNE FEMME");
            afficherNombreEnfantsFemme(1L);

            // 6. Afficher les femmes mariées deux fois ou plus
            System.out.println("\n6. 💍 FEMMES MARIÉES AU MOINS DEUX FOIS");
            afficherFemmesMarieesDeuxFois();

            // 7. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("\n7. 👨‍👩‍👧‍👦 HOMMES MARIÉS À QUATRE FEMMES");
            afficherHommesMarieesQuatreFemmes();

            // 8. Afficher les mariages d'un homme avec tous les détails
            System.out.println("\n8. 📊 DÉTAILS DES MARIAGES D'UN HOMME");
            hommeService.afficherMariagesHomme(1L);

            System.out.println("\n✅ TOUS LES TESTS ONT RÉUSSI !");

        } catch (Exception e) {
            System.err.println("❌ ERREUR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 1. Créer 10 femmes et 5 hommes avec des mariages
     */
    private static void creerPersonnes() throws Exception {
        System.out.println("Création des hommes et femmes...");

        // ==================== CRÉATION DES 5 HOMMES ====================
        Homme h1 = new Homme("SAFI", "SAID", "0612345678", "Casablanca", sdf.parse("15/05/1970"));
        Homme h2 = new Homme("ALAMI", "MOHAMED", "0612345679", "Rabat", sdf.parse("20/08/1975"));
        Homme h3 = new Homme("BERADA", "AHMED", "0612345680", "Fès", sdf.parse("10/03/1980"));
        Homme h4 = new Homme("KABLI", "YOUSSEF", "0612345681", "Marrakech", sdf.parse("25/12/1978"));
        Homme h5 = new Homme("CHAFIK", "KARIM", "0612345682", "Tanger", sdf.parse("05/07/1982"));

        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);

        // ==================== CRÉATION DES 10 FEMMES ====================
        Femme f1 = new Femme("SALIMA", "RAMI", "0612345683", "Casablanca", sdf.parse("12/06/1972"));
        Femme f2 = new Femme("AMAL", "ALI", "0612345684", "Rabat", sdf.parse("18/09/1976"));
        Femme f3 = new Femme("WAFA", "ALAOUI", "0612345685", "Fès", sdf.parse("22/11/1981"));
        Femme f4 = new Femme("KARIMA", "ALAMI", "0612345686", "Marrakech", sdf.parse("30/01/1974"));
        Femme f5 = new Femme("FATIMA", "BERADA", "0612345687", "Tanger", sdf.parse("14/04/1979"));
        Femme f6 = new Femme("NADIA", "KABLI", "0612345688", "Casablanca", sdf.parse("08/08/1983"));
        Femme f7 = new Femme("SOUAD", "CHAFIK", "0612345689", "Rabat", sdf.parse("17/02/1977"));
        Femme f8 = new Femme("HANAE", "SAFI", "0612345690", "Fès", sdf.parse("03/12/1980"));
        Femme f9 = new Femme("IMANE", "ALAMI", "0612345691", "Marrakech", sdf.parse("28/07/1975"));
        Femme f10 = new Femme("LEILA", "BERADA", "0612345692", "Tanger", sdf.parse("09/10/1982"));

        femmeService.create(f1);
        femmeService.create(f2);
        femmeService.create(f3);
        femmeService.create(f4);
        femmeService.create(f5);
        femmeService.create(f6);
        femmeService.create(f7);
        femmeService.create(f8);
        femmeService.create(f9);
        femmeService.create(f10);

        // Petite pause pour s'assurer que tout est sauvegardé
        Thread.sleep(1000);

        // ==================== CRÉATION DES MARIAGES ====================
        System.out.println("Création des mariages...");

        // Récupérer les personnes depuis la base de données (pour avoir leurs IDs)
        List<Homme> hommes = hommeService.findAll();
        List<Femme> femmes = femmeService.findAll();

        // SAFI SAID (4 mariages)
        Mariage m1 = new Mariage(sdf.parse("03/09/1990"), null, 4, hommes.get(0), femmes.get(0));
        Mariage m2 = new Mariage(sdf.parse("03/09/1995"), null, 2, hommes.get(0), femmes.get(1));
        Mariage m3 = new Mariage(sdf.parse("04/11/2000"), null, 3, hommes.get(0), femmes.get(2));
        Mariage m4 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, hommes.get(0), femmes.get(3));

        // ALAMI MOHAMED (2 mariages)
        Mariage m5 = new Mariage(sdf.parse("15/06/1998"), null, 1, hommes.get(1), femmes.get(4));
        Mariage m6 = new Mariage(sdf.parse("20/07/2002"), null, 2, hommes.get(1), femmes.get(5));

        // SOUAD CHAFIK mariée 2 fois (pour tester la requête)
        Mariage m7 = new Mariage(sdf.parse("10/01/2005"), sdf.parse("15/06/2010"), 1, hommes.get(2), femmes.get(6));
        Mariage m8 = new Mariage(sdf.parse("20/08/2011"), null, 0, hommes.get(0), femmes.get(6));

        mariageService.create(m1);
        mariageService.create(m2);
        mariageService.create(m3);
        mariageService.create(m4);
        mariageService.create(m5);
        mariageService.create(m6);
        mariageService.create(m7);
        mariageService.create(m8);

        System.out.println("✅ Données créées : 5 hommes, 10 femmes, 8 mariages");
    }

    /**
     * 2. Afficher la liste des femmes
     */
    private static void afficherFemmes() {
        List<Femme> femmes = femmeService.findAll();

        if (femmes.isEmpty()) {
            System.out.println("Aucune femme trouvée");
            return;
        }

        System.out.println("Nombre de femmes : " + femmes.size());
        for (int i = 0; i < femmes.size(); i++) {
            Femme f = femmes.get(i);
            System.out.println((i + 1) + ". " + f.getNom() + " " + f.getPrenom() +
                    " - " + sdf.format(f.getDateNaissance()));
        }
    }

    /**
     * 3. Afficher la femme la plus âgée
     */
    private static void afficherFemmeLaPlusAgee() {
        Femme femme = femmeService.getFemmeLaPlusAgee();

        if (femme != null) {
            System.out.println(femme.getNom() + " " + femme.getPrenom() +
                    " - Née le " + sdf.format(femme.getDateNaissance()));
        } else {
            System.out.println("Aucune femme trouvée");
        }
    }

    /**
     * 4. Afficher les épouses d'un homme entre deux dates
     */
    private static void afficherEpousesHomme(Long hommeId) throws Exception {
        Homme homme = hommeService.findById(hommeId);
        if (homme == null) {
            System.out.println("Homme non trouvé");
            return;
        }

        Date debut = sdf.parse("01/01/1990");
        Date fin = sdf.parse("31/12/2005");

        List<Object[]> epouses = hommeService.getEpousesEntreDates(hommeId, debut, fin);

        System.out.println("Épouses de " + homme.getNom() + " " + homme.getPrenom() + " :");

        if (epouses.isEmpty()) {
            System.out.println("Aucune épouse dans cette période");
        } else {
            for (Object[] e : epouses) {
                System.out.println("- " + e[0] + " " + e[1] +
                        " (Mariée le " + sdf.format((Date)e[2]) +
                        ", Enfants: " + e[4] + ")");
            }
        }
    }

    /**
     * 5. Afficher le nombre d'enfants d'une femme entre deux dates
     */
    private static void afficherNombreEnfantsFemme(Long femmeId) throws Exception {
        Femme femme = femmeService.findById(femmeId);
        if (femme == null) {
            System.out.println("Femme non trouvée");
            return;
        }

        Date debut = sdf.parse("01/01/1990");
        Date fin = sdf.parse("31/12/2010");

        Long nbrEnfants = femmeService.getNombreEnfantsEntreDates(femmeId, debut, fin);

        System.out.println(femme.getNom() + " " + femme.getPrenom() +
                " a eu " + nbrEnfants + " enfant(s)");
    }

    /**
     * 6. Afficher les femmes mariées deux fois ou plus
     */
    private static void afficherFemmesMarieesDeuxFois() {
        List<Femme> femmes = femmeService.getFemmesMarieesAuMoinsDeuxFois();

        System.out.println("Femmes mariées au moins 2 fois :");

        if (femmes.isEmpty()) {
            System.out.println("Aucune femme mariée au moins 2 fois");
        } else {
            for (Femme f : femmes) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }
        }
    }

    /**
     * 7. Afficher les hommes mariés à quatre femmes entre deux dates
     */
    private static void afficherHommesMarieesQuatreFemmes() throws Exception {
        Date debut = sdf.parse("01/01/1980");
        Date fin = sdf.parse("31/12/2010");

        List<Object[]> hommes = mariageService.getHommesMarieesQuatreFemmesEntreDates(debut, fin);

        System.out.println("Hommes mariés à au moins 4 femmes :");

        if (hommes.isEmpty()) {
            System.out.println("Aucun homme marié à 4 femmes ou plus");
        } else {
            for (Object[] h : hommes) {
                System.out.println("- " + h[1] + " " + h[2] + " (" + h[3] + " femmes)");
            }
        }
    }
}