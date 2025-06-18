package db;

import model.Rezept;
import model.ZutatenProRezept;
import ZutatenProRezeptDAO.ZutatenProRezeptDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RezeptDAO {
    private static final Logger LOGGER = Logger.getLogger(RezeptDAO.class.getName());

    public static List<Rezept> getAllRezepte() {
        List<Rezept> rezepte = new ArrayList<>();
        String sql = "SELECT * FROM rezept";
        try (Connection conn = DBConnect.getConnection();
             Statement stmt = conn != null ? conn.createStatement() : null;
             ResultSet rs = stmt != null ? stmt.executeQuery(sql) : null) {
            if (rs != null) {
                while (rs.next()) {
                    int rezeptID = rs.getInt("RezeptID");
                    String titel = rs.getString("Titel");
                    String zubereitung = rs.getString("Zubereitung");
                    boolean favorit = rs.getBoolean("Favorit");
                    String bildpfad = rs.getString("Bildpfad");
                    int kategorieID = rs.getInt("KategorieID");
                    List<ZutatenProRezept> zuordnung = ZutatenProRezeptDAO.getZutatenProRezept(rezeptID);
                    rezepte.add(new Rezept(rezeptID, zubereitung, favorit, titel, bildpfad, kategorieID, zuordnung));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Laden der Rezepte", e);
        }
        return rezepte;
    }

    public static void addRezept(Rezept rezept) {
        String sql = "INSERT INTO rezept (Titel, Zubereitung, Favorit, Bildpfad, KategorieID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn != null
                     ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                     : null) {
            if (stmt != null) {
                stmt.setString(1, rezept.getTitel());
                stmt.setString(2, rezept.getZubereitung());
                stmt.setBoolean(3, rezept.isFavorit());
                stmt.setString(4, rezept.getBildpfad());
                stmt.setInt(5, rezept.getKategorieID());
                stmt.executeUpdate();
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        rezept.setRezeptID(keys.getInt(1));
                    }
                }
                for (ZutatenProRezept zpr : rezept.getZutatenZuordnung()) {
                    ZutatenProRezeptDAO.addZutatZuRezept(
                        new ZutatenProRezept(0, rezept.getRezeptID(), zpr.getZutatenID()));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Fehler beim Hinzufügen eines Rezepts", e);
        }
    }
}