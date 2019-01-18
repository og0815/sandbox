/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.modweb.mod03web;

import de.ltux.modweb.mod03web.dto.StudentLectureDto;
import de.ltux.modweb.mod03web.entity.Student;
import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.InheritMaps;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

/**
 *
 * @author oliver.guenther
 */
@Mapper
public interface StudentMapper {
    
    Student duplicateStudent(Student student);
    
    @Maps(withCustomFields = {
        @Field({"Student.name","studentName"}),
        @Field({"Student.primaryLecture.name","lectureName"})
    })
    StudentLectureDto asStudentLecutureDto(Student student);

    @InheritMaps
    Student update(StudentLectureDto dto, Student student);
    
}
