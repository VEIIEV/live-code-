package com.example.empty.mapper;

import com.example.empty.dto.StockDto;
import com.example.empty.entity.Stock;

public class StockMapper {

    public static Stock DtoToStock(StockDto stockDto) {
        Stock stock = new Stock();
        stock.setQuantity(stockDto.getQuantity());
        stock.setName(stockDto.getName());
        return stock;
    }

    public static StockDto StockToDto(Stock stock) {
        StockDto dto = new StockDto();
        dto.setName(stock.getName());
        dto.setQuantity(stock.getQuantity());
        return dto;
    }
}
