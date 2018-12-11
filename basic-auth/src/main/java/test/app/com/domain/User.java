package test.app.com.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private Long userId;
    private String userName;
    private String total;
    private List<Courses> courses;
}
