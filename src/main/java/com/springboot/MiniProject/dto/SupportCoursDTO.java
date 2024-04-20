package com.springboot.MiniProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportCoursDTO {
    private int id;
    private String libelleSupport;
    private String lienPDF;

}
