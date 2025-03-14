package tek.getarrays.employeemanagement.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "userName is required")
    private String userName;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    private String password;
    @Transient
    private String passWordConfirm;
    private String role;
}
