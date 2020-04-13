package org.zincat.covidmap.controllers;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zincat.covidmap.dto.requests.CreateUserRequest;
import org.zincat.covidmap.dto.responses.UserSearchResponse;
import org.zincat.covidmap.models.ZincatUser;
import org.zincat.covidmap.services.UserService;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    @ApiOperation(value = "${UserController.register}")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 400, message = "Something went wrong"), //
                    @ApiResponse(code = 403, message = "Access denied"), //
                    @ApiResponse(code = 422, message = "Username is already in use"), //
                    @ApiResponse(code = 500, message = "Expired or invalid JWT token")
            })
    public String register(@ApiParam("Register User") @RequestBody CreateUserRequest user)
    {
        return userService.register(modelMapper.map(user, ZincatUser.class));
    }

    @PostMapping("/login")
    @ApiOperation(value = "${UserController.login}")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 400, message = "Something went wrong"), //
                    @ApiResponse(code = 422, message = "Invalid username/password supplied")
            })
    public String login(@ApiParam("Username") @RequestParam String username, @ApiParam("Password") @RequestParam String password)
    {
        return userService.login(username, password);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @ApiOperation(value = "${UserController.delete}")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 404, message = "The user doesn't exist"),
                    @ApiResponse(code = 500, message = "Expired or invalid JWT token")
            })
    public String delete(@ApiParam("Username") @PathVariable String username)
    {
        userService.delete(username);
        return username + " was successfully deleted!";
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @ApiOperation(value = "${UserController.search}", response = UserSearchResponse.class)
    @ApiResponses(value =
            {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 404, message = "The user doesn't exist"),
                    @ApiResponse(code = 500, message = "Expired or invalid JWT token")
            })
    public UserSearchResponse search(@ApiParam("Username") @PathVariable String username)
    {
        return modelMapper.map(userService.search(username), UserSearchResponse.class);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @ApiOperation(value = "${UserController.update}")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 400, message = "Something went wrong"),
                    @ApiResponse(code = 403, message = "Access denied"),
                    @ApiResponse(code = 404, message = "The user doesn't exist"),
                    @ApiResponse(code = 500, message = "Expired or invalid JWT token")
            })
    public String update(@ApiParam("Search Key") @PathVariable long id, @ApiParam("New Credentials") @RequestBody CreateUserRequest updatedCredentials)
    {
        return userService.update(id, modelMapper.map(updatedCredentials, ZincatUser.class));
    }


}
