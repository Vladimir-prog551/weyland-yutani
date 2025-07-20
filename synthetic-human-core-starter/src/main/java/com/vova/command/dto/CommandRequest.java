package com.vova.command.dto;

import com.vova.command.enums.CommandPriority;
import com.vova.command.exceptions.CommandRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record CommandRequest(String description, CommandPriority commandPriority, String author, String time) {

    public CommandRequest {
        if (description == null || description.isEmpty()) {
            throw new CommandRequestException("Description cannot be empty");
        } else if (commandPriority == null) {
            throw new CommandRequestException("CommandPriority cannot be empty");
        } else if (author == null || author.isEmpty()) {
            throw new CommandRequestException("Author cannot be empty");
        }  else if (time == null || time.isEmpty()) {
            throw new CommandRequestException("Time cannot be empty");
        }

        if (description.length() >= 1000) {
            throw new CommandRequestException("Description length exceed 1000");
        } else if (author.length() >= 100) {
            throw new CommandRequestException("Author length exceed 100");
        } else {
            String regex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(time);
            if (!matcher.matches()) {
                throw new CommandRequestException("Invalid time format");
            }
        }
    }
}
