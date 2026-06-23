package server.api.portfolios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import server.api.common.entities.BaseEntity;
import server.api.users.User;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "portfolios")
public class Portfolio extends BaseEntity {

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
