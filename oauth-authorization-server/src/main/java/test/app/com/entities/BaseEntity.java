package test.app.com.entities;

import lombok.Getter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
}