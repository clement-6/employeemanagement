package tek.getarrays.employeemanagement.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tek.getarrays.employeemanagement.Enum.UserStatus;
import tek.getarrays.employeemanagement.utils.ErrorMessages;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;


@Data
public class UserDto {

    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank
    private String userName;

    @ApiModelProperty(required = true)
    @Email(message = EMAIL_VALID)
    @NotBlank(message = EMAIL_REQUIRED)
    private String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = PASSWORD_REQUIRED)
    private String password;

    @Transient
    private String passWordConfirm;


    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(updatable = false)
    private Date createDate;


    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(updatable = false)
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String role;
}
