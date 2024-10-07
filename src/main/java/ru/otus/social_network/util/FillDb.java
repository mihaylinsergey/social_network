package ru.otus.social_network.util;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FillDb {
    private static final String CSV_FILE_PATH = "people.v2.csv";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/social_network?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(CSV_FILE_PATH), "UTF-8"));
             Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            String line;
            String sql = "INSERT INTO public.users (first_name, second_name, birthdate, city) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length >= 3) {
                    String[] names = values[0].split(" ");
                    String firstName = names[0];
                    String secondName = names.length > 1 ? names[1] : "";
                    String birthdate = values[1];
                    String city = values[2];

                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, secondName);
                    preparedStatement.setDate(3, java.sql.Date.valueOf(birthdate));
                    preparedStatement.setString(4, city);

                    preparedStatement.executeUpdate();
                }
            }

            System.out.println("Data has been inserted successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
