package com.vadrin.vtalk.services;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

  public String reduceImageSize(String largeBase64Image) {
    try {
      BufferedImage inputImage = ImageIO
          .read(new ByteArrayInputStream(Base64.getDecoder().decode(largeBase64Image.split(",")[1])));
      BufferedImage outputImage = resizeImage(inputImage, 640d / inputImage.getWidth());
      return largeBase64Image.split(",")[0] + ","
          + encodeToString(outputImage, largeBase64Image.split(";")[0].split("/")[1]);
    } catch (IOException e) {
      e.printStackTrace();
      return largeBase64Image;
    }
  }

  public String encodeToString(BufferedImage image, String type) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(image, type, bos);
    byte[] imageBytes = bos.toByteArray();
    Base64.Encoder encoder = Base64.getEncoder();
    String imageString = encoder.encodeToString(imageBytes);
    bos.close();
    return imageString;
  }

  public BufferedImage resizeImage(BufferedImage originalImage, double scale) {
    BufferedImage resizedImage = new BufferedImage((int) (originalImage.getWidth() * scale),
        (int) (originalImage.getHeight() * scale), originalImage.getType());
    Graphics graphics = resizedImage.createGraphics();
    graphics.drawImage(originalImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), null);
    graphics.dispose();
    return resizedImage;
  }

}
