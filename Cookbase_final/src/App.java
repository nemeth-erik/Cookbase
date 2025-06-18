import db.DBConnect;
import db.RezeptDAO;
import model.Rezept;
import model.Zutat;
import ZutatDAO.ZutatDAO;

import java.sql.Connection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Connection conn = DBConnect.getConnection();
        if (conn == null) {
            System.err.println("Konnte keine DB-Verbindung herstellen.");
            return;
        }

        System.out.println("Zutaten aus der Datenbank:");
        List<Zutat> zutaten = ZutatDAO.getAllZutaten();
        for (Zutat z : zutaten) {
            System.out.println("- " + z.getName());
        }

        System.out.println("\nRezepte aus der Datenbank:");
        List<Rezept> rezepte = RezeptDAO.getAllRezepte();
        for (Rezept r : rezepte) {
            System.out.println(r);
        }
    }
}