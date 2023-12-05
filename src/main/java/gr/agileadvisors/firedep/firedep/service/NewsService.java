package gr.agileadvisors.firedep.firedep.service;

import gr.agileadvisors.firedep.firedep.dto.NewsCreateDto;
import gr.agileadvisors.firedep.firedep.dto.NewsDto;
import gr.agileadvisors.firedep.firedep.model.News;
import gr.agileadvisors.firedep.firedep.repository.NewsRepository;
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
public class NewsService {

    private final NewsRepository newsRepository;

    public ResponseEntity<Long> createNews(NewsCreateDto NewsCreateDto, MultipartFile image) throws IOException {
        News news = mapNewsCreateDtoToNews(NewsCreateDto);
        if(image!=null){
            news.setImage(image.getBytes());
        }

        return new ResponseEntity<>(newsRepository.save(news).getNewsId(),  HttpStatus.CREATED);
    }

    public ResponseEntity<NewsDto> getNewsById(Long newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        return news.map(value -> new ResponseEntity<>(mapNewsToNewsDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    public ResponseEntity<String> deleteNewsById(Long newsId){
        Optional<News> news = newsRepository.findById(newsId);
        if(news.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newsRepository.deleteById(newsId);

        return new ResponseEntity<>("News Deleted successfully",HttpStatus.OK);
    }

    public ResponseEntity<Page<NewsDto>> getNews(int page, int size) {
        PageRequest paging = PageRequest.of(page, size);
        Page<News> res = newsRepository.findAll(paging);

        return new ResponseEntity<>(res.map(this::mapNewsToNewsDto),HttpStatus.OK);
    }

    public ResponseEntity<NewsCreateDto> updateNewsById(Long newsId,NewsCreateDto newsCreateDto,MultipartFile image) throws Exception {
        Optional<News> news = newsRepository.findById(newsId);
        if (news.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            News updatedNews = mapNewsCreateDtoToNews(newsCreateDto);
            updatedNews.setNewsId(news.get().getNewsId());
            if(image!=null){
                updatedNews.setImage(image.getBytes());
            }
            newsRepository.save(updatedNews);
        }

        return new ResponseEntity<>(newsCreateDto,HttpStatus.OK);
    }

    private News mapNewsCreateDtoToNews(NewsCreateDto newsCreateDto) {
        return News.builder().title(newsCreateDto.getTitle()).subTitle(newsCreateDto.getSubTitle())
                .content(newsCreateDto.getContent()).category(newsCreateDto.getCategory())
                .videoURL(newsCreateDto.getVideoURL())
                .build();
    }
    private NewsDto mapNewsToNewsDto(News news){
        return NewsDto.builder().newsId(news.getNewsId()).title(news.getTitle()).subTitle(news.getSubTitle())
                .content(news.getContent()).category(news.getCategory()).videoURL(news.getVideoURL())
                .createdAt(news.getCreatedAt())
                .image(news.getImage() != null ? Base64.getEncoder().encodeToString(news.getImage()): null).build();
    }
}
