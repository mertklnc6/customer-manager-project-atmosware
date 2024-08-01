package com.turkcell.customer_service.external.mernis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MernisAdapterTest {

    @Mock
    private MernisClient mernisClient;

    @InjectMocks
    private MernisAdapter mernisAdapter;

    @Test
    void testValidate_whenValidData() {
        // Arrange
        CheckNationalityDTO dto = new CheckNationalityDTO("12345678901", "John", "Doe", LocalDate.of(1990, 1, 1));
        when(mernisClient.TCKimlikNoDogrula("12345678901", "JOHN", "DOE", 1990)).thenReturn(true);

        // Act
        boolean result = mernisAdapter.validate(dto);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void testValidate_whenInvalidData() {
        // Arrange
        CheckNationalityDTO dto = new CheckNationalityDTO("12345678901", "John", "Doe", LocalDate.of(1990, 1, 1));
        when(mernisClient.TCKimlikNoDogrula("12345678901", "JOHN", "DOE", 1990)).thenReturn(false);

        // Act
        boolean result = mernisAdapter.validate(dto);

        // Assert
        assertEquals(false, result);
    }
}
