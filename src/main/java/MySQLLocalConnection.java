import java.sql.*;

public class MySQLLocalConnection {

    static String url = "jdbc:mysql://localhost:3306/sys";
    static String username = "root";
    static String password = "test123";
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    static Connection connection = null;

    public void connectionToDB(String query) {

        try {
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {

                System.out.println("DB bağlantısı başarılı");

                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    String User_ID = resultSet.getString("User_ID");
                    String First_Name = resultSet.getString("First_Name");
                    String Last_Name = resultSet.getString("Last_Name");
                    String Age = resultSet.getString("Age");
                    String Manager = resultSet.getString("Manager");

                    System.out.println("User ID: " + User_ID + ", Kisi Adi: " + First_Name + ", Kisi Soyadi: " + Last_Name + ", Yas: " + Age + ", Yönetici: " + Manager + " ");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

        public void kisiSorgulama(String kisi) {
            String query = "SELECT * FROM sys.list WHERE Manager IN (" + kisi + ")";
            connectionToDB(query);
        }

    public static void main(String[] args) {

        MySQLLocalConnection connectToLocalDB = new MySQLLocalConnection();
        connectToLocalDB.kisiSorgulama("'Ali'");

    }

}
