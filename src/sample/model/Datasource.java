package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    private final String DB_NAME = "theatre.db";
    private final String CONNECTION_STRING = "jdbc:sqlite:/Users/trido/Desktop/JavaProject/Application_Theatre_Version2/" + DB_NAME;

    // ************************************** USER ************************************** //

    private final String USER_COLUMN_ID = "id";
    private final String USER_COLUMN_FIRSTNAME = "first";
    private final String USER_COLUMN_LASTNAME= "last";
    private final String USER_COLUMN_USERNAME = "username";
    private final String USER_COLUMN_PASSWORD = "password";
    private final String USER_COLUMN_EMAIL = "email";
    private final String USER_COLUMN_IMAGE= "image";

    private final String TABLE_USER = "users";

    private final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + " (id INTEGER PRIMARY KEY AUTOINCREMENT "
            + ", first TEXT, last TEXT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, email TEXT UNIQUE, authority INTEGER DEFAULT 0)";

    private final String INSERT_USER = "INSERT INTO " + TABLE_USER + " (" + USER_COLUMN_FIRSTNAME + ", "
            + USER_COLUMN_LASTNAME + ", " + USER_COLUMN_USERNAME + ", "
            + USER_COLUMN_PASSWORD + ", " + USER_COLUMN_EMAIL + ") "
            + "VALUES (?, ?, ?, ?, ?)" ;

    private final String QUERY_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM " + TABLE_USER
            + " WHERE " + USER_COLUMN_USERNAME + " = ? AND " + USER_COLUMN_PASSWORD + " = ?";

    private final String UPDATE_USER_BY_ID = "UPDATE " + TABLE_USER + " SET " + USER_COLUMN_FIRSTNAME + " = ?, "
            + USER_COLUMN_LASTNAME + " = ?, " + USER_COLUMN_USERNAME + " = ?, "
            + USER_COLUMN_PASSWORD + " = ?, " + USER_COLUMN_EMAIL + " = ?, "
            + USER_COLUMN_IMAGE + " = ? WHERE " + USER_COLUMN_ID + " = ?";

    private final String QUERY_USERS = "SELECT * FROM " + TABLE_USER;
    // ************************************** MOVIE ************************************** //

    private final String MOVIE_COLUMN_TITLE = "title";
    private final String MOVIE_COLUMN_DESCRIPTION= "description";
    private final String MOVIE_COLUMN_DATE = "date";
    private final String MOVIE_COLUMN_TIME = "time";
    private final String MOVIE_COLUMN_URL = "url";
    private final String MOVIE_COLUMN_ID = "id";

    private final String TABLE_MOVIE = "movies";

    private final String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIE + " (id INTEGER PRIMARY KEY AUTOINCREMENT "
            + ", title TEXT NOT NULL, description TEXT NOT NULL, date TEXT, time TEXT NOT NULL, url TEXT NOT NULL)";

    private final String INSERT_MOVIE = "INSERT INTO " + TABLE_MOVIE + " (" + MOVIE_COLUMN_TITLE
            + ", " + MOVIE_COLUMN_DESCRIPTION + ", " + MOVIE_COLUMN_DATE + ", " + MOVIE_COLUMN_TIME
            + ", " + MOVIE_COLUMN_URL + ") VALUES (?, ?, ?, ?, ?)";

    private final String QUERY_MOVIES = "SELECT * FROM " + TABLE_MOVIE;

    private final String UPDATE_MOVIE = "UPDATE " + TABLE_MOVIE + " SET " + MOVIE_COLUMN_TITLE
            + " = ?, " + MOVIE_COLUMN_DESCRIPTION + " = ?, " + MOVIE_COLUMN_DATE
            + " = ?, " + MOVIE_COLUMN_TIME + " = ?, " + MOVIE_COLUMN_URL + " = ? "
            + " WHERE id = ?";

    private final String DELETE_MOVIE_BY_ID = "DELETE FROM " + TABLE_MOVIE + " WHERE " + MOVIE_COLUMN_ID + " = ?";

    // ************************************** SEAT ************************************** //

    private final String TABLE_SEAT = "seats";

    private final String SEAT_COLUMN_NUMBER = "number";

    private final String CREATE_SEAT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEAT + "(id INTEGER PRIMARY KEY AUTOINCREMENT "
            + ", number TEXT NOT NULL UNIQUE)";

    private final String DROP_SEAT_TABLE = "DROP TABLE " + TABLE_SEAT;

    private final String INSERT_SEAT = "INSERT INTO " + TABLE_SEAT + " (" + SEAT_COLUMN_NUMBER + ") VALUES (?)";

    private final String QUERY_SEAT = "SELECT " + SEAT_COLUMN_NUMBER + " FROM " + TABLE_SEAT;



    // ************************************** BOOK SHOW ************************************** //
    private final String TABLE_BOOKSHOW = "bookshows";

    private final String BOOKSHOW_COLUMN_ID = "id";
    private final String BOOKSHOW_COLUMN_USER = "user";
    private final String BOOKSHOW_COLUMN_MOVIE = "movie";
    private final String BOOKSHOW_COLUMN_SEAT = "seat";

    private final String CREATE_BOOKSHOW_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKSHOW + "(id INTEGER PRIMARY KEY AUTOINCREMENT "
            + ", user INTEGER NOT NULL, movie INTEGER NOT NULL, seat TEXT NOT NULL)";

    private final String INSERT_BOOKSHOW = "INSERT INTO " + TABLE_BOOKSHOW + " (" + BOOKSHOW_COLUMN_USER
            + ", " + BOOKSHOW_COLUMN_MOVIE + ", " + BOOKSHOW_COLUMN_SEAT + ") VALUES(?, ?, ?)";

    private final String QUERY_BOOKSHOWS = "SELECT " + BOOKSHOW_COLUMN_ID + ", " + BOOKSHOW_COLUMN_USER + ", " + BOOKSHOW_COLUMN_MOVIE
            + ", " + BOOKSHOW_COLUMN_SEAT + " FROM " + TABLE_BOOKSHOW;

    private final String DELETE_BOOKSHOW_BY_ID = "DELETE FROM " + TABLE_BOOKSHOW + " WHERE user = ? AND movie = ? AND seat = ?";

    //SELECT movies.title, seats.number, movies.date, movies.time
    //FROM bookshows INNER JOIN movies ON bookshows.movie = movies.id
    //INNER JOIN seats ON bookshows.seat = seats.number
    //WHERE bookshows.user = 3

    private final String BOOKING_HISTORY_BY_USER_ID = "SELECT " + TABLE_MOVIE + "." + MOVIE_COLUMN_TITLE
            + ", " + TABLE_MOVIE + "." + MOVIE_COLUMN_DATE
            + ", " + TABLE_MOVIE + "." + MOVIE_COLUMN_TIME
            + ", " + TABLE_SEAT + "." + SEAT_COLUMN_NUMBER + " FROM " + TABLE_BOOKSHOW
            + " INNER JOIN " + TABLE_MOVIE + " ON " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_MOVIE
            + " = " + TABLE_MOVIE + "." + MOVIE_COLUMN_ID
            + " INNER JOIN " + TABLE_SEAT + " ON " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_SEAT
            + " = " + TABLE_SEAT + "." + SEAT_COLUMN_NUMBER
            + " WHERE " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_USER + " = ?";

    //SELECT users.first, users.last, movies.title, movies.date,
    //	movies.time, bookshows.seat FROM bookshows
    //	INNER JOIN users ON bookshows.user = users.id
    //	INNER JOIN movies ON bookshows.movie = movies.id
    private final String QUERY_BOOKSHOWS_FOR_ADMIN = "SELECT " + TABLE_USER + "." + USER_COLUMN_FIRSTNAME
            + ", " + TABLE_USER + "." + USER_COLUMN_LASTNAME
            + ", " + TABLE_MOVIE + "." + MOVIE_COLUMN_TITLE
            + ", " + TABLE_MOVIE + "." + MOVIE_COLUMN_DATE
            + ", " + TABLE_MOVIE + "." + MOVIE_COLUMN_TIME
            + ", " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_SEAT + " FROM " + TABLE_BOOKSHOW
            + " INNER JOIN " + TABLE_USER + " ON " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_USER
            + " = " + TABLE_USER + "." + USER_COLUMN_ID
            + " INNER JOIN " + TABLE_MOVIE + " ON " + TABLE_BOOKSHOW + "." + BOOKSHOW_COLUMN_MOVIE
            + " = " + TABLE_MOVIE + "." + MOVIE_COLUMN_ID + " ORDER BY " + USER_COLUMN_FIRSTNAME + " ASC";
    private PreparedStatement insertIntoUsers;
    private PreparedStatement queryUserByUsernameAndPassword;
    private PreparedStatement updateUserById;

    private PreparedStatement insertIntoMovies;
    private PreparedStatement updateMovieById;
    private PreparedStatement deleteMovieById;

    private PreparedStatement insertIntoSeats;

    private PreparedStatement insertIntoBookShows;
    private PreparedStatement deleteBookshowById;
    private PreparedStatement bookingHistoryByUserId;

    private Connection conn;
    private static Datasource instance = new Datasource();

    private ObservableList<Movie> movies;


    private Datasource(){

    }

    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertIntoUsers = conn.prepareStatement(INSERT_USER);
            queryUserByUsernameAndPassword = conn.prepareStatement(QUERY_USER_BY_USERNAME_AND_PASSWORD);
            updateUserById = conn.prepareStatement(UPDATE_USER_BY_ID);

            insertIntoMovies = conn.prepareStatement(INSERT_MOVIE);
            updateMovieById = conn.prepareStatement(UPDATE_MOVIE);
            deleteMovieById = conn.prepareStatement(DELETE_MOVIE_BY_ID);

            insertIntoSeats = conn.prepareStatement(INSERT_SEAT);

            insertIntoBookShows = conn.prepareStatement(INSERT_BOOKSHOW);
            deleteBookshowById = conn.prepareStatement(DELETE_BOOKSHOW_BY_ID);
            bookingHistoryByUserId = conn.prepareStatement(BOOKING_HISTORY_BY_USER_ID);

            return true;
        }catch (SQLException e){
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close(){
        if(conn != null){
            try {
                if(insertIntoUsers != null){
                    insertIntoUsers.close();
                }
                if(queryUserByUsernameAndPassword != null){
                    queryUserByUsernameAndPassword.close();
                }
                if(updateUserById != null){
                    updateUserById.close();
                }
                if(insertIntoMovies != null){
                    insertIntoMovies.close();
                }
                if(updateMovieById != null){
                    updateMovieById.close();
                }
                if(deleteMovieById != null){
                    deleteMovieById.close();
                }
                if(insertIntoSeats != null){
                    insertIntoSeats.close();
                }
                if(insertIntoBookShows != null){
                    insertIntoBookShows.close();
                }
                if(deleteBookshowById != null){
                    deleteBookshowById.close();
                }
                if(bookingHistoryByUserId != null){
                    bookingHistoryByUserId.close();
                }

                conn.close();
            }catch (SQLException e){
                System.out.println("Couldn't close database properly: " + e.getMessage());
            }
        }
    }

    public static Datasource getInstance(){
        return instance;
    }

    public boolean addUser(String firstName, String lastName, String username, String password, String email){
        try{
            insertIntoUsers.setString(1, firstName);
            insertIntoUsers.setString(2, lastName);
            insertIntoUsers.setString(3, username);
            insertIntoUsers.setString(4, password);
            insertIntoUsers.setString(5, email);

            int affectedRow = insertIntoUsers.executeUpdate();
            if(affectedRow != 1){
                System.out.println("Affected row > 1");
                return false;
            }else{
                return true;
            }

        }catch (SQLException e){
            System.out.println("Error from insert user: " + e.getMessage());
            return false;
        }
    }

    public User queryUserByUserNameAndPass(String username, String password){
        try{
            queryUserByUsernameAndPassword.setString(1, username);
            queryUserByUsernameAndPassword.setString(2, password);

            ResultSet result = queryUserByUsernameAndPassword.executeQuery();
            if(result.next()){
                User user = new User(result.getInt(1), result.getString(2),
                                     result.getString(3), result.getString(4),
                                     result.getString(5), result.getString(6),
                                     result.getInt(7));
                user.setImagePath(result.getString(8));
                return user;
            }else{
                return null;
            }
        }catch (SQLException e){
            System.out.println("Error from queryUserByUserNameAndPass(): " + e.getMessage());
            return null;
        }
    }

    public boolean updateUser(String first, String last, String username, String password, String email, int id, String imagePath){
        try {

            updateUserById.setString(1, first);
            updateUserById.setString(2, last);
            updateUserById.setString(3, username);
            updateUserById.setString(4, password);
            updateUserById.setString(5, email);
            updateUserById.setString(6, imagePath);
            updateUserById.setInt(7, id);

            int affectedRow = updateUserById.executeUpdate();
            if(affectedRow == 1){
                System.out.println("Successfully updated user");
                return true;
            }else{
                System.out.println("Something went wrong while updating");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error from updateUser(): " + e.getMessage());
            return false;
        }
    }

    public ObservableList<User> queryUsers(){
        try(Statement statement = conn.createStatement()){
            ResultSet results = statement.executeQuery(QUERY_USERS);
            ObservableList<User> users = FXCollections.observableArrayList();
            while(results.next()){
                users.add(new User(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getString(6),
                        results.getInt(7)

                ));
            }
            return users;
        }catch (SQLException e){
            System.out.println("Error from queryUsers(): " + e.getMessage());
            return null;
        }
    }

    public boolean addMovie(String title, String description, String date, String time, String url){
        try{
            insertIntoMovies.setString(1, title);
            insertIntoMovies.setString(2, description);
            insertIntoMovies.setString(3, date);
            insertIntoMovies.setString(4, time);
            insertIntoMovies.setString(5, url);
            int affectedRow = insertIntoMovies.executeUpdate();

            if(affectedRow != 1){
                System.out.println("Affected row > 1");
                return false;
            }else{
                movies.add(new Movie(title, description, date, time, url));
                return true;
            }
        }catch (SQLException e){
            System.out.println("Error from addMovie(): " + e.getMessage());
            return false;
        }
    }

    public ObservableList<Movie> listMovies(){
        try(Statement statement = conn.createStatement()){
            ResultSet results = statement.executeQuery(QUERY_MOVIES);
            movies = FXCollections.observableArrayList();
            while (results.next()){
                int id = results.getInt(1);
                String title = results.getString(2);
                String desc = results.getString(3);
                String date = results.getString(4);
                String time = results.getString(5);
                String url = results.getString(6);
                Movie newMovie = new Movie(title, desc, date, time, url);
                newMovie.setId(id);
                movies.add(newMovie);
            }
            return movies;
        }catch (SQLException e){
            System.out.println("Error from listMovies(): " + e.getMessage());
            return null;
        }
    }

    public boolean updateMovieById(int movieId, String title, String desc, String date, String time, String url){
        try{
           updateMovieById.setString(1, title);
           updateMovieById.setString(2, desc);
           updateMovieById.setString(3, date);
           updateMovieById.setString(4, time);
           updateMovieById.setString(5, url);
           updateMovieById.setInt(6, movieId);

           int affectedRow = updateMovieById.executeUpdate();

           if(affectedRow == 1){
               System.out.println("Update successfully");
               listMovies();
               return true;
           }else{
               System.out.println("Something went wrong");
               return false;
           }
        }catch (SQLException e){
            System.out.println("Error from updateMovieById(): " + e.getMessage());
            return false;
        }
    }

    public boolean deleteMovieById(int id){
        try{
            deleteMovieById.setInt(1, id);
            int affectedRow = deleteMovieById.executeUpdate();
            if(affectedRow != 1){
                System.out.println("Some thing went wrong while deleting movie");
                return false;
            }else{
                System.out.println("Delete successfully");
                return true;
            }

        }catch (SQLException e){
            System.out.println("Error from deleteMovieById(): " + e.getMessage());
            return false;
        }
    }

    public boolean createTableUsers(){
        try(Statement statement = conn.createStatement()){
            statement.execute(CREATE_USER_TABLE);
            return true;

        }catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }

    public boolean addSeats(String seatNumber){
        try {
            insertIntoSeats.setString(1, seatNumber);
            int afftectedRow = insertIntoSeats.executeUpdate();
            if(afftectedRow != 1){
                System.out.println("Error from insert seat");
                return false;
            }
            System.out.println("Successfully added seat");
            return true;
        }catch (SQLException e){
            System.out.println("Error from addSeats(): " + e.getMessage());
            return false;
        }
    }

    // get all the seats from table seats and assign them in List
    public List<Seat> getAllSeats(){
        List<Seat> seats = new ArrayList<>();

        try(Statement statement = conn.createStatement()){
            ResultSet results = statement.executeQuery(QUERY_SEAT);
            while(results.next()){
                Seat seat = new Seat(results.getString(1));
                seats.add(seat);
            }
            return seats;
        }catch (SQLException e){
            System.out.println("Error from getAllSeats(): " + e);
            return null;
        }
    }

    public boolean addBookShows(int userId, int movieId, String seatNumber){
        try{
            insertIntoBookShows.setInt(1, userId);
            insertIntoBookShows.setInt(2, movieId);
            insertIntoBookShows.setString(3, seatNumber);

            int affectedRow = insertIntoBookShows.executeUpdate();
            if(affectedRow != 1){
                System.out.println("Something went wrong while adding booking");
                return false;
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error from addBookShows(): " + e);
            return false;
        }
    }

    public List<Booking> getBookShows(){
        List<Booking> bookshows = new ArrayList<>();
        try(Statement statement = conn.createStatement()){
            ResultSet results = statement.executeQuery(QUERY_BOOKSHOWS);
            while(results.next()){
                int bookshowId = results.getInt(1);
                int userId = results.getInt(2);
                int movieId = results.getInt(3);
                String seatNumber = results.getString(4);

                Booking bookshow = new Booking(userId, movieId, seatNumber);
                bookshows.add(bookshow);
            }
            return bookshows;
        }catch (SQLException e){
            System.out.println("Error from getBookShows(): " + e.getStackTrace());
            return null;
        }
    }

    public boolean deleteBookShow(int userId, int movieId, String seatNumber){
        try{
            deleteBookshowById.setInt(1, userId);
            deleteBookshowById.setInt(2, movieId);
            deleteBookshowById.setString(3, seatNumber);
            int affectedRow = deleteBookshowById.executeUpdate();
            if(affectedRow != 1){
                System.out.println("Something went wrong while deleting the bookshow");
                return false;
            }
            return true;
        }catch (SQLException e){
            System.out.println("Error From deleteBookShow(): " + e.getStackTrace());
            return false;
        }
    }

    public ObservableList<BookingHistory> queryBookingHistory(int userId){
        try{
            bookingHistoryByUserId.setInt(1, userId);
            ResultSet results = bookingHistoryByUserId.executeQuery();
            ObservableList<BookingHistory> bookingHistoryList = FXCollections.observableArrayList();
            while(results.next()){
                String movieTitle = results.getString(1);
                String date = results.getString(2);
                String time = results.getString(3);
                String seatNumber = results.getString(4);
                bookingHistoryList.add(new BookingHistory(movieTitle, seatNumber, date, time));
            }
            return bookingHistoryList;
        }catch (SQLException e){
            System.out.println("Error From queryBookingHistory(): " + e.getMessage());
            return null;
        }
    }

    public ObservableList queryBookShowsForAdmin(){
        try(Statement statement = conn.createStatement()){
            ResultSet results = statement.executeQuery(QUERY_BOOKSHOWS_FOR_ADMIN);
            ObservableList<BookShow> bookShows = FXCollections.observableArrayList();
            while(results.next()){
                bookShows.add(new BookShow(
                        results.getString(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getString(6)
                        ));
            }
            return bookShows;
        }catch (SQLException e){
            System.out.println("Error from queryBookShowsForAdmin: " + e.getMessage());
            return null;
        }
    }

    public boolean createTableMovies(){
        try(Statement statement = conn.createStatement()){
            statement.execute(CREATE_MOVIE_TABLE);
            return true;
        }catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }

    public boolean createTableSeats(){
        try(Statement statement = conn.createStatement()){
            statement.execute(CREATE_SEAT_TABLE);
            return true;
        }catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }

    public boolean dropTableSeats(){
        try(Statement statement = conn.createStatement()){
            statement.execute(DROP_SEAT_TABLE);
            System.out.println("Drop table seats successfully");
            return true;

        }catch (SQLException e){
            System.out.println("Error From Drop Table Seats" + e.getMessage());
            e.getStackTrace();
            return false;
        }
    }

    public boolean createTableBookShows(){
        try(Statement statement = conn.createStatement()){
            statement.execute(CREATE_BOOKSHOW_TABLE);
            return true;
        }catch (SQLException e){
            e.getStackTrace();
            return false;
        }
    }
}
