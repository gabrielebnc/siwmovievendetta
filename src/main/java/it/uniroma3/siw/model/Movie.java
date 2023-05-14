package it.uniroma3.siw.model;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer year;
    private String urlImage;
    @OneToMany
    private List<News> news;
    @ManyToOne
    private Artist regist;
    @ManyToMany(mappedBy = "movieActedIn")
    private List<Artist> actors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public List<Artist> getActors() {
        return this.actors;
    }

    public void setActors(List<Artist> actors) {
        this.actors = actors;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Artist getRegist() {
        return regist;
    }

    public void setRegist(Artist regist) {
        this.regist = regist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getYear(), movie.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getYear());
    }
}
