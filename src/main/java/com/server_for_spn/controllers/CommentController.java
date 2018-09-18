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

import java.util.ArrayList;
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

    private List<CommentDTO> convertCommentToCommentDTO(List<Comment> comments){
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c: comments) {
            commentDTOs.add(new CommentDTO(c, userService.findOne(c.getUserWhoLeaveAComment())));
        }
        return commentDTOs;
    }


}
