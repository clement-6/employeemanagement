package tek.getarrays.employeemanagement.employee.Entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String jobTitle;
    private String phone;
    private String address;
    private String imageUrl;
    @Column(nullable = false, updatable = false)
    private String codeEmployee;
}
