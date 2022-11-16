package com.saxy.drones.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DrugDto {
    private String name;
    private long weight ;
    private String code ;
    private String image ;
}
