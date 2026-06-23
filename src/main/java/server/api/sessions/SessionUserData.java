package server.api.sessions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.api.users.User;

@Getter
@Setter
@NoArgsConstructor
public class SessionUserData {
  private Long userId;
  private String email;

  public SessionUserData(User user) {
    this.userId = user.getId();
    this.email = user.getEmail();
  }
}
