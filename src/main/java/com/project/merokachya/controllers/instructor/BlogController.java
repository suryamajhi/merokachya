package com.project.merokachya.controllers.instructor;

import com.project.merokachya.dto.BlogRequest;
import com.project.merokachya.entities.Blog;
import com.project.merokachya.repos.BlogRepository;
import com.project.merokachya.repos.CommentReplyRepository;
import com.project.merokachya.repos.CommentRepository;
import com.project.merokachya.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("instructorBlogController")
@RequestMapping("/instructor")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentReplyRepository commentReplyRepository;

    @GetMapping("/blogs")
    public String showBlogs(ModelMap modelMap){
        List<Blog> blogs = blogRepository.findAll();
        modelMap.addAttribute("blogs", blogs);
        modelMap.addAttribute("title","Blogs");
        return "instructor.blog.index";
    }
    @GetMapping("/blog/{id}")
    public String display(@PathVariable int id, ModelMap modelMap){
        Blog blog = blogRepository.findById(id).get();
        modelMap.addAttribute("blog",blog);
        return "instructor.blog.display";
    }

    @GetMapping("/blog/new")
    public String addBlog(ModelMap modelMap){
        modelMap.addAttribute("title","Add New Blog");
        return "instructor.blog.new";
    }

    @PostMapping("/blog/new")
    public String add(@ModelAttribute("blogRequest") BlogRequest blogRequest){
        blogService.addBlog(blogRequest);
        return "redirect:/instructor/blogs";
    }

    @GetMapping("/blog/edit/{id}")
    public String showEdit(@PathVariable("id") int id, ModelMap modelMap){
        Blog blog = blogRepository.findById(id).get();
        modelMap.addAttribute("blog", blog);
        modelMap.addAttribute("title","Edit Blog");
        return "instructor.blog.edit";
    }
    @PostMapping("/blog/edit/{id}")
    public String updateBlog(@PathVariable("id") int id,@ModelAttribute("blogRequest") BlogRequest blogRequest, ModelMap modelMap){
        blogService.editBlog(id, blogRequest);
        return "redirect:/instructor/blogs";

    }

    @GetMapping("/blog/delete/{id}")
    public String delete(@PathVariable("id") int id){
        blogRepository.deleteById(id);
        return "redirect:/instructor/blogs";
    }


//    Controllers for comment and commentReply
    @GetMapping("/blog/{blogId}/comment/delete/{id}")
    public String deleteComment(@PathVariable int blogId,@PathVariable int id){
        commentRepository.deleteById(id);
        return "redirect:/instructor/blog/"+blogId;
    }
    @GetMapping("/blog/{blogId}/commentReply/delete/{id}")
    public String deleteCommentReply(@PathVariable int blogId,@PathVariable int id){
        commentReplyRepository.deleteById(id);
        return "redirect:/instructor/blog/"+blogId;
    }

}
