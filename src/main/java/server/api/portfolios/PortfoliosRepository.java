package server.api.portfolios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PortfoliosRepository
    extends JpaRepository<Portfolio, Long>, JpaSpecificationExecutor<Portfolio> {}
