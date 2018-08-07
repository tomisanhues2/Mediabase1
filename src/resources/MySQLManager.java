package resources;

import objects.List;
import objects.Movie;
import parser.MovieParser;

import javax.swing.plaf.nimbus.State;
import java.io.IOError;
import java.sql.*;
import java.util.ArrayList;

public class MySQLManager implements IObservableLists {


    private final String URL = "jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9250882?serverTimezone=UTC";  // jdbc:subprotocol:subname
    private final String USER = "sql9250882";
    private final String PASSWORD = "az2htT8htN";
    private static int totalListCount = 0;
    private static int currentDatabaseID;

    private static Connection connection;

    public MySQLManager() {
        try {
            startConnection();
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    private void startConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection successful");
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed())
                connection.close();
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    public static void createMoviesFromList(ArrayList<Movie> movies, String listName, String authorName) {
        try {
            int newListID = getNextAvailableID();
            insertRowIntoLists(newListID, listName, authorName);
            String queryTable = "CREATE TABLE movie_list_" + newListID + " (MovieDB_ID INT);";
            String queryFields = "INSERT INTO movie_list_" + newListID + " (MovieDB_ID) VALUES(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(queryFields);
            preparedStatement.execute(queryTable);
            connection.setAutoCommit(false);
            for (int i = 0; i < movies.size(); i++) {
                preparedStatement.setInt(1, movies.get(i).getDatabaseID());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.setAutoCommit(true);
            System.out.println("Successfully saved");
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    private static void getCommentsFromDB(int listID) {

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT Comments FROM list_comments_" + listID + ";";
            ResultSet resultSet1 = statement.executeQuery("SHOW TABLES LIKE 'list_comments_" + currentDatabaseID + "';");
            if (!resultSet1.next()) {
                IObservableLists.addCommentToObservableList("This is one of the best lists i've seen in my life, it's is just so perfect and contains the best movies of all times, they are extra thick movies");
            } else {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    IObservableLists.addCommentToObservableList(resultSet.getString("Comments"));
                }

            }
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    public static void insertCommentToDB() {
        try {

            Statement statement = connection.createStatement();
            System.out.println(currentDatabaseID);
            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE 'list_comments_" + currentDatabaseID + "';");
            if (!resultSet.next()) {
                statement.execute("CREATE TABLE list_comments_" + currentDatabaseID + "(Comments text);");
                statement.execute("CREATE UNIQUE INDEX list_comments_1_Comments_u1 ON list_comments_1 (Comments(255));");
            }
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO list_comments_" + currentDatabaseID + "(Comments) VALUES(?)");
            for (int i = 0; i < IObservableLists.getCommentsObservableList().size(); i++) {
                preparedStatement.setString(1,IObservableLists.getCommentsObservableList().get(i));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.setAutoCommit(true);
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    public static int getNextAvailableID() {
        Statement statement = null;
        String query = "SELECT ID FROM movie_lists;";
        int newListID = 1;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                newListID++;
            }
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
        return newListID;
    }

    private static void insertRowIntoLists(int listID, String listName, String authorName) {
        try {
            String query = "INSERT INTO movie_lists (ID,ListName,ListAuthor) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, listID);
            statement.setString(2, listName);
            statement.setString(3, authorName);
            statement.execute();
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    public static void getMoviesFromList(int listID) {
        currentDatabaseID = listID + 1;
        getCommentsFromDB(currentDatabaseID);

        Statement statement = null;
        String query = "SELECT * FROM movie_list_" + (currentDatabaseID);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int MOVIEDB_ID;
            while (resultSet.next()) {
                MOVIEDB_ID = resultSet.getInt("MOVIEDB_ID");
                new MovieParser(MOVIEDB_ID);
            }
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

    public void getListsFromList() {
        Statement statement;
        String query = "SELECT * FROM movie_lists;";

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int listID;
            String listName;
            String listAuthor;
            while (resultSet.next()) {
                listID = resultSet.getInt("ID");
                listName = resultSet.getString("ListName");
                listAuthor = resultSet.getString("ListAuthor");
                IObservableLists.addListToObservableList(new List(listID, listName, listAuthor));
                totalListCount++;
            }
        } catch (SQLException ignore) {
            ignore.printStackTrace();
        }
    }

}
