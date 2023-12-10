package gr.agileadvisors.firedep.firedep.controller;

import gr.agileadvisors.firedep.firedep.dto.CoursesCreateDto;
import gr.agileadvisors.firedep.firedep.dto.CoursesDto;
import gr.agileadvisors.firedep.firedep.dto.NewsCreateDto;
import gr.agileadvisors.firedep.firedep.dto.NewsDto;
import gr.agileadvisors.firedep.firedep.service.CoursesService;
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
@CrossOrigin(origins = {"http://192.168.28.40:3000/","http://localhost:3000"},allowedHeaders = "*")
public class CoursesController {

    private final CoursesService coursesService;

    @Operation(summary = "Create Courses", description = "Create Courses only for admins and moderators users")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/courses")
    public ResponseEntity<String> createCourses(@RequestPart(required = false) MultipartFile image,
                                                   @RequestPart(required = false) MultipartFile image2,
                                                   @RequestPart(required = false) MultipartFile image3,
                                                   @RequestPart CoursesCreateDto coursesCreateDto) throws IOException {
        return coursesService.createCourses(coursesCreateDto, image,image2,image3);
    }

    @Operation(summary = "Get Courses", description = "Get Courses available for everyone by newsID")
    @GetMapping("/courses/{id}")
    public ResponseEntity<CoursesDto>  getCoursesById(@PathVariable String id){

        return coursesService.getCoursesById(id);
    }

    @Operation(summary = "Get All Courses", description = "Get All Courses available for everyone paginated")
    @GetMapping("/courses")
    public ResponseEntity<Page<CoursesDto>> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return coursesService.getCourses(page, size);
    }

    @Operation(summary = "Update Courses", description = "Update Courses by courseId only for admin and moderators users")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/courses/{id}")
    public  ResponseEntity<CoursesCreateDto> updateNewsById(@PathVariable String id,
                                                            @RequestPart(required = false) MultipartFile image,
                                                            @RequestPart(required = false) MultipartFile image2,
                                                            @RequestPart(required = false) MultipartFile image3,
                                                            @RequestPart  CoursesCreateDto coursesCreateDto) throws Exception {

        return coursesService.updateCourseById(id,coursesCreateDto,image,image2,image3);
    }

    @Operation(summary = "Delete Courses", description = "Delete Courses by courseId only for admin users")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<String> deleteNewsById(@PathVariable String id){

        return coursesService.deleteCoursesById(id);
    }

}
