import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        System.out.println("Lese alle Zutaten aus der Datenbank:");
        List<Zutat> zutaten = ZutatDAO.getAllZutaten();
        for (Zutat z : zutaten) {
            System.out.println("- " + z.getName());
        }

        System.out.println("\nFüge Beispiel-Zutat hinzu:");
        Zutat neueZutat = new Zutat(0, "Beispielzutat");
        ZutatDAO.addZutat(neueZutat);
        System.out.println("Hinzugefügt.");
    }
}