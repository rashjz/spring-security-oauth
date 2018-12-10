package test.app.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="USERS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Data
@NoArgsConstructor@AllArgsConstructor
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String total;
    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    private List<Courses> courses;
}
