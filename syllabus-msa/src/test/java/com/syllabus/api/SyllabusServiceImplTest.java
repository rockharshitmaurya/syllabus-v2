package com.syllabus.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.syllabus.api.dto.request_dto.SyllabusDto;
import com.syllabus.api.entity.Syllabus;
import com.syllabus.api.model.Message;
import com.syllabus.api.service.SyllabusService;
import com.syllabus.api.service.SyllabusServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class SyllabusServiceImplTest {

    private SyllabusService syllabusService;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        syllabusService = new SyllabusServiceImpl(entityManager);
    }

    @Test
    void testGetAllSyllabusNotEmpty() {
        // Mocking a list of Syllabus
        List<Syllabus> mockedSyllabusList = new ArrayList<>();
        mockedSyllabusList.add(new Syllabus(1L,"Math","Active","English","This is a dummy syllabus"));

        // Mocking EntityManager and TypedQuery behavior
        TypedQuery<Syllabus> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Syllabus", Syllabus.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(mockedSyllabusList);

        // Call the service method
        List<Syllabus> syllabusList= syllabusService.getAllSyllabus();

        // Assert the result
        assertEquals(syllabusList.size(), mockedSyllabusList.size());
        assertEquals(mockedSyllabusList.toString(), syllabusList.toString());
    }

    @Test
    void testGetAllSyllabusEmpty() {
        TypedQuery<Syllabus> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery("FROM Syllabus", Syllabus.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(new ArrayList<>());

        // Call the service method
        List<Syllabus> syllabusList= syllabusService.getAllSyllabus();

        // Assert the result
        assertEquals(syllabusList.size(), 0);
        assertEquals(new ArrayList<>().toString(), syllabusList.toString());
    }


    @Test
    void testGetSyllabusByIdFound() {
        // Mocking a Syllabus object with ID 1
        Syllabus mockedSyllabus = new Syllabus(1L,"Math","Active","English","This is a dummy syllabus");
        mockedSyllabus.setSyllabus_id(1L);

        // Mocking EntityManager find behavior
        when(entityManager.find(Syllabus.class, 1L)).thenReturn(mockedSyllabus);

        // Call the service method
        ResponseEntity<?> response = syllabusService.getSyllabusById(1L);

        // Assert the result
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Syllabus);
        assertEquals(mockedSyllabus, response.getBody());
    }


    @Test
    void testGetSyllabusByIdNotFound() {
        Syllabus mockedSyllabus = null;

        when(entityManager.find(Syllabus.class, 1L)).thenReturn(mockedSyllabus);

        ResponseEntity<?> response = syllabusService.getSyllabusById(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals(new Message("No Record Found").toString(),  response.getBody().toString());
    }


    @Test
    void testDeleteSyllabusSuccess(){
        Query mockedQuery=mock(Query.class);
        when(mockedQuery.executeUpdate()).thenReturn(1);
        when(entityManager.createQuery("DELETE from Syllabus where syllabus_id=:s_id")).thenReturn(mockedQuery);
        ResponseEntity<?> response=syllabusService.deleteSyllabus(1L);

        assertEquals(200,response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals(new Message("Syllabus Entry Has Been Deleted Successfully").toString(),response.getBody().toString());
    }

    @Test
    void testDeleteSyllabusNotFound(){
        Query mockedQuery=mock(Query.class);
        when(mockedQuery.executeUpdate()).thenReturn(0);
        when(entityManager.createQuery("DELETE from Syllabus where syllabus_id=:s_id")).thenReturn(mockedQuery);
        ResponseEntity<?> response=syllabusService.deleteSyllabus(1L);

        assertEquals(204,response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals(new Message("Syllabus Entry Not Found With the given id").toString(),response.getBody().toString());
    }


    @Test
    void testUpdateSyllabusSuccess() {
        Syllabus existingSyllabus = new Syllabus(1L,"Math","Active","English","This is a dummy syllabus");

        when(entityManager.find(Syllabus.class, 1L)).thenReturn(existingSyllabus);

        ResponseEntity<?> response = syllabusService.updateSyllabus(1L, new Syllabus());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals("Syllabus updated successfully", ((Message) response.getBody()).getMessage());
    }

    @Test
    void testUpdateSyllabusNotFound() {
        when(entityManager.find(Syllabus.class, 1L)).thenReturn(null);

        ResponseEntity<?> response = syllabusService.updateSyllabus(1L, new Syllabus());

        assertEquals(204, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals(new Message("Syllabus with given id not Found").toString(), response.getBody().toString());
    }


    @Test
    void testCreateSyllabusSuccess() {

        SyllabusDto syllabusDto = new SyllabusDto("Math","Active","English","This is a dummy syllabus");

        Query query = mock(Query.class);

        when(entityManager.createQuery("SELECT COUNT(s) FROM Syllabus s WHERE s.name = :name")).thenReturn(query);
        when(query.setParameter("name",syllabusDto.getName())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(0L);


        ResponseEntity<?> response = syllabusService.createSyllabus(syllabusDto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals("Data has been inserted", ((Message) response.getBody()).getMessage());
    }

    @Test
    void testCreateSyllabusFailure() {

        SyllabusDto syllabusDto = new SyllabusDto("Math","Active","English","This is a dummy syllabus");

        Query query = mock(Query.class);

        when(entityManager.createQuery("SELECT COUNT(s) FROM Syllabus s WHERE s.name = :name")).thenReturn(query);
        when(query.setParameter("name",syllabusDto.getName())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(2L);


        ResponseEntity<?> response = syllabusService.createSyllabus(syllabusDto);

        assertEquals(409, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Message);
        assertEquals("Syllabus with the same name already exists", ((Message) response.getBody()).getMessage());
    }


}

