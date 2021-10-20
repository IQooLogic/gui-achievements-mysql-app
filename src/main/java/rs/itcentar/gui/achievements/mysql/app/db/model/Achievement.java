package rs.itcentar.gui.achievements.mysql.app.db.model;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class Achievement {

    public static final String COL_ID = "id";
    public static final String COL_DISPLAY_NAME = "displayName";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_ICON = "icon";
    public static final String COL_DISPLAY_ORDER = "displayOrder";
    public static final String COL_CREATED = "created";
    public static final String COL_UPDATED = "updated";
    public static final String COL_GAME_ID = "game_id";

    private String id;
    private String displayName;
    private String description;
    private String icon;
    private int displayOrder;
    private long created;
    private long updated;
    private String gameId;

    public Achievement(String id, String displayName, String description,
            String icon, int displayOrder, long created, long updated, String gameId) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.displayOrder = displayOrder;
        this.created = created;
        this.updated = updated;
        this.gameId = gameId;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "Achievement{" + "id=" + id + ", displayName=" + displayName + ", description=" + description + ", icon=" + icon + ", displayOrder=" + displayOrder + ", created=" + created + ", updated=" + updated + ", gameId=" + gameId + '}';
    }
}
