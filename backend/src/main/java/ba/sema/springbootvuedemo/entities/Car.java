package ba.sema.springbootvuedemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "car")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String manufacturer;

    @Column(length = 100, nullable = false)
    private String model;

    @Column(name = "release_year", nullable = false)
    private Short releaseYear;

    @Column(name = "last_change", nullable = false)
    private LocalDateTime lastChange;

    //region Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDateTime lastChange) {
        this.lastChange = lastChange;
    }
    //endregion
}
