package hexagonstore.knockback.database.providers;

import hexagonstore.knockback.KnockbackPlugin;
import hexagonstore.knockback.database.Database;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL implements Database {

    private Connection connection;

    @Override
    public Connection getConnection() {
        return connection;
    }

    private String table, host, user, pass, database;

    public MySQL(String table, String host, String user, String pass, String database) {
        this.table = table;
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.database = database;
    }

    @Override
    public void open() {
        String url = "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(url, user, pass);
            createTable(table);
            Bukkit.getConsoleSender().sendMessage("§2[HexagonClearPlot] §aConexão com MySQL estabelecida com sucesso.");
        } catch (SQLException ex) {
            Bukkit.getConsoleSender().sendMessage("§4[HexagonClearPlot] §cHouve um erro ao conectar-se com o MySQL!");
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(KnockbackPlugin.getPlugin());
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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