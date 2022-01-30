package hexagonstore.knockback.database.providers;

import hexagonstore.knockback.KnockbackPlugin;
import hexagonstore.knockback.database.Database;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.*;

public class SQLite implements Database {

    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    private String table;

    public SQLite(String table) {
        this.table = table;
    }

    @Override
    public void open() {
        File file = new File(KnockbackPlugin.getPlugin().getDataFolder(), "database.db");
        String url = "jdbc:sqlite:" + file;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            createTable(table);
            Bukkit.getConsoleSender().sendMessage("§2[HexagonClearPlot] §aConexão com SQLite estabelecida com sucesso.");
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§4[HexagonClearPlot] §cHouve um erro ao conectar-se com o SQLite!");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(KnockbackPlugin.getPlugin());
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String table) {
        try {
            PreparedStatement stm = this.connection.prepareStatement(
                    table
            );
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}