package tek.getarrays.employeemanagement.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "le nom ne doit pas etre vide")
    @Size(max = 250)
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "l'email doit etre valide")
    @NotBlank
    private String email;

    @Size(max = 200)
    private String jobTitle;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;

    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private String codeEmployee;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyy")
    private Date createDate;

    @UpdateTimestamp
    @JsonFormat(pattern = "dd-MM-yyy")
    @Column(updatable = false)
    private Date updateDate;
}
