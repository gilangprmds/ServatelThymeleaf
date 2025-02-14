package com.juaracoding.servatelthymeleaf.httpclient;


import com.juaracoding.servatelthymeleaf.dto.validasi.AddressDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.HotelRegistrationDTO;
import com.juaracoding.servatelthymeleaf.dto.validasi.RoomDTO;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "hotel-services",url = "http://localhost:8080/hotel", configuration = HotelService.FeignConfig.class)
public interface HotelService {

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> save(@RequestPart("hotel") String hotelJson,
                                       @RequestPart("hotelImages") List<MultipartFile> hotelImages);

    @GetMapping("/manager/hotels/{id}")
    public ResponseEntity<Object> findAllByManagerId( @RequestParam(value = "page") Integer page,
                                                      @PathVariable(value = "id") Long id);



//    @PostMapping(value = "/save", consumes = "multipart/form-data")
//    public ResponseEntity<Object> saveHotel(
//            @RequestPart("name") String name,
////            @RequestPart("address") AddressDTO address,
//            @RequestPart("description") String description,
//            @RequestPart("hotelImages") List<MultipartFile> hotelImages
////            @RequestPart("rooms") List<RoomDTO> rooms
//    );
//
    class FeignConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }
}
