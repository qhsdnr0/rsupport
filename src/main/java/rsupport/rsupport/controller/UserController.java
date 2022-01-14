package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Transactional
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<String> newUser(@RequestBody UserForm userForm) {
        User user = new User();
        user.setName(userForm.getName());
        userService.createUser(user);
        return ResponseEntity.ok("CREATED");
    }
}
