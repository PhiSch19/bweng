package at.technikum.bweng.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;



@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, columnDefinition = "varchar(20) default user")
    private String role;


}
