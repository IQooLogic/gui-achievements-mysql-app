package rs.itcentar.gui.achievements.mysql.app;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rs.itcentar.gui.achievements.mysql.app.db.AchievementsDatabase;
import rs.itcentar.gui.achievements.mysql.app.db.model.Achievement;
import rs.itcentar.gui.achievements.mysql.app.db.model.CreateAchievementDTO;
import rs.itcentar.gui.achievements.mysql.app.db.model.Game;

/**
 *
 * @author Milos Stojkovic <iqoologic@gmail.com>
 */
public class MainFrame extends javax.swing.JFrame {

    // FIXME: must execute from separated thread
    private final AchievementsDatabase database;
    private final GamesListModel gamesModel = new GamesListModel();
    private final AchievementsTableModel achievementsModel = new AchievementsTableModel();

    public MainFrame() {
        initComponents();

        pbLoading.setVisible(false);

        database = readDBPropertiesAndConnect();
//        List<Game> games = getGames();// FIXME: must execute from separated thread
//        gamesModel.update(games);

        getGamesInAnotherThread();

        lGames.setModel(gamesModel);
        lGames.setCellRenderer(new GamesListCellRenderer());

        lGames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Game game = lGames.getSelectedValue();
                    if (game != null) {
                        List<Achievement> achievementsForGame = getAchievementsForGame(game.getId()); // FIXME: must execute from separated thread
                        achievementsModel.update(achievementsForGame);
                    }
                }
            }
        });

        tAchievementsForGame.setModel(achievementsModel);
    }

    private AchievementsDatabase readDBPropertiesAndConnect() throws HeadlessException, NumberFormatException {
        try {
            Properties props = new Properties();
            props.load(ClassLoader.getSystemResourceAsStream("database.properties"));

            String username = props.getProperty("username");
            String password = props.getProperty("password");

            String host = props.getProperty("host");
            int port = Integer.parseInt(props.getProperty("port"));
            String dbName = props.getProperty("dbname");
            String dbUrlPattern = props.getProperty("dburl");

            String connectionString = String.format(dbUrlPattern, host, port, dbName);

            AchievementsDatabase db = new AchievementsDatabase();
            db.connect(connectionString, username, password);
//            db.disconnect();
            return db;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri citanju konekcionih parametara za bazu", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri konektovanju nad bazom", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lGames = new javax.swing.JList<>();
        bAddGame = new javax.swing.JButton();
        bRemoveGame = new javax.swing.JButton();
        pbLoading = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tAchievementsForGame = new javax.swing.JTable();
        bAddAchievement = new javax.swing.JButton();
        bRemoveAchievement = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUI Game Achievements App");

        jSplitPane1.setResizeWeight(0.5);

        jScrollPane3.setViewportView(lGames);

        bAddGame.setText("Add");
        bAddGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddGameActionPerformed(evt);
            }
        });

        bRemoveGame.setText("Remove");
        bRemoveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemoveGameActionPerformed(evt);
            }
        });

        pbLoading.setIndeterminate(true);
        pbLoading.setString("");
        pbLoading.setStringPainted(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bAddGame)
                        .addGap(18, 18, 18)
                        .addComponent(pbLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(bRemoveGame)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bAddGame)
                        .addComponent(bRemoveGame))
                    .addComponent(pbLoading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );

        jSplitPane1.setLeftComponent(jPanel1);

        tAchievementsForGame.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tAchievementsForGame);

        bAddAchievement.setText("Add");
        bAddAchievement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddAchievementActionPerformed(evt);
            }
        });

        bRemoveAchievement.setText("Remove");
        bRemoveAchievement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemoveAchievementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bAddAchievement)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bRemoveAchievement)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bAddAchievement)
                    .addComponent(bRemoveAchievement))
                .addGap(11, 11, 11))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bAddGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddGameActionPerformed
        AddGamePanel panel = new AddGamePanel();
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        switch (result) {
            case JOptionPane.OK_OPTION: {
                createGame(panel.getDisplayName());

//                List<Game> games = getGames();// FIXME: must execute from separated thread
//                gamesModel.update(games);
                getGamesInAnotherThread();
            }
        }
    }//GEN-LAST:event_bAddGameActionPerformed

    private void bRemoveGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemoveGameActionPerformed
        if (!lGames.isSelectionEmpty()) {
            int result = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da hocete da izbrisete igricu ?", "Delete Game Confirmation", JOptionPane.YES_NO_OPTION);
            switch (result) {
                case JOptionPane.OK_OPTION:
                    Game selectedGame = lGames.getSelectedValue();
                    deleteGame(selectedGame);

//                    List<Game> games = getGames();// FIXME: must execute from separated thread
//                    gamesModel.update(games);
                    getGamesInAnotherThread();
            }
        }
    }//GEN-LAST:event_bRemoveGameActionPerformed

    private void bAddAchievementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddAchievementActionPerformed
        if (!lGames.isSelectionEmpty()) {
            Game selectedGame = lGames.getSelectedValue();

            AddAchievementPanel panel = new AddAchievementPanel(selectedGame);
            int result = JOptionPane.showConfirmDialog(this, panel, "Add New Achievement", JOptionPane.YES_NO_OPTION);
            switch (result) {
                case JOptionPane.YES_OPTION:
                    createAchievement(panel.getAchievement());

                    List<Achievement> achievementsForGame = getAchievementsForGame(selectedGame.getId()); // FIXME: must execute from separated thread
                    achievementsModel.update(achievementsForGame);
            }
        }
    }//GEN-LAST:event_bAddAchievementActionPerformed

    private void bRemoveAchievementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemoveAchievementActionPerformed
        if (!lGames.isSelectionEmpty()) {
            Game selectedGame = lGames.getSelectedValue();
            int selectedRow = tAchievementsForGame.getSelectedRow();
            if (selectedRow >= 0) {
                int result = JOptionPane.showConfirmDialog(this, "Da li ste sigurni da hocete da izbrisete achievement ?", "Delete Achievement Confirmation", JOptionPane.YES_NO_OPTION);
                switch (result) {
                    case JOptionPane.OK_OPTION:
                        Achievement achievement = achievementsModel.getAchievement(selectedRow);
                        deleteAchievement(achievement.getId());

                        List<Achievement> achievementsForGame = getAchievementsForGame(selectedGame.getId()); // FIXME: must execute from separated thread
                        achievementsModel.update(achievementsForGame);
                }
            }
        }
    }//GEN-LAST:event_bRemoveAchievementActionPerformed

//    private List<Game> getGames() {
//        try {
//            return database.getAllGames();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
//                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
//        } catch (RuntimeException ex) {
//            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
//                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
//        }
//        return Collections.EMPTY_LIST;
//    }
    private void getGamesInAnotherThread() {
        pbLoading.setVisible(true);
        pbLoading.setString("GET GAMES");
        SwingWorker<List<Game>, Void> worker = new SwingWorker<List<Game>, Void>() {
            @Override
            protected List<Game> doInBackground() throws Exception {
                Thread.sleep(10_000);
                return database.getAllGames();
            }

            @Override
            protected void done() {
                try {
                    List<Game> games = get();
                    gamesModel.update(games);
                } catch (InterruptedException | ExecutionException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                            "Doslo je do greske. Proverite konektivnost sa bazom.", JOptionPane.ERROR_MESSAGE);
                } finally {
                    pbLoading.setVisible(false);
                }
            }
        };
        worker.execute();
    }

    private List<Achievement> getAchievementsForGame(String gameId) {
        try {
            return database.getAchievementsForGame(gameId);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
        }
        return Collections.EMPTY_LIST;
    }

    private void deleteAchievement(String id) throws HeadlessException {
        try {
            database.deleteAchievement(id);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAchievement(CreateAchievementDTO dto) throws HeadlessException {
        try {
            database.createAchievement(dto);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGame(Game game) throws HeadlessException {
        try {
            database.deleteGame(game.getId());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createGame(String displayName) throws HeadlessException {
        try {
            database.createGame(displayName);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Greska pri izvrsavanju upita nad bazom", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, ex.getMessage(),
                    "Niste konektovani nad bazom. Mora se prvo pozvati connect()", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddAchievement;
    private javax.swing.JButton bAddGame;
    private javax.swing.JButton bRemoveAchievement;
    private javax.swing.JButton bRemoveGame;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<Game> lGames;
    private javax.swing.JProgressBar pbLoading;
    private javax.swing.JTable tAchievementsForGame;
    // End of variables declaration//GEN-END:variables
}
