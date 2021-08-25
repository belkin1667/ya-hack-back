package com.belkin.yahack.api.controller;

import com.belkin.yahack.serivce.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/i")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{base64id}")
    public ResponseEntity<Resource> getImage(@PathVariable("base64id") String id) {

        //если картинка хранится локально - сходить в БД за ImageMetadata, cходить по указанному в метадате пути в файловой системе и отдать изображение
        Resource resource = imageService.download(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        //если картинка хранится не локально - сходить в БД за ImageMetadata, отдать ссылку на картинку (поменять тогда return type на соответствующий)
    }

    @PostMapping("/edit")
    public String addImage(@RequestParam("file") MultipartFile file, @RequestHeader("username") String username) {
        // при загрузке картинки генерируем Base64 id для нее и его возвращаем, сохраняем картинку в файловой системе или удаленно, сохраняем в БД ImageMetadata
        return "localhost:8080/i/" + imageService.upload(username, file);
    }

    @DeleteMapping(path ="/edit/{base64id}")
    public void deleteImage(@PathVariable("base64id") String id, @RequestHeader("username") String username) {
        // удаляем метадату из базы, чистим файловую систему
        imageService.delete(id, username);
    }

    @GetMapping("/test")
    public String getYandexCloudSecretKey() throws Exception {
        return ImageService.getSignatureKey("YOLHEB27T4ygBqpU7tvRX3_zp1EcTrwEY4NVbsj1", "20210822",
                "ru-central1", "s3");
    }

}
