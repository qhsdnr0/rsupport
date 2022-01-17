package rsupport.rsupport.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.domain.User;
import rsupport.rsupport.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;

    @BeforeEach
    void setUp() throws Exception {
        User user = new User();
        user.setName("user");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        userRepository.deleteAll();

    }

    @Test
    void CreateUser() {
        User user = new User();
        user.setName("user1");
        userService.createUser(user);
        assertEquals(user.getId(), userRepository.getById(user.getId()).getId());
    }

    @Test
    void getUser() {
        assertEquals("user", userRepository.findAll().get(0).getName());
    }
}