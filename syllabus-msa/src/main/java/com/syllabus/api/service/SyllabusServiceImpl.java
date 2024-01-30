package com.syllabus.api.service;

import com.syllabus.api.dto.request_dto.SyllabusDto;
import com.syllabus.api.entity.Syllabus;
import com.syllabus.api.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SyllabusServiceImpl implements SyllabusService{
    EntityManager entityManager;

    @Autowired
    public SyllabusServiceImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    @Cacheable(value = "syllabusCache")
    public List<Syllabus> getAllSyllabus() {
        TypedQuery<Syllabus> typedQuery=entityManager.createQuery("FROM Syllabus",Syllabus.class);
        List<Syllabus> syllabusList=typedQuery.getResultList();
        return syllabusList;
    }


    @Override
    @Transactional
    @CacheEvict(value = "syllabusCache", allEntries = true)
    public ResponseEntity<?> createSyllabus(SyllabusDto syllabusDto) {
        // Validate the SyllabusDto
        if (syllabusDto == null || syllabusDto.getName() == null || syllabusDto.getName().isEmpty() ||
                syllabusDto.getMedium() == null || syllabusDto.getMedium().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Please fill all the filed"));
        }

        // Check if a similar Syllabus already exists
        if (syllabusExists(syllabusDto.getName())) {
            return ResponseEntity.status(409).body(new Message("Syllabus with the same name already exists"));
        }

        Syllabus syllabus = new Syllabus();
        syllabus.setName(syllabusDto.getName());
        syllabus.setMedium(syllabusDto.getMedium());
        syllabus.setDescription(syllabusDto.getDescription());
        syllabus.setStatus(syllabusDto.getStatus());

        entityManager.persist(syllabus);

        return ResponseEntity.ok(new Message("Data has been inserted"));
    }


    @Override
    public ResponseEntity<?> getSyllabusById(Long id) {
        Syllabus syllabus = entityManager.find(Syllabus.class, id);

        if (syllabus == null) {
            // Return a not found response (HTTP 404 Not Found) if no Syllabus is found with the given ID
            return ResponseEntity.status(204).body(new Message("No Record Found"));
        }

        return ResponseEntity.ok(syllabus);
    }



    @Override
    @Transactional
    @CacheEvict(value = "syllabusCache", allEntries = true)
    public ResponseEntity<?> updateSyllabus(Long id, Syllabus updatedSyllabus) {
        Syllabus existingSyllabus = entityManager.find(Syllabus.class, id);

        if (existingSyllabus == null) {
            System.out.println("Syllabus not found");
            return ResponseEntity.status(204).body(new Message("Syllabus with given id not Found"));
        }

        existingSyllabus.setName(updatedSyllabus.getName());
        existingSyllabus.setStatus(updatedSyllabus.getStatus());
        existingSyllabus.setDescription(updatedSyllabus.getDescription());
        existingSyllabus.setMedium(updatedSyllabus.getMedium());

        // entityManager.merge is not needed in this case, as the entity is already in the persistence context

        return ResponseEntity.ok(new Message("Syllabus updated successfully"));
    }

    @Override
    @Transactional
    @CacheEvict(value = "syllabusCache", allEntries = true)
    public ResponseEntity<?> deleteSyllabus(Long id) {
        Query query=entityManager.createQuery("DELETE from Syllabus where syllabus_id=:s_id");
        query.setParameter("s_id",id);
        int rows=query.executeUpdate();
        if(rows>0){
            return ResponseEntity.ok(new Message("Syllabus Entry Has Been Deleted Successfully"));
        }else{
            return ResponseEntity.status(204).body(new Message("Syllabus Entry Not Found With the given id"));
        }
    }

    @Override
    public ResponseEntity<Integer> idExistOrNot(Long id) {
        Syllabus syllabus=(Syllabus)this.getSyllabusById(id).getBody();
        if(syllabus!=null) return ResponseEntity.ok(1);
        else return ResponseEntity.ok(0);
    }

    // Helper method to check if a similar Syllabus already exists in the database
     private boolean syllabusExists(String syllabusName) {

        Query query = entityManager.createQuery("SELECT COUNT(s) FROM Syllabus s WHERE s.name = :name")
                .setParameter("name", syllabusName);
                Long count = (Long) query.getSingleResult();

        return count > 0;
    }
}
