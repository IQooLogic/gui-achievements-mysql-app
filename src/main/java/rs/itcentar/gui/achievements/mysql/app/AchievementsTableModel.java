package rs.itcentar.gui.achievements.mysql.app;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import rs.itcentar.gui.achievements.mysql.app.db.model.Achievement;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class AchievementsTableModel extends DefaultTableModel {

    private List<Achievement> achievements;

    @Override
    public Object getValueAt(int row, int column) {
        Achievement ach = achievements.get(row);
        switch (column) {
            case 0:
                return ach.getDisplayName();
            case 1:
                return ach.getDescription();
            case 2:
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(ach.getCreated()), ZoneId.systemDefault()).toString();
            case 3:
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(ach.getUpdated()), ZoneId.systemDefault()).toString();
            default:
                throw new IllegalArgumentException("Nepostojeca kolona");
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Display Name";
            case 1:
                return "Description";
            case 2:
                return "Created";
            case 3:
                return "Updated";
            default:
                throw new IllegalArgumentException("Nepostojeca kolona");
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return achievements == null ? 0 : achievements.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2:
            case 3:
                return String.class;
            default:
                throw new IllegalArgumentException("Nepostojeca kolona");
        }
    }

    public void update(List<Achievement> achievements) {
        this.achievements = achievements;
        fireTableDataChanged();
    }

    public Achievement getAchievement(int row) {
        return achievements.get(row);
    }

    public String getAchievementId(int row) {
        Achievement a = achievements.get(row);
        return a.getId();
    }
}
