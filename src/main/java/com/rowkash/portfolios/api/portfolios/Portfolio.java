package com.rowkash.portfolios.api.portfolios;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.rowkash.portfolios.api.common.entities.BaseEntity;
import com.rowkash.portfolios.api.users.User;

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
