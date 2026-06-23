package server.api.portfolios.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Portfolios")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("portfolios")
public class PortfoliosController {}
