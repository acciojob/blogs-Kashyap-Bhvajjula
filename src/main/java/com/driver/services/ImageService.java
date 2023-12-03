package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog

        /*
        * Image image = new Image(description, dimensions);
        Blog blog = blogRepository2.findById(blogId).get();

        image.setBlog(blog);
        blog.getImageList().add(image);
        * */
        /*Image image = new Image();
        image.setDimensions(description);
        image.setDimensions(dimensions);

        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);
        blog.getImageList().add(image);*/

        //add imagelist to blog
        //add image to image list
        //or
        // List<Image> imageList = blog.getImageList();
        //imageList.add(image);

        Optional<Blog> optionalBlog = blogRepository2.findById(blogId);
        if(!optionalBlog.isPresent()){
            System.out.println("Image service blog not found");
            return null;
        }

        if(description==null || description.equals(""))
            return null;

        if(dimensions==null || dimensions.equals(""))
            return null;

        Blog blog = optionalBlog.get();
        Image image = new Image(description, dimensions);
        image.setBlog(blog);

        List<Image> blogImageList = blog.getImageList();
        blogImageList.add(image);
        blog.setImageList(blogImageList);

        blogRepository2.save(blog);
        return image;
    }

    public void deleteImage(Integer id){
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`

        //get the obj
        Image image = imageRepository2.findById(id).get();
        // get image dimension in form of string
        String givenDimensions = image.getDimensions();

        String [] givenDimension = givenDimensions.split("X"); //10X20X30==>[10,20,30]
        String [] screenDimension = screenDimensions.split("X");

        int givenWidth = Integer.parseInt(givenDimension[0]); //convert string to integer
        int givenHeight = Integer.parseInt(givenDimension[1]);

        int screenWidth = Integer.parseInt(screenDimension[0]);
        int screenHeight = Integer.parseInt(screenDimension[1]);

        int width = screenWidth/givenWidth;
        int height = screenHeight/givenHeight;

        return width*height ;
    }
}