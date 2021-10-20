package rs.itcentar.gui.achievements.mysql.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import rs.itcentar.gui.achievements.mysql.app.db.model.Achievement;
import rs.itcentar.gui.achievements.mysql.app.db.model.CreateAchievementDTO;
import rs.itcentar.gui.achievements.mysql.app.db.model.Game;
import rs.itcentar.gui.achievements.mysql.app.db.model.UpdateAchievementDTO;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementsDatabase {

    private Connection conn;

    public void connect(String url, String username, String password) throws SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        this.conn = DriverManager.getConnection(url, username, password);
    }

    public List<Achievement> getAllAchievements() throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM achievement")) {
            ResultSet rs = ps.executeQuery();

            List<Achievement> achievements = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
                String gameId = rs.getString(Achievement.COL_GAME_ID);

                Achievement achievement = new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId);
                achievements.add(achievement);
            }

            return achievements;
        }
    }

    public List<Game> getAllGames() throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM game")) {
            ResultSet rs = ps.executeQuery();

            List<Game> games = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString(Game.COL_ID);
                String displayName = rs.getString(Game.COL_DISPLAY_NAME);

                Game game = new Game(id, displayName);
                games.add(game);
            }

            return games;
        }
    }

    public List<Achievement> getAchievementsForGame(String gameId) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        List<Achievement> achievements = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM achievement WHERE game_id = ?")) {
            ps.setString(1, gameId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
//            String gameId = rs.getString(Achievement.COL_GAME_ID);

                Achievement achievement = new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId);
                achievements.add(achievement);
            }

            return achievements;
        }
    }

    public boolean createAchievement(CreateAchievementDTO achievement) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        int maxDisplayOrder = findMaxDisplayOrder(achievement.getGameId());
        if (maxDisplayOrder == -1) {
            throw new RuntimeException("Max display order not found! Maybe game doesn't exist!");
        }

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO achievement (id, displayName, description, icon, displayOrder, created, updated, game_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, achievement.getDisplayName());
            ps.setString(3, achievement.getDescription());
            ps.setString(4, achievement.getIcon());
            ps.setInt(5, maxDisplayOrder + 1);
            ps.setLong(6, achievement.getCreated());
            ps.setLong(7, achievement.getUpdated());
            ps.setString(8, achievement.getGameId());
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    private int findMaxDisplayOrder(String gameId) throws SQLException, RuntimeException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT MAX(displayOrder) AS max_order FROM achievement WHERE game_id = ?")) {
            ps.setString(1, gameId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_order");
            }
        }
        return -1;
    }

    public boolean createGame(String displayName) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO game (id, displayName) VALUES (?, ?)")) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, displayName);
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    public boolean updateAchievement(UpdateAchievementDTO achievement) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("UPDATE achievement SET displayName = ?, description = ?, icon = ?, displayOrder = ?, updated = ?, game_id = ? WHERE id = ?")) {
            ps.setString(1, achievement.getDisplayName());
            ps.setString(2, achievement.getDescription());
            ps.setString(3, achievement.getIcon());
            ps.setInt(4, achievement.getDisplayOrder());
            ps.setLong(5, System.currentTimeMillis());
            ps.setString(6, achievement.getGameId());
            ps.setString(7, achievement.getId());
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    public boolean updateGame(String gameId, String displayName) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("UPDATE game SET displayName = ? WHERE id = ?")) {
            ps.setString(1, displayName);
            ps.setString(2, gameId);
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    public boolean deleteAchievement(String id) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM achievement WHERE id = ?")) {
            ps.setString(1, id);
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    public boolean deleteGame(String id) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM game WHERE id = ?")) {
            ps.setString(1, id);
            int affectedRow = ps.executeUpdate();

            return affectedRow == 1;
        }
    }

    public Optional<Achievement> findAchievementById(String id) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM achievement WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //  String id = rs.getString(Achievement.COL_ID);
                String displayName = rs.getString(Achievement.COL_DISPLAY_NAME);
                String description = rs.getString(Achievement.COL_DESCRIPTION);
                String icon = rs.getString(Achievement.COL_ICON);
                int displayOrder = rs.getInt(Achievement.COL_DISPLAY_ORDER);
                long created = rs.getLong(Achievement.COL_CREATED);
                long updated = rs.getLong(Achievement.COL_UPDATED);
                String gameId = rs.getString(Achievement.COL_GAME_ID);

                return Optional.of(new Achievement(id, displayName, description, icon,
                        displayOrder, created, updated, gameId));
            }
        }

        return Optional.empty();
    }

    public Optional<Game> findGameById(String id) throws SQLException, RuntimeException {
        if (conn == null) {
            throw new RuntimeException("Not connected to database server. Invoke connect() method first.");
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM game WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
//            String id = rs.getString(Game.COL_ID);
                String displayName = rs.getString(Game.COL_DISPLAY_NAME);

                return Optional.of(new Game(id, displayName));
            }
        }

        return Optional.empty();
    }

    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
