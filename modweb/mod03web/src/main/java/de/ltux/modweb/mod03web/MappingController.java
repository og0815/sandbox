/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.modweb.mod03web;

import de.ltux.modweb.mod03web.dto.StudentLectureDto;
import de.ltux.modweb.mod03web.entity.Lecture;
import de.ltux.modweb.mod03web.entity.Student;
import fr.xebia.extras.selma.Selma;
import javax.annotation.ManagedBean;
import javax.inject.Named;

/**
 *
 * @author oliver.guenther
 */
@Named
@ManagedBean
public class MappingController {

    public String getLectureName() {
        Student s1 = new Student();
        s1.setName("Hans");
        Lecture l1 = new Lecture();
        l1.setName("Jura1");
        s1.setPrimaryLecture(l1);
        l1.getStudents().add(s1);

        StudentMapper mapper = Selma.builder(StudentMapper.class).build();
        StudentLectureDto dto = mapper.asStudentLecutureDto(s1);
        return dto.getLectureName();
    }

}
