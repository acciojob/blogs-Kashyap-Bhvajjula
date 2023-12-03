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
        Optional<Blog> optionalBlog = blogRepository2.findById(blogId);
        if(!optionalBlog.isPresent()) {
            System.out.println("ImageService Blog not found");
            return null;
        }
        if(description == null || description.equals("")) {
            return null;
        }
        if(dimensions == null || dimensions.equals("")) {
            return null;
        }
        Blog blog = optionalBlog.get();
        Image image = new Image(description, dimensions);

        image.setBlog(blog);

        List<Image> blogImageList = blog.getImageList();
        blogImageList.add(image);
        blog.setImageList(blogImageList);

        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id) {
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        Optional<Image> optionalImage= imageRepository2.findById(id);
//        if(!optionalImage.isPresent()) {
//            throw new Exception("Image not found");
//        }
        Image image = optionalImage.get();
        String imageDimension = image.getDimensions();

        int image_index_X = imageDimension.indexOf('X');
        int imageLength = Integer.parseInt(imageDimension.substring(0, image_index_X));
        int imageBreadth = Integer.parseInt(imageDimension.substring(image_index_X+1));

        int screen_index_X = screenDimensions.indexOf('X');
        int screenLength = Integer.parseInt(screenDimensions.substring(0, screen_index_X));
        int screenBreadth = Integer.parseInt(screenDimensions.substring(screen_index_X+1));

        int fitLength = screenLength / imageLength;
        int fitBreadth = screenBreadth / imageBreadth;

        int imagesFit = fitBreadth * fitLength;

        return imagesFit;
    }
}