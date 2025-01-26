package ru.otus.social_network.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class FillTablePosts {
    private static final String CSV_FILE_PATH = "posts.txt";
    private static final String DB_URL = "jdbc:postgresql://localhost:15431/social_network?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // Чтение данных из файла
            String content = new String(Files.readAllBytes(Paths.get(CSV_FILE_PATH)));

            // Подключение к базе данных
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // Разделение текста на предложения
                String[] sentences = content.split("\\. ");

                // Подготовка SQL-запроса
                String sql = "INSERT INTO posts (id, text, author_user_id) VALUES (?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    for (String sentence : sentences) {
                        // Генерация уникального идентификатора для поста и автора
                        UUID postId = UUID.randomUUID();
                        UUID authorId = UUID.fromString("6ed52161-7487-4094-90cb-20f321bf08f8");

                        // Установка параметров запроса
                        preparedStatement.setObject(1, postId);
                        preparedStatement.setString(2, sentence.trim());
                        preparedStatement.setObject(3, authorId);

                        // Выполнение запроса
                        preparedStatement.executeUpdate();
                    }
                }
            }

            System.out.println("Data has been inserted successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
