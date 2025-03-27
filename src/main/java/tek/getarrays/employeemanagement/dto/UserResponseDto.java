package tek.getarrays.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tek.getarrays.employeemanagement.Enum.UserStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.util.Set;

@Data
public class UserResponseDto {

    private Long id;

    private String userName;

    private String email;

    @JsonFormat(pattern = "dd-MM-yyy")
    private Date createDate;

    @JsonFormat(pattern = "dd-MM-yyy")
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private Set<String> roles;

}
