package server.api.sessions;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;
import server.api.users.User;

@Service
public class SessionsService {
  private final RedisIndexedSessionRepository sessionRepository;

  public SessionsService(RedisIndexedSessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public String createSession(User user) {
    RedisIndexedSessionRepository.RedisSession session = sessionRepository.createSession();
    SessionUserData userData = new SessionUserData(user);
    session.setAttribute("userData", userData);
    session.setAttribute(
        FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, user.getEmail());
    sessionRepository.save(session);

    return session.getId();
  }

  public void updateSession(RedisIndexedSessionRepository.RedisSession session) {
    session.changeSessionId();
    sessionRepository.save(session);
  }

  public RedisIndexedSessionRepository.RedisSession getSessionById(String id) {
    return sessionRepository.findById(id);
  }

  public void deleteSession(String id) {
    sessionRepository.deleteById(id);
  }
}
