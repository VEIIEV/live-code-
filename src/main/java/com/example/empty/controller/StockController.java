package com.example.empty.controller;

import com.example.empty.dto.AvaliableResponseDto;
import com.example.empty.dto.StockChangeDto;
import com.example.empty.dto.StockDto;
import com.example.empty.entity.Stock;
import com.example.empty.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;


    @GetMapping("/contain")
    public AvaliableResponseDto stockCheck(@RequestBody StockDto dto) {

        return stockService.contain(dto);
    }

    @PostMapping
    public ResponseEntity<Stock> addNewProductType(@RequestBody StockDto stockDto) {

        return new ResponseEntity<>(stockService.addNewProductType(stockDto), HttpStatus.CREATED);

    }

    @PatchMapping
    public StockDto updateProductType(@RequestBody StockChangeDto dto) {
        return stockService.updateQuantity(dto);
    }
}
