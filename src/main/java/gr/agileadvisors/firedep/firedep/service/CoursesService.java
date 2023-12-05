package gr.agileadvisors.firedep.firedep.service;

import gr.agileadvisors.firedep.firedep.dto.CoursesCreateDto;
import gr.agileadvisors.firedep.firedep.dto.CoursesDto;
import gr.agileadvisors.firedep.firedep.model.Courses;
import gr.agileadvisors.firedep.firedep.repository.CoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursesService {

    private final CoursesRepository coursesRepository;

    public ResponseEntity<String> createCourses(CoursesCreateDto coursesCreateDto, MultipartFile image, MultipartFile image2, MultipartFile image3) throws IOException {
        Courses course = mapCoursesCreateDtoToCourses(coursesCreateDto);
        if(image!=null){
            course.setImage(image.getBytes());
        }
        if(image2!=null){
            course.setImage2(image2.getBytes());
        }
        if(image3!=null){
            course.setImage3(image3.getBytes());
        }

        return new ResponseEntity<>(coursesRepository.save(course).getCourseId(),  HttpStatus.CREATED);
    }

    public ResponseEntity<CoursesDto> getCoursesById(String coursesId) {
        Optional<Courses> courses = coursesRepository.findById(coursesId);
        return courses.map(value -> new ResponseEntity<>(mapCoursesToCoursesDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<String> deleteCoursesById(String coursesId){
        Optional<Courses> courses = coursesRepository.findById(coursesId);
        if(courses.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        coursesRepository.deleteById(coursesId);

        return new ResponseEntity<>("Course Deleted successfully",HttpStatus.OK);
    }

    public ResponseEntity<Page<CoursesDto>> getCourses(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        Page<Courses> res = coursesRepository.findAll(paging);

        return new ResponseEntity<>(res.map(this::mapCoursesToCoursesDto),HttpStatus.OK);
    }

    public ResponseEntity<CoursesCreateDto> updateCourseById(String courseId,CoursesCreateDto coursesCreateDto, MultipartFile image, MultipartFile image2, MultipartFile image3) throws Exception {
        Optional<Courses> course = coursesRepository.findById(courseId);
        if (course.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            Courses updatedCourses = mapCoursesCreateDtoToCourses(coursesCreateDto);
            updatedCourses.setCourseId(course.get().getCourseId());
            if(image!=null){
                updatedCourses.setImage(image.getBytes());
            }
            if(image2!=null){
                updatedCourses.setImage2(image2.getBytes());
            }
            if(image3!=null){
                updatedCourses.setImage3(image3.getBytes());
            }
            coursesRepository.save(updatedCourses);
        }

        return new ResponseEntity<>(coursesCreateDto,HttpStatus.OK);
    }

    private Courses mapCoursesCreateDtoToCourses(CoursesCreateDto coursesCreateDto) {
        return Courses.builder().courseId(coursesCreateDto.getCourseId()).accreditations(coursesCreateDto.getAccreditations())
                .content(coursesCreateDto.getContent()).content2(coursesCreateDto.getContent2()).content3(coursesCreateDto.getContent3())
                .length(coursesCreateDto.getLength()).templateType(coursesCreateDto.getTemplateType()).title(coursesCreateDto.getTitle())
                .subTitle(coursesCreateDto.getTitle()).sectors(coursesCreateDto.getSectors()).videoURL(coursesCreateDto.getVideoURL())
                .overview(coursesCreateDto.getOverview()).build();
    }

    private CoursesDto mapCoursesToCoursesDto(Courses courses) {
        return CoursesDto.builder()
                .courseId(courses.getCourseId())
                .accreditations(courses.getAccreditations())
                .content(courses.getContent())
                .content2(courses.getContent2())
                .content3(courses.getContent3())
                .length(courses.getLength())
                .templateType(courses.getTemplateType())
                .title(courses.getTitle())
                .subTitle(courses.getTitle())
                .sectors(courses.getSectors())
                .videoURL(courses.getVideoURL())
                .overview(courses.getOverview())
                .image(courses.getImage() != null ? Base64.getEncoder().encodeToString(courses.getImage()) : null)
                .image2(courses.getImage2() != null ? Base64.getEncoder().encodeToString(courses.getImage2()) : null)
                .image3(courses.getImage3() != null ? Base64.getEncoder().encodeToString(courses.getImage3()) : null)
                .build();

    }

}
