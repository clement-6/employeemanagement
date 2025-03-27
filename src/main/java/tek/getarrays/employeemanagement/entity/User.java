package tek.getarrays.employeemanagement.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tek.getarrays.employeemanagement.Enum.UserStatus;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.util.Set;

import static tek.getarrays.employeemanagement.utils.ErrorMessages.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = NAME_REQUIRED)
    private String userName;

    @Column(nullable = false, unique = true)
    @Email(message = EMAIL_VALID)
    @NotBlank(message = EMAIL_REQUIRED)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Role> roles;
}
