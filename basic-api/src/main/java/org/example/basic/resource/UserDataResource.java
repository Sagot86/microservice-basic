package org.example.basic.resource;

import org.example.basic.dto.UserDto;
import org.example.basic.dto.UserDtoFull;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * UserDataResource.
 *
 * @author Igor_Orlov
 */
@RequestMapping("/user")
public interface UserDataResource {

    /**
     * Update existing user information.
     *
     * @param userDto dto with user id, country and money params.
     */
    @PutMapping("/update")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    void updateUser(@RequestBody UserDto userDto);

    /**
     * Get full user info by user id.
     *
     * @param uid user ID
     * @return full user dto
     */
    @GetMapping("/info")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    UserDtoFull getUserInfo(@RequestParam UUID uid);

    /**
     * Add user activity information.
     *
     * @param uid      user if
     * @param activity activity value
     */
    @PutMapping("/activity")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    void updateActivity(@RequestParam UUID uid,
                        @RequestParam Long activity);


}
