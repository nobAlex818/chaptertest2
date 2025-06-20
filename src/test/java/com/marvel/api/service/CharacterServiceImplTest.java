package com.marvel.api.service;

import com.marvel.api.dto.Character;
import com.marvel.api.repository.CharacterRepository;
import com.marvel.api.service.impl.CharacterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceImplTest {
    @Mock
    private CharacterRepository repository;

    @InjectMocks
    private CharacterServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsList() {
        Character c1 = new Character();
        c1.setId(1);
        c1.setName("Iron Man");
        c1.setAlterego("Tony Stark");
        c1.setDescription("Genius billionaire");
        c1.setPowers(Arrays.asList("Armor", "Flight"));
        when(repository.findAll()).thenReturn(Arrays.asList(c1));
        List<Character> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("Iron Man", result.get(0).getName());
    }

    @Test
    void getById_ExistingId_ReturnsCharacter() {
        Character c1 = new Character();
        c1.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(c1));
        Character result = service.getById(1);
        assertEquals(1, result.getId());
    }

    @Test
    void getById_NotFound_ThrowsException() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertThrows(com.marvel.api.exception.ResourceNotFoundException.class, () -> service.getById(2));
    }

    @Test
    void create_SetsIdAndSaves() {
        Character c1 = new Character();
        c1.setName("Thor");
        when(repository.save(any())).thenReturn(c1);
        Character result = service.create(c1);
        assertEquals("Thor", result.getName());
    }

    @Test
    void update_ExistingId_UpdatesCharacter() {
        Character c1 = new Character();
        c1.setId(1);
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(any())).thenReturn(c1);
        Character result = service.update(1, c1);
        assertEquals(1, result.getId());
    }

    @Test
    void update_NotFound_ThrowsException() {
        Character c1 = new Character();
        when(repository.existsById(2)).thenReturn(false);
        assertThrows(com.marvel.api.exception.ResourceNotFoundException.class, () -> service.update(2, c1));
    }

    @Test
    void delete_ExistingId_Deletes() {
        when(repository.existsById(1)).thenReturn(true);
        assertDoesNotThrow(() -> service.delete(1));
        verify(repository).deleteById(1);
    }

    @Test
    void delete_NotFound_ThrowsException() {
        when(repository.existsById(2)).thenReturn(false);
        assertThrows(com.marvel.api.exception.ResourceNotFoundException.class, () -> service.delete(2));
    }
}