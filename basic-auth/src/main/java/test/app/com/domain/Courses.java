package test.app.com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Builder
@Data
@NoArgsConstructor@AllArgsConstructor
public class Courses implements Serializable {
    private Long courseId;
    private String name;
    private String grade;
}
