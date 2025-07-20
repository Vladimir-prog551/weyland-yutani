package com.vova.bishop_prototype.controller;

import com.vova.command.dto.CommandRequest;
import com.vova.command.service.CommandExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands")
public class CommandController {

    private final CommandExecutionService commandExecutionService;

    public CommandController(CommandExecutionService commandExecutionService) {
        this.commandExecutionService = commandExecutionService;
    }

    @PostMapping
    public ResponseEntity<Void> executeCommand(@RequestBody CommandRequest commandRequest) {
        commandExecutionService.executeCommand(commandRequest);
        return ResponseEntity.accepted().build();
    }
}
