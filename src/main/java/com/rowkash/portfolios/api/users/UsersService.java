package com.rowkash.portfolios.api.users;

import com.rowkash.portfolios.api.users.dto.UserResponseDto;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.rowkash.portfolios.api.auth.dto.AuthRegisterDto;
import com.rowkash.portfolios.api.common.dto.PageResponseDto;
import com.rowkash.portfolios.api.common.exceptions.NotFoundException;
import com.rowkash.portfolios.api.users.dto.UpdateUserDto;
import com.rowkash.portfolios.api.users.dto.UserFilter;
import com.rowkash.portfolios.api.users.dto.UserPageDto;
import com.rowkash.portfolios.api.users.dto.UserSearchOptions;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

  private final UsersRepository usersRepository;
  private final UserMapper userMapper;

  //    @Transactional(propagation = Propagation.MANDATORY)
  public UsersService(UsersRepository usersRepository, UserMapper userMapper) {
    this.usersRepository = usersRepository;
    this.userMapper = userMapper;
  }

  public User create(AuthRegisterDto dto, String hashPassword) {
    User newUser = userMapper.toEntity(dto);
    newUser.setPassword(hashPassword);
    return usersRepository.save(newUser);
  }

  @Transactional
  public void update(Long id, UpdateUserDto dto) {
    User user =
        usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

    userMapper.updateUserFromDto(dto, user);
  }

  public User getById(long id) {
    return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
  }

  @Transactional(readOnly = true)
  public Optional<User> getOne(UserSearchOptions options) {
    Specification<User> spec = getFilter(options);
    return usersRepository.findOne(spec);
  }

  public PageResponseDto<UserResponseDto> getPage(UserPageDto dto) {

    Sort.Direction sortDirection = Sort.Direction.fromString(dto.getOrderSort());
    Pageable pageable =
        PageRequest.of(
            dto.getPage() - 1,
            dto.getLimit(),
            Sort.by(sortDirection, dto.getSortBy().getDatabaseField()));

    Specification<User> spec = getFilter(dto);

    Page<UserResponseDto> dtoPage =
        usersRepository.findAll(spec, pageable).map(userMapper::toResponseDto);

    return new PageResponseDto<>(dtoPage.getContent(), dtoPage.getTotalElements());
  }

  private Specification<User> getFilter(UserFilter options) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (options.getId() != null) {
        predicates.add(cb.equal(root.get("id"), options.getId()));
      }

      if (options.getEmail() != null) {
        predicates.add(cb.equal(root.get("email"), options.getEmail()));
      }

      if (options.getName() != null) {
        predicates.add(
            cb.like(cb.lower(root.get("name")), "%" + options.getName().toLowerCase() + "%"));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
