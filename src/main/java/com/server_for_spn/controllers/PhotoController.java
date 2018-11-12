package com.server_for_spn.controllers;

import com.server_for_spn.dao.PhotoDao;
import com.server_for_spn.dao.PhotoLikeDao;
import com.server_for_spn.entity.Photo;
import com.server_for_spn.entity.PhotoLikes;
import com.server_for_spn.entity.User;
import com.server_for_spn.service.ImageSavingService;
import com.server_for_spn.service.UserService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

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

    @Autowired
    private PhotoLikeDao photoLikeDao;




    @PostMapping("/updateMainPhoto")
    public ResponseEntity<?> updateMainPhoto(@RequestPart(name = "img") MultipartFile img,
                                             @RequestParam("id")Long id,
                                             Authentication authentication){

        User user = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long idNew =imageSavingService.savePhoto(img, user, true, null);
        List<Photo> photos = user.getPhotos();
        for (Photo photo: photos) {
            if(!photo.getId().equals(idNew)){
                photo.setMain(false);
                photoDao.save(photo);
            }
        }
        if(idNew != null) {
           return new ResponseEntity<>(HttpStatus.OK);
       }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
       }

    }

    @PostMapping("/addNewPhoto")
    public ResponseEntity<?> addNewPhoto(@RequestPart(name = "img") MultipartFile img,
                                             @RequestParam("id")Long id,
                                             @RequestParam("caption") String caption,
                                             Authentication authentication){

        User user = userService.findOne(id);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long idNew =imageSavingService.savePhoto(img, user, false, caption);
        if(idNew != null) {
            return new ResponseEntity<>(idNew,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }


    @PostMapping(value = "/getUsersPhoto",  produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUsersPhoto(@RequestParam("id") Long requesterId,
                                                                 @RequestParam("photoId") Long photoId,
                                                                 Authentication authentication){
        User user = userService.findOne(requesterId);
        if(!authentication.getPrincipal().toString().equals(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Photo photo = photoDao.getOne(photoId);
        Path path = Paths.get(photo.getPath());
        System.out.println("RETURNING MAIN PHOTO FOR USER: "+ user.getId());
        System.out.println("PATH: "+ path.toString());

        try {
            return new ResponseEntity<>(Files.readAllBytes(path), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PostMapping(value = "/getUsersMainPhoto",  produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUsersMainPhoto(@RequestParam("id") Long requesterId,
                                                Authentication authentication){
        User user = userService.findOne(requesterId);
        Photo photo = photoDao.findFirstByOwnerAndMain(user, true);
        if(photo == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Path path = Paths.get(photo.getPath());
        System.out.println("RETURNING MAIN PHOTO FOR USER: "+ user.getId());
        System.out.println("PATH: "+ path.toString());
        try {
            return new ResponseEntity<>(Files.readAllBytes(path), HttpStatus.OK);
        } catch (NoSuchFileException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IOException e) {
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


    @GetMapping("api/getPhotoCaption")
    public ResponseEntity<String> getPhotoCaption(@RequestParam("photoId") String id){


        String caption = photoDao.getOne(Long.decode(id)).getCaption();
        caption = caption.replaceAll("\\s", "%");

        System.out.println(caption);
        return new ResponseEntity<>(caption, HttpStatus.OK);
    }




    @PostMapping("/like")
    public ResponseEntity<Boolean> like(@RequestParam("userID") Long userId,
                                        @RequestParam("photoId") Long photoId,
                                        Authentication authentication){
        User user = userService.findByEmail(authentication.getPrincipal().toString());

        if(!userId.equals(user.getId())){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        List<PhotoLikes> likes = photoLikeDao.findAllByPhotoId(photoId);
        PhotoLikes like = null;

        boolean contains = false;

        for (PhotoLikes pl: likes) {
            if(pl.getUserId().equals(userId)){
                contains = true;
                like = pl;
                break;
            }
        }
        Photo photo  = photoDao.getOne(photoId);
        if(contains){
            photoLikeDao.deleteById(like.getId());
            photo.setLikes(photo.getLikes()-1);
            photoDao.save(photo);
        }else {
            PhotoLikes photoLikes = new PhotoLikes();
            photoLikes.setPhotoId(photo.getId());
            photoLikes.setUserId(user.getId());
            photoLikeDao.save(photoLikes);
            photo.setLikes(photo.getLikes() + 1);
            photoDao.save(photo);
        }

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @GetMapping("/getLikesIds")
    public List<Long> getLikeIds(@RequestParam("photoId") Long id){
        System.out.println("Getting likes for: " +id);
        List<PhotoLikes> likes = photoLikeDao.findAllByPhotoId(id);
        List<Long> userids = new ArrayList<>();
        for(PhotoLikes pl:likes){
            userids.add(pl.getUserId());
        }
        return userids;
    }







}
