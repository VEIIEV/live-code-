package com.example.empty;


import com.example.empty.dto.AvaliableResponseDto;
import com.example.empty.dto.StockChangeDto;
import com.example.empty.dto.StockDto;
import com.example.empty.entity.Stock;
import com.example.empty.repository.StockRepository;
import com.example.empty.service.StockService;
import com.example.exception.OutOfStockException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTests {

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockService stockService;


    @ParameterizedTest
    @CsvSource({"apple, 13, true",
            "apple, 15, true",
            "apple, 25, false"})
    public void containCorrectValueTest(String name, Long quantity, boolean result) {
        Stock stockToReturn = new Stock();
        StockDto stockDto = new StockDto(name, quantity);

        stockToReturn.setName("apple");
        stockToReturn.setQuantity(15L);
        when(stockRepository.findByName(anyString())).thenReturn(stockToReturn);

        AvaliableResponseDto dto = stockService.contain(stockDto);
        Assertions.assertEquals(result, dto.getIsAvaliable());
    }

    @Test
    public void containZeroQuantityTest() {
        StockDto stockDto = new StockDto("product", 0);

        Stock stockToReturn = new Stock();
        stockToReturn.setName("apple");
        stockToReturn.setQuantity(15L);

        when(stockRepository.findByName(anyString())).thenReturn(stockToReturn);

        AvaliableResponseDto dto = stockService.contain(stockDto);
        Assertions.assertEquals(true, dto.getIsAvaliable());
    }

    @Test
    public void containNonExistingNameTest() {
        StockDto stockDto = new StockDto("product", 0);

        when(stockRepository.findByName(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> stockService.contain(stockDto));

    }

    @Test
    public void containNullInputTest() {
        StockDto stockDto = new StockDto(null, 25);
        Assertions.assertThrows(IllegalArgumentException.class, () -> stockService.contain(stockDto));
    }

    @Test
    public void updateQuantityIncreaseStockTest() {
        Stock existing = new Stock();
        existing.setName("apple");
        existing.setQuantity(10L);

        when(stockRepository.findByName("apple")).thenReturn(existing);
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StockDto result = stockService.updateQuantity(new StockChangeDto("apple", 5));

        Assertions.assertEquals("apple", result.getName());
        Assertions.assertEquals(15L, result.getQuantity());
    }

    @Test
    public void updateQuantityDecreaseStockTest() {
        Stock existing = new Stock();
        existing.setName("apple");
        existing.setQuantity(10L);

        when(stockRepository.findByName("apple")).thenReturn(existing);
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StockDto result = stockService.updateQuantity(new StockChangeDto("apple", -5));

        Assertions.assertEquals("apple", result.getName());
        Assertions.assertEquals(5L, result.getQuantity());
    }

    @Test
    public void updateQuantityDecreaseToLesserThenZeroTest() {
        Stock existing = new Stock();
        existing.setName("apple");
        existing.setQuantity(10L);

        StockChangeDto stockChangeDto = new StockChangeDto("apple", -15);

        when(stockRepository.findByName("apple")).thenReturn(existing);

        Assertions.assertThrows(OutOfStockException.class,
                () -> stockService.updateQuantity(stockChangeDto));
    }


    @Test
    public void updateQuantityNonExistingNameTest() {
        StockChangeDto stockDto = new StockChangeDto("zero", 0);

        when(stockRepository.findByName(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> stockService.updateQuantity(stockDto));
    }
}
