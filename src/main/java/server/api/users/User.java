package server.api.users;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import server.api.common.entities.BaseEntity;
import server.api.portfolios.Portfolio;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(nullable = false)
  String name;

  @Column(unique = true, nullable = false)
  String email;

  @Column(nullable = false)
  String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Portfolio> portfolios = new ArrayList<>();
}
