package com.blockchainia.image;

import org.springframework.ai.image.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.*;
import org.springframework.beans.factory.annotation.Qualifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import javax.imageio.ImageIO;


@RestController
public class ImageController {

  @Qualifier("openAiImageModel")
  private ImageModel imageModel;

  public ImageController(@Qualifier("openAiImageModel") ImageModel imageModel) {
    this.imageModel = imageModel;
  }
  
  @GetMapping("/hello")
  public String hello() {
    return "Hello!";
  }

@GetMapping("/")
  public String hi() {
    return "Hi!";
  }
   
  @GetMapping("/image")
  public String imageGen() {
    ImageOptions options = ImageOptionsBuilder.builder()
        .withModel("dall-e-3")
        .withHeight(1024)
        .withWidth(1024)
        .build();
    String message = "Cat chasing a car";

    ImagePrompt imagePrompt = new ImagePrompt(message, options);
    ImageResponse response = imageModel.call(imagePrompt);
    String imageUrl = response.getResult().getOutput().getUrl();

    String destinationFile = "saved_image.jpg";  // The destination file

    try {
       URI uri = new URI(imageUrl);

        // Open a stream from the URI
        InputStream inputStream = uri.toURL().openStream();

        // Read the image from the input stream
        BufferedImage image = ImageIO.read(inputStream);

        // Close the input stream
        inputStream.close();

        // Write the image to a file
        File file = new File(destinationFile);
        ImageIO.write(image, "jpg", file);

        System.out.println("Image saved successfully: " + file.getAbsolutePath());
    } catch (Exception e) {
        System.out.println("Error saving the image: " + e.getMessage());
        e.printStackTrace();
    }
    return "redirect:" + imageUrl;


  }
}
