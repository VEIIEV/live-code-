package com.example.empty.service;


import com.example.empty.dto.AvaliableResponseDto;
import com.example.empty.dto.StockChangeDto;
import com.example.empty.dto.StockDto;
import com.example.empty.entity.Stock;
import com.example.empty.mapper.StockMapper;
import com.example.empty.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    final private StockRepository stockRepository;

    public Stock addNewProductType(StockDto stockDto) {
        return stockRepository.save(StockMapper.DtoToStock(stockDto));
    }

    public AvaliableResponseDto contain(StockDto dto) {
        Stock stock = stockRepository.findByName(dto.getName());
        if (stock == null) {
            throw new EntityNotFoundException(dto.getName());
        }
        if (stock.getQuantity() >= dto.getQuantity()) {
            return new AvaliableResponseDto(stock.getName(), true);
        } else {
            return new AvaliableResponseDto(stock.getName(), false);
        }
    }

    public StockDto updateQuantity(StockChangeDto dto) {
        Stock stock = stockRepository.findByName(dto.getName());
        stock.setQuantity(stock.getQuantity() + dto.getDeltaQuantity());
        return StockMapper.StockToDto(stockRepository.save(stock));
    }
}
