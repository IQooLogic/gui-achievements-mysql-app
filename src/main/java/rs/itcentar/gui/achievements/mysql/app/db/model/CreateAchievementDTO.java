package rs.itcentar.gui.achievements.mysql.app.db.model;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class CreateAchievementDTO {

    private String displayName;
    private String description;
    private String icon;
    private long created;
    private long updated;
    private String gameId;

    public CreateAchievementDTO(String displayName, String description,
            String icon, long created, long updated, String gameId) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.created = created;
        this.updated = updated;
        this.gameId = gameId;
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
        return "Achievement{" + "displayName=" + displayName + ", description=" + description + ", icon=" + icon + ", created=" + created + ", updated=" + updated + ", gameId=" + gameId + '}';
    }
}
