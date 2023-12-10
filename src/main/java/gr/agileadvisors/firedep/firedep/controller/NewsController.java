package gr.agileadvisors.firedep.firedep.controller;

import gr.agileadvisors.firedep.firedep.dto.NewsCreateDto;
import gr.agileadvisors.firedep.firedep.dto.NewsDto;
import gr.agileadvisors.firedep.firedep.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/firedep")
@CrossOrigin(origins = {"http://192.168.28.40:3000/","http://localhost:3000","https://kepse.psnet.gr/"},allowedHeaders = "*")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "Create News", description = "Create News only for admins and moderators users")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/news")
    public ResponseEntity<Long> createNews(@RequestPart MultipartFile image,
                                    @RequestPart NewsCreateDto news) throws IOException {
        return newsService.createNews(news, image);
    }

    @Operation(summary = "Get News", description = "Get News available for everyone by newsID")
    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDto>  getNewsById(@PathVariable Long id){

        return newsService.getNewsById(id);
    }

    @Operation(summary = "Get All News", description = "Get All News available for everyone paginated")
    @GetMapping("/news")
    public ResponseEntity<Page<NewsDto>> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size){

        return newsService.getNews(page,size);
    }

    @Operation(summary = "Update News", description = "Update News by newsId only for admin and moderators users")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/news/{id}")
    public  ResponseEntity<NewsCreateDto> updateNewsById(@PathVariable Long id,
                                        @RequestPart MultipartFile image,
                                        @RequestPart NewsCreateDto news) throws Exception {

        return newsService.updateNewsById(id,news,image);
    }

    @Operation(summary = "Delete News", description = "Delete News by newsId only for admin users")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/news/{id}")
    public ResponseEntity<String> deleteNewsById(@PathVariable Long id){

        return newsService.deleteNewsById(id);
    }

}
