package com.example.hakaton;

import com.example.hakaton.dto.entity.UziDTO;
import com.example.hakaton.entity.Uzi;
import com.example.hakaton.repository.entity.UziRep;
import com.example.hakaton.service.entity.impl.UziServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UziServiceImplTest {

    @InjectMocks
    private UziServiceImpl uziService;

    @Mock
    private UziRep uziRep;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testGetAll() {
        // Arrange
        List<Uzi> uziList = Arrays.asList(new Uzi(), new Uzi());
        when(uziRep.findAll()).thenReturn(uziList);

        // Act
        List<UziDTO> result = uziService.getAll();

        // Assert
        assertEquals(uziList.size(), result.size());
        verify(modelMapper, times(uziList.size())).map(any(), eq(UziDTO.class));
    }

    @Test
    public void testGetById() {
        // Arrange
        Long id = 1L;
        Uzi uzi = new Uzi();
        when(uziRep.findById(id)).thenReturn(Optional.of(uzi));

        // Act
        UziDTO result = uziService.getById(id);

        // Assert
        verify(modelMapper).map(uzi, UziDTO.class);
    }

    @Test
    public void testCreate() {
        // Arrange
        UziDTO uziDTO = new UziDTO();
        Uzi uzi = new Uzi();
        when(modelMapper.map(uziDTO, Uzi.class)).thenReturn(uzi);
        when(uziRep.save(uzi)).thenReturn(uzi);

        // Act
        UziDTO result = uziService.create(uziDTO);

        // Assert
        verify(modelMapper).map(uziDTO, Uzi.class);
        verify(uziRep).save(uzi);
        verify(modelMapper).map(uzi, UziDTO.class);
        System.out.println("Тест создания прошел" + result);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Long id = 1L;
        UziDTO uziDTO = new UziDTO();
        Uzi uzi = new Uzi();
        when(uziRep.findById(id)).thenReturn(Optional.of(uzi));
        when(uziRep.save(uzi)).thenReturn(uzi);

        // Act
        UziDTO result = uziService.update(id, uziDTO);

        // Assert
        verify(uziRep).findById(id);
        verify(uziRep).save(uzi);
        assertEquals(uziDTO.getName(), result.getName());
        assertEquals(uziDTO.getCode(), result.getCode());
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;
        Uzi uzi = new Uzi();
        when(uziRep.findById(id)).thenReturn(Optional.of(uzi));

        // Act
        uziService.delete(id);

        // Assert
        verify(uziRep).findById(id);
        verify(uziRep).delete(uzi);
    }
}