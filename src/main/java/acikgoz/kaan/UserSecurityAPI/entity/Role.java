package acikgoz.kaan.UserSecurityAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Getter
    public enum RoleType {
        EMPLOYEE("Employee"),
        ADMIN("Admin");

        private final String name;

        RoleType(String name) {
            this.name = name;
        }

    }

}