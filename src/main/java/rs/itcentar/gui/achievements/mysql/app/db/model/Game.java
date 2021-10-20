package rs.itcentar.gui.achievements.mysql.app.db.model;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class Game {

    public static final String COL_ID = "id";
    public static final String COL_DISPLAY_NAME = "displayName";

    private String id;
    private String displayName;

    public Game(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id + ", displayName=" + displayName + '}';
    }
}
