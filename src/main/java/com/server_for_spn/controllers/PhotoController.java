package com.server_for_spn.controllers;

import com.server_for_spn.dao.PhotoDao;
import com.server_for_spn.entity.Photo;
import com.server_for_spn.entity.User;
import com.server_for_spn.service.ImageSavingService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 06.09.2018.
 */
@RestController
public class PhotoController {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoDao photoDao;


    @Autowired
    private ImageSavingService imageSavingService;




    @PostMapping("/updateMainPhoto")
    public ResponseEntity<?> updateMainPhoto(@RequestPart(name = "img") MultipartFile img,
                                             @RequestParam("id")Long id,
                                             Authentication authentication){

        System.out.println("i am here");

        User user = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       if(imageSavingService.savePhoto(img, user)) {
           return new ResponseEntity<>(HttpStatus.OK);
       }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
       }

    }
    @PostMapping("/getUsersPhoto")
    public ResponseEntity<byte[]> getUsersPhoto(@RequestParam("id") Long requesterId,
                                                                 @RequestParam("photoId") Long photoId,
                                                                 Authentication authentication){
        User user = userService.findOne(requesterId);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Photo photo = photoDao.getOne(photoId);
        Path path = Paths.get(photo.getPath());

        try {
            return new ResponseEntity<>(Files.readAllBytes(path), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/getUsersPhotoIds")
    public ResponseEntity<List<Long>> getPhotoIdis(@RequestParam("requesterId") Long id,
                                   @RequestParam("targetId") Long targetId,
                                   Authentication authentication){
        User requester = userService.findOne(id);

        if(!authentication.getPrincipal().toString().equals(requester.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        User target = userService.findOne(targetId);
        List<Long> requestList = new ArrayList<>();

        for (Photo p:
             target.getPhotos()) {
            requestList.add(p.getId());
        }
        return new ResponseEntity<>(requestList, HttpStatus.OK);
    }







}
