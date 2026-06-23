package server.api.portfolios;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.api.common.dto.PageResponseDto;
import server.api.common.exceptions.NotFoundException;
import server.api.portfolios.dto.PortfolioPageDto;

@Service
public class PortfoliosService {

  private final PortfoliosRepository portfoliosRepository;

  //    private final UserMapper userMapper;

  public PortfoliosService(PortfoliosRepository portfoliosRepository) {
    this.portfoliosRepository = portfoliosRepository;
  }

  //    public User create(AuthRegisterDto dto, String hashPassword) {
  //    if (isUserEmailExists(dto.email())) {
  //        throw new BadRequestException("Email already exists");
  //    }
  //    User newUser = userMapper.toEntity(dto);
  //    newUser.setPassword(hashPassword);
  //    return usersRepository.save(newUser);
  //    }

  public Portfolio getById(long id) {
    return portfoliosRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Portfolio not found"));
  }

  public PageResponseDto<Portfolio> getPage(PortfolioPageDto dto) {

    Sort.Direction sortDirection = Sort.Direction.fromString(dto.getOrderSort());
    Pageable pageable =
        PageRequest.of(
            dto.getPage() - 1,
            dto.getLimit(),
            Sort.by(sortDirection, dto.getSortBy().getDatabaseField()));
    Specification<Portfolio> spec = getFilter(dto);

    Page<Portfolio> result = portfoliosRepository.findAll(spec, pageable);

    return new PageResponseDto<Portfolio>(result.getContent(), result.getTotalElements());
  }

  private Specification<Portfolio> getFilter(PortfolioPageDto dto) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (dto.getName() != null) {
        predicates.add(
            cb.like(cb.lower(root.get("name")), "%" + dto.getName().toLowerCase() + "%"));
      }

      if (dto.getDescription() != null) {
        predicates.add(
            cb.like(
                cb.lower(root.get("description")), "%" + dto.getDescription().toLowerCase() + "%"));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
