package org.seniorcare.shared.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/upload")
@Tag(name = "Upload", description = "Endpoint para upload de arquivos de mídia")
public class FileUploadController {

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg", "image/png", "image/webp", "image/gif"
    );

    private static final Map<String, String> MIME_TO_EXTENSION = Map.of(
            "image/jpeg", ".jpg",
            "image/png",  ".png",
            "image/webp", ".webp",
            "image/gif",  ".gif"
    );

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.upload.base-url:http://localhost:8080}")
    private String baseUrl;

    @Operation(summary = "Faz upload de uma imagem e retorna a URL pública")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("O arquivo não pode ser vazio.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new BadRequestException("Tipo de arquivo não permitido. Apenas JPEG, PNG, WebP e GIF são aceitos.");
        }

        String ext = MIME_TO_EXTENSION.get(contentType);
        String filename = UUID.randomUUID() + ext;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Path destination = uploadPath.resolve(filename).normalize();
        if (!destination.getParent().equals(uploadPath)) {
            throw new BadRequestException("Caminho de arquivo inválido.");
        }

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok(Map.of("url", baseUrl + "/uploads/" + filename));
    }
}
