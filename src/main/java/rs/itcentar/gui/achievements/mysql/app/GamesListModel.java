package rs.itcentar.gui.achievements.mysql.app;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import rs.itcentar.gui.achievements.mysql.app.db.model.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class GamesListModel extends DefaultComboBoxModel<Game> {

    private List<Game> games;
    private Game selectedGame;

    @Override
    public Game getElementAt(int index) {
        return games.get(index);
    }

    @Override
    public int getSize() {
        return games == null ? 0 : games.size();
    }

    @Override
    public Object getSelectedItem() {
        return selectedGame;
    }

    @Override
    public void setSelectedItem(Object obj) {
        this.selectedGame = (Game) obj;
    }

    public void update(List<Game> games) {
        this.games = games;
        fireContentsChanged(this, 0, games.size() - 1);
    }
}
