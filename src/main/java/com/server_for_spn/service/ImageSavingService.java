package com.server_for_spn.service;

import com.server_for_spn.dao.PhotoDao;
import com.server_for_spn.entity.Photo;
import com.server_for_spn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Victor on 06.09.2018.
 */



@Service
public class ImageSavingService {

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoDao photoDao;


    public boolean savePhoto(MultipartFile img, User user, boolean main){
        String path =System.getProperty("user.dir") + "/data/Users/"
                + user.getName()+user.getId()+"/";
        File filePath = new File(path);
        //user.setPathToImage(path+image.getOriginalFilename());
        //System.err.println(user.getPathToImage());
        filePath.mkdirs();
        try {
            // Get the file and
            // Save it somewhere
            byte[] bytes = img.getBytes();
            Path pathTo = Paths.get(path+System.currentTimeMillis()+img.getOriginalFilename());
            Photo photo = new Photo();
            photo.setOwner(user);
            photo.setMain(main);
            photo.setPath(pathTo.toString());
            photoDao.save(photo);
            Files.write(pathTo, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
