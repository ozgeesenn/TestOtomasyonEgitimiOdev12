import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementException;

public class MySQLRemoteConnection {

    public static void main(String[] args)
    {
        String url = "jdbc:mysql://sql11.freemysqlhosting.net/sql11688140";
        String userName = "sql11688140";
        String password = "iKzi6NzSRT";

        Jdbi jdbi = Jdbi.create(url, userName, password);

        try (Handle handle = jdbi.open()) {

            handle.execute("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, kisiAdi VARCHAR(100), kisiMeslek VARCHAR(100), kisiDogumTarihi VARCHAR(100))");
            handle.execute("INSERT INTO users (kisiAdi, kisiMeslek, kisiDogumTarihi) VALUES (?,?,?)", "Ozge", "Yapay Zeka Muhendisi", "21-05-1995");
            handle.execute("INSERT INTO users (kisiAdi, kisiMeslek, kisiDogumTarihi) VALUES (?,?,?)", "Ali", "Sistem Muhendisi", "06-01-1994");
            handle.execute("INSERT INTO users (kisiAdi, kisiMeslek, kisiDogumTarihi) VALUES (?,?,?)", "Esra", "Radyoloji Uzmani", "14-03-2002");
            handle.execute("INSERT INTO users (kisiAdi, kisiMeslek, kisiDogumTarihi) VALUES (?,?,?)", "Eda", "Ev Hanimi", "01-03-1969");


            System.out.println("Tablodan SELECT(read) islemi: \n");

            String result = handle.createQuery("SELECT kisiAdi FROM users WHERE id = :id")
                    .bind("id",1)
                    .mapTo(String.class)
                    .one();

            System.out.println("Result: " + result);

            System.out.println("Tablodan UPDATE islemi: \n");

            int result2 = handle.createUpdate("UPDATE kisiAdi SET kisiDogumTarihi = 10-03-1969 WHERE kisiAdi = :kisiAdi")
                    .bind("kisiAdi", "Sukran").execute();

            System.out.println("Result2: " + result2);

            System.out.println("Tablodan DELETE islemi: \n");

            int result3= handle.createUpdate("DELETE FROM users WHERE kisiDogumTarihi = 14-03-2002").execute();

            List results4 = handle.createQuery("SELECT * FROM users").mapToMap().list();
            System.out.println("Result4: " + results4);


        } catch (StatementException e) {
            e.printStackTrace();
        }




    }
}

