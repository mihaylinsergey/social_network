package ru.otus.dialog_service.model;

import lombok.Data;

@Data
public class DialogMessage {
    private String from;
    private String to;
    private String text;
}
