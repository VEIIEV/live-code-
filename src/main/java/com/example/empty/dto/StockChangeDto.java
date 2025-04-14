package com.example.empty.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockChangeDto {

    private String name;

    private int deltaQuantity;

}
