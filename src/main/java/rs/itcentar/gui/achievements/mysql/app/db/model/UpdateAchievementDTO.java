package rs.itcentar.gui.achievements.mysql.app.db.model;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class UpdateAchievementDTO {

    private String id;
    private String displayName;
    private String description;
    private String icon;
    private int displayOrder;
    private String gameId;

    public UpdateAchievementDTO(String id, String displayName, String description,
            String icon, int displayOrder, String gameId) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.displayOrder = displayOrder;
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

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "UpdateAchievementDTO{" + "id=" + id + ", displayName=" + displayName + ", description=" + description + ", icon=" + icon + ", displayOrder=" + displayOrder + ", gameId=" + gameId + '}';
    }
}
