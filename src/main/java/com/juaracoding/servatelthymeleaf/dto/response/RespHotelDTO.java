package com.juaracoding.servatelthymeleaf.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juaracoding.servatelthymeleaf.dto.validasi.AddressDTO;

import java.util.List;

public class RespHotelDTO {
    private Long id;
    private String name;
    private AddressDTO address;
    private List<RespRoomDTO> rooms;
    private String description;
    private String userUsername;
}
