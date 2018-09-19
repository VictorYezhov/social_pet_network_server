package com.server_for_spn.controllers;

import com.server_for_spn.dao.PhotoDao;
import com.server_for_spn.dto.CommentDTO;
import com.server_for_spn.entity.Comment;
import com.server_for_spn.entity.Photo;
import com.server_for_spn.service.CommentService;
import com.server_for_spn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoDao photoDao;

    @PostMapping("/getAllCommentOfCurrentPhoto")
    public List<CommentDTO> sendToTheClientAllCommentOfCurrentPhoto(@RequestParam("photo_id") Long photoID){

        Photo photo = photoDao.getOne(photoID);
        return convertCommentToCommentDTO(photo.getComments());
    }


    @PostMapping("/sendNewComment")
    public String getNewComment(@RequestParam("time") String time, @RequestParam("user_id_who_left_a_comment") Long id,
                                @RequestParam("text") String text, @RequestParam("photoID") Long photoID) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss.SSS");

        Date parsedTimeStamp = dateFormat.parse(time);
        Timestamp timestamp = new Timestamp(parsedTimeStamp.getTime());
        Comment comment = new Comment(timestamp, id, text, photoDao.getOne(photoID));
        commentService.save(comment);
        return "ok";

    }

    private List<CommentDTO> convertCommentToCommentDTO(List<Comment> comments){
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c: comments) {
            commentDTOs.add(new CommentDTO(c, userService.findOne(c.getUserWhoLeaveAComment())));
        }
        return commentDTOs;
    }


}
