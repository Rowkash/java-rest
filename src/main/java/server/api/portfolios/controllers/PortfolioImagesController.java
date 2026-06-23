package server.api.portfolios.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Portfolios")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("portfolios")
public class PortfolioImagesController {

  //    @PostMapping("/upload")
  //    public ResponseEntity<HashMap<String, String>> uploadImage(@RequestParam("file")
  // MultipartFile file) {
  //        if (file.isEmpty()) {
  //            throw new BadRequestException("File is empty");
  //        }
  //
  //        String fileName = file.getOriginalFilename();
  //        long size = file.getSize();
  //        String contentType = file.getContentType();
  //        HashMap<String, String> body = new HashMap<>();
  //        body.put("name", fileName);
  //        body.put("size", String.valueOf(size));
  //        body.put("contentType", contentType);
  //
  //
  //        return ResponseEntity.ok(body);
  //    }

  @PostMapping("/upload")
  public ResponseEntity<HashMap<String, String>> uploadImage(HashMap<String, String> dto) {
    return ResponseEntity.ok(dto);
  }
}
