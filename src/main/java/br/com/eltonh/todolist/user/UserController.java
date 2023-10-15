package br.com.eltonh.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(
            @RequestBody UserModel userModel
    ) {
        userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));

        if (this.userRepository.findByUsername(userModel.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existente no banco de dados!!");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(userModel));
    }
}
