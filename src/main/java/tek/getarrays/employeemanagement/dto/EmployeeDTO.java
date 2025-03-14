package tek.getarrays.employeemanagement.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @ApiModelProperty(required = true)
    @NotBlank
    @Size(max = 250)
    private String name;


    @ApiModelProperty(required = true)
    @Email
    @NotBlank
    private String email;

    @Size(max = 200)
    private String jobTitle;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;


    private String imageUrl;


}
