package tek.getarrays.employeemanagement.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tek.getarrays.employeemanagement.Enum.UserStatus;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;


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

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(updatable = false)
    private Date createDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(updatable = false)
    private Date updateDate;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String role;
}
