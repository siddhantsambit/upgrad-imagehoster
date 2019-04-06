package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    // This controller method is called when the request pattern is of type
    // 'addComment' and also the incoming request is of POST Type
    // The method calls the createComment() method in the business logic passing the
    // comments, image id, image title to be add comment
    // Looks for a controller method with mapping of type
    // '/image/{imageId}/{imageTitle}'
    @RequestMapping(value="/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComments(@RequestParam("comment") String commentText, @PathVariable("imageId") Integer imageId, @PathVariable("imageTitle") String imageTitle, HttpSession session, Model model){
        Image image = imageService.getImageByTitleAndId(imageTitle, imageId);
        Comment newComment = new Comment();
        User loggerUser = (User) session.getAttribute("loggeduser");
        newComment.setUser(loggerUser);
        newComment.setImage(image);
        newComment.setText(commentText);
        newComment.setCreatedDate(new Date());
        commentService.saveComment(newComment);
        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        model.addAttribute("comments", image.getComments());
        return "redirect:/images/" + imageId + "/" + imageTitle;
    }
}
