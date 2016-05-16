package br.com.psi.alexandria.service;

import br.com.psi.alexandria.domain.Student;

import java.util.List;

/**
 * Service Interface for managing Student.
 */
public interface StudentService {

    /**
     * Save a student.
     * 
     * @param student the entity to save
     * @return the persisted entity
     */
    Student save(Student student);

    /**
     *  Get all the students.
     *  
     *  @return the list of entities
     */
    List<Student> findAll();

    /**
     *  Get the "id" student.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Student findOne(Long id);

    /**
     *  Delete the "id" student.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
