package td1.commandes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.ArrayList;

import static td1.commandes.Categorie.*;
import td1.paires.Paire;

public class DAO {
    private List<Commande> commandes;

    private DAO(Commande c1, Commande ...cs) {
        commandes = new ArrayList<>();
        commandes.add(c1);
        commandes.addAll(List.of(cs));
    }

    public static DAO instance = null;

    public static final DAO instance() {
        if (instance == null) {
            Produit camembert = new Produit("Camembert", 4.0, NORMAL);
            Produit yaourts = new Produit("Yaourts", 2.5, INTERMEDIAIRE);
            Produit masques = new Produit("Masques", 25.0, REDUIT);
            Produit gel = new Produit("Gel", 5.0, REDUIT);
            Produit tournevis = new Produit("Tournevis", 4.5, NORMAL);
            //
            Commande c1 = new Commande()
                .ajouter(camembert, 1)
                .ajouter(yaourts, 6);
            Commande c2 = new Commande()
                .ajouter(masques, 2)
                .ajouter(gel, 10)
                .ajouter(camembert, 2)
                .ajouter(masques, 3);
            //
            instance = new DAO(c1,c2);
        }
        return instance;
    }

    /**
     * liste de toutes les commandes
     */
    public List<Commande> commandes() { return commandes; }

    /**
     * ensemble des différents produits commandés
     */
    public Set<Produit> produits() {
        Set<Produit> rtr = new HashSet<>();
        for(Commande commande : commandes){
            for(Paire<Produit, Integer> p : commande.lignes()){
                rtr.add(p.fst());
            }
        }
        return rtr;
    }

    /**
     * liste des commandes vérifiant un prédicat
     */
    public List<Commande> selectionCommande(Predicate<Commande> p) {
        List<Commande> rtr = new ArrayList<>();
        for(Commande commande: commandes){
            if(p.test(commande)){
                rtr.add(commande);
            }
        }
        return rtr;
    }

    /**
     * liste des commandes dont au moins une ligne vérifie un prédicat
     */
    public List<Commande> selectionCommandeSurExistanceLigne(Predicate<Paire<Produit,Integer>> p) {
        List<Commande> rtr = new ArrayList<>();
        for(Commande commande: commandes){
            for (Paire<Produit, Integer> ligne : commande.lignes()){
                if(p.test(ligne)){
                    rtr.add(commande);
                }
            }
        }
        return rtr;
    }

    /**
     * ensemble des différents produits commandés vérifiant un prédicat
     */
    public Set<Produit> selectionProduits(Predicate<Produit> p) {
        Set<Produit> rtr = new HashSet<>();
        for(Produit produit : produits()){
            if(p.test(produit)){
                rtr.add(produit);
            }
        }
        return rtr;
    }

}
