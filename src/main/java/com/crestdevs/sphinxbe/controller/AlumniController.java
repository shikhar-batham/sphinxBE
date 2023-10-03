package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.AlumniDto;
import com.crestdevs.sphinxbe.payload.ApiResponse;
import com.crestdevs.sphinxbe.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alumni")
public class AlumniController {

    @Value("${project.alumniProfileImages}")
    private String path;

    @Autowired
    private AlumniService alumniService;

    @PutMapping("/updateAlumni")
    public ResponseEntity<AlumniDto> updateAlumni(@RequestBody AlumniDto alumniDto, @PathVariable Integer alumniId) {

        AlumniDto updatedAlumni = this.alumniService.updateAlumni(alumniDto, alumniId);

        return new ResponseEntity<>(updatedAlumni, HttpStatus.OK);
    }

    @GetMapping("/getAlumniById")
    public ResponseEntity<AlumniDto> getAlumniById(Integer alumniId) {

        AlumniDto alumniDto = this.alumniService.getAlumniById(alumniId);

        return new ResponseEntity<>(alumniDto, HttpStatus.FOUND);
    }

    @GetMapping("/getAllAlumni")
    public ResponseEntity<List<AlumniDto>> getAllAlumni() {

        List<AlumniDto> alumniDtoList = this.alumniService.getAllAlumni();

        return new ResponseEntity<>(alumniDtoList, HttpStatus.OK);
    }

    @PostMapping("/setAlumniProfileImageById/{alumniId}")
    public ResponseEntity<ApiResponse> uploadAlumniProfileImage(@PathVariable("alumniId") Integer alumniId,
                                                                @RequestParam("image") MultipartFile file) throws IOException {

        Boolean res = this.alumniService.uploadAlumniProfileImage(alumniId, path, file);

        if (res)
            return new ResponseEntity<>(new ApiResponse("Image uploaded successfully!!", true), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiResponse("Something went wrong. Failed to upload your image!!", false), HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/getAlumniProfileImageById/{alumniId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadAlumniProfileImageById(@PathVariable Integer alumniId, HttpServletResponse response) {

        try {
            this.alumniService.downloadAlumniProfileImageById(alumniId, path, response);
        } catch (Exception ignored) {

        }
    }

    @PostMapping("/uploadAlumniImageByEmail/{email}")
    public ResponseEntity<ApiResponse> uploadAlumniProfileImageByEmail(@PathVariable("email") String email,
                                                                       @RequestParam("image") MultipartFile image) throws IOException {

        Boolean res = this.alumniService.uploadAlumniProfileImageByEmail(email, path, image);

        if (res)
            return new ResponseEntity<>(new ApiResponse("Image uploaded successfully!!", true), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiResponse("Something went wrong. Failed to upload you image!!", false), HttpStatus.CONFLICT);
    }

    @GetMapping("/getAlumniProfileImage/{email:.+}")
    public void downloadAlumniProfileImageByEmail(@PathVariable("email") String email, HttpServletResponse response) {
        try {
            this.alumniService.downloadAlumniProfileImageByEmail(email, path,  response);

            // Set the content type of the response
            response.setContentType(MediaType.IMAGE_PNG_VALUE); // Change to the appropriate image type

        } catch (Exception ignored) {
            // Handle exceptions appropriately
        }
    }
}
