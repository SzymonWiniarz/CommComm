package pl.simcode.comm_comm.users.api.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.simcode.comm_comm.common.result.ErrorResultException;
import pl.simcode.comm_comm.users.api.UsersModule;
import pl.simcode.comm_comm.users.api.dto.NewUserDTO;
import pl.simcode.comm_comm.users.api.dto.UserDTO;

@RestController
@RequestMapping("/api/v1/users")
class UsersController {

    private final UsersModule usersModule;

    UsersController(UsersModule usersModule) {
        this.usersModule = usersModule;
    }

    @PostMapping
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody NewUserDTO newUser) {
        var userRegistrationResult = usersModule.registerNewUser(newUser);

        if (userRegistrationResult.isError()) {
            throw new ErrorResultException(userRegistrationResult.getError());
        }

        var cretedUser = userRegistrationResult.getPayload();

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cretedUser.userId())
                .toUri();

        return ResponseEntity.created(location)
                .body(cretedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable String id) {
        return usersModule.findUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
