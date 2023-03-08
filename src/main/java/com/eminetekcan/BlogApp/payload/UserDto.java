package com.eminetekcan.BlogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4,message = "Name must be min of 4 characters !!")
    private String name;

    @NotEmpty
    @Size(min = 5, max = 15, message = "Password must be min of 5 chars and max of 15 chars")
    private String password;

    @Email(message = "Your email address not valid !!")
    private String email;

    @NotNull
    private String about;
}
