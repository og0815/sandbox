package test;

import de.ltux.modweb.mod03web.StudentMapper;
import static de.ltux.modweb.mod03web.dto.Assertions.*;
import de.ltux.modweb.mod03web.dto.StudentLectureDto;
import de.ltux.modweb.mod03web.entity.Lecture;
import de.ltux.modweb.mod03web.entity.Student;
import fr.xebia.extras.selma.Selma;
import org.junit.Test;

/**
 *
 * @author oliver.guenther
 */
public class SelmaTest {

    @Test
    public void testMapAndAssertionsGenerator() {
        Student s1 = new Student();
        s1.setName("Hans");
        Lecture l1 = new Lecture();
        l1.setName("Jura1");
        s1.setPrimaryLecture(l1);
        l1.getStudents().add(s1);

        StudentMapper mapper = Selma.builder(StudentMapper.class).build();
        StudentLectureDto dto = mapper.asStudentLecutureDto(s1);

        assertThat(dto).hasStudentName(s1.getName()).hasLectureName(l1.getName());
//         assertThat(dto.getStudentName()).isEqualTo(s1.getName());
//         assertThat(dto.getLectureName()).isEqualTo(l1.getName());
    }
}
