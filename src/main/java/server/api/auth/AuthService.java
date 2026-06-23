package server.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import server.api.auth.dto.AuthLoginDto;
import server.api.auth.dto.AuthRegisterDto;
import server.api.auth.dto.AuthResponse;
import server.api.common.exceptions.BadRequestException;
import server.api.security.JwtService;
import server.api.security.JwtUserData;
import server.api.sessions.SessionUserData;
import server.api.sessions.SessionsService;
import server.api.users.User;
import server.api.users.UsersService;
import server.api.users.dto.UserSearchOptions;

import java.util.Optional;

@Service
public class AuthService {
  private final UsersService usersService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final SessionsService sessionsService;

  public AuthService(
      UsersService usersService,
      JwtService jwtService,
      PasswordEncoder passwordEncoder,
      SessionsService sessionsService) {
    this.usersService = usersService;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.sessionsService = sessionsService;
  }

  //    @Transactional
  public AuthResponse register(AuthRegisterDto dto) {
    UserSearchOptions searchOptions = UserSearchOptions.builder()
            .email(dto.email())
            .build();

    User existUser = usersService.getOne(searchOptions).orElse(null);
    if (existUser != null) {
      throw new BadRequestException("Email already exists");
    }
    String hashPass = passwordEncoder.encode(dto.password());
    User user = usersService.create(dto, hashPass);
    String sessionId = sessionsService.createSession(user);

    JwtUserData jwtUserData = new JwtUserData(user.getId(), user.getEmail());
    String accessToken = jwtService.generateToken(jwtUserData);

    return new AuthResponse(accessToken, sessionId);
  }

  public AuthResponse login(AuthLoginDto dto) {
    User user = validateUser(dto);
    String sessionId = sessionsService.createSession(user);
    JwtUserData jwtUserData = new JwtUserData(user.getId(), user.getEmail());
    String accessToken = jwtService.generateToken(jwtUserData);

    return new AuthResponse(accessToken, sessionId);
  }

  public AuthResponse refreshTokens(String refreshToken) {
    RedisIndexedSessionRepository.RedisSession session =
        sessionsService.getSessionById(refreshToken);
    if (session == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }
    SessionUserData userData = session.getAttribute("userData");

    sessionsService.updateSession(session);
    JwtUserData jwtUserData = new JwtUserData(userData.getUserId(), userData.getEmail());
    String accessToken = jwtService.generateToken(jwtUserData);
    return new AuthResponse(accessToken, session.getId());
  }

  public void logout(String refreshToken) {
    RedisIndexedSessionRepository.RedisSession session =
        sessionsService.getSessionById(refreshToken);
    if (session == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

    sessionsService.deleteSession(session.getId());
  }

  public User validateUser(AuthLoginDto dto) {
    UserSearchOptions searchOptions = UserSearchOptions.builder()
            .email(dto.email())
            .build();
    User user = usersService.getOne(searchOptions).orElse(null);
    if (user != null) {
      boolean passEquals = passwordEncoder.matches(dto.password(), user.getPassword());
      if (passEquals) return user;
    }
    throw new BadRequestException("Wrong user name or password");
  }
}
