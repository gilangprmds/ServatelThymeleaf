package com.juaracoding.servatelthymeleaf.dto.validasi;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/*
Created By IntelliJ IDEA 2024.3 (Community Edition)
Build #IC-243.21565.193, built on November 13, 2024
@Author USER Febby Tri Andika
Java Developer
Created on 06/02/2025 19:31
@Last Modified 06/02/2025 19:31
Version 1.0
*/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValUserDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^([a-z0-9\\.]{8,16})$",
            message = "Format Huruf kecil ,numeric dan titik saja min 8 max 25 karakter, contoh : paulch.123")
    private String username;

    @JsonProperty("first-name")
    private String firstName;
    @JsonProperty("last-name")
    private String lastName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,50})+$",
            message = "Format tidak valid contoh : user_name123@sub.domain.com")
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^(62|\\+62|0)8[0-9]{9,13}$",
            message = "Format No HP Tidak Valid , min 9 max 13 setelah angka 8, contoh : (0/62/+62)81111111")
    @JsonProperty("no-hp")
    private String noHp;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[\\w\\s\\.\\,]{20,255}$",
            message = "Format Alamat Tidak Valid min 20 maks 255, contoh : Jln. Kenari 2B jakbar 11480")
    private String address;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonProperty("tanggal-lahir")
    private LocalDate tanggalLahir;
//
//    @NotNull
//    private RespRoleDTO role;


}