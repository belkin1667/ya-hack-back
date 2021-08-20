package com.belkin.yahack.api.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/i")
public class ImageController {

    @GetMapping("/{base64id}")
    public ResponseEntity<Resource> getImage(@PathVariable("base64id") String id) {

        /*
        //если картинка хранится локально - сходить в БД за ImageMetadata, cходить по указанному в метадате пути в файловой системе и отдать изображение
        Resource resource = imageService.download(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        */

        //если картинка хранится не локально - сходить в БД за ImageMetadata, отдать ссылку на картинку (поменять тогда return type на соответствующий)
        return null;
    }

    @PostMapping("/upload")
    public String addImage(@RequestParam("file") MultipartFile file) {
        // при загрузке картинки генерируем Base64 id для нее и его возвращаем, сохраняем картинку в файловой системе или удаленно, сохраняем в БД ImageMetadata
        return "generated base64 id here";
    }

    @DeleteMapping(path ="/{base64id}")
    public void deleteImage(@PathVariable("base64id") String id) {
        // удаляем метадату из базы, чистим файловую систему
    }


}
