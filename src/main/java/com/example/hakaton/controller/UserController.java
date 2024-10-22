package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.UserCommand;
import com.example.hakaton.dto.entity.UserQuery;
import com.example.hakaton.service.entity.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserQuery> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "25") int size,
                                  @RequestParam(required = false) Optional<Boolean> sortOrder,
                                  @RequestParam(required = false) String sortBy) {
        return userService.getAll(page, size, sortOrder, sortBy);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserQuery> create(@Valid @RequestBody UserCommand command) {
        UserQuery saveEntity = userService.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserQuery getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/getByPin")
    @PreAuthorize("hasRole('ADMIN')")
    public UserQuery getByUserPin(@RequestParam String pin) {
        return userService.getByUserPin(pin);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserQuery> updateUser(@Valid @RequestBody UserCommand command) {
        UserQuery updatedEntity = userService.update(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletedUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
