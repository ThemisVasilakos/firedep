package gr.agileadvisors.firedep.firedep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRegisterDto implements Serializable {

    private String username;
    private String password;
    private String role;
}
