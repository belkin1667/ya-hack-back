package com.belkin.yahack.serivce;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.belkin.yahack.dao.ImageMetadataDAO;
import com.belkin.yahack.exception.AccessDeniedException;
import com.belkin.yahack.exception.ImageStorageException;
import com.belkin.yahack.exception.RestException;
import com.belkin.yahack.exception.not_found.ImageNotFoundException;
import com.belkin.yahack.model.ImageMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMetadataDAO imageMetadataDAO;

    public String upload(String uploaderUsername, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName == null ? "" :
                originalFileName.substring(originalFileName.lastIndexOf('.')).toLowerCase();

        ImageMetadata imageMetadata = new ImageMetadata(uploaderUsername, ext);
        imageMetadataDAO.save(imageMetadata);
        imageMetadata.setPath();
        imageMetadataDAO.save(imageMetadata);

        String id = imageMetadata.getId();
        try {
            Path copyLocation = Paths.get(StringUtils.cleanPath(imageMetadata.getPath()));
            if (Files.exists(copyLocation))
                throw new IOException();
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            imageMetadataDAO.deleteById(id);
            throw new ImageStorageException(file.getOriginalFilename());
        }
        return id;
    }

    public Resource download(String id) {
        String path = ImageMetadata.BASE_PATH + File.separator + id + "." + "jpg";
        String finalPath = path;
        ImageMetadata imageMetadata = imageMetadataDAO.findById(id)
                .or(() -> {
                    ImageMetadata imd = null;
                    if (Files.exists(Path.of(finalPath))) {
                        imd = new ImageMetadata(id, null, ".jpg");
                        imageMetadataDAO.save(imd);
                    }
                    return Optional.ofNullable(imd);
                }).orElseThrow(() -> new ImageNotFoundException(id));
        path = imageMetadata.getPath();
        return new FileSystemResource(path);
    }

    public boolean delete(String id, String myUsername) {
        ImageMetadata imageMetadata = imageMetadataDAO.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
        if (!Files.exists(Path.of(imageMetadata.getPath()))) {
            imageMetadataDAO.deleteById(id);
            throw new ImageNotFoundException(id);
        }

        if (imageMetadata.getUploaderUsername().equals(myUsername)) {
            try {
                Files.delete(Path.of(imageMetadata.getPath()));
            } catch (IOException e) {
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete image");
            }
            imageMetadataDAO.deleteById(id);
            return true;
        } else {
            throw new AccessDeniedException(myUsername);
        }
    }

    private static byte[] HmacSHA256(String data, byte[] key) throws Exception {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF-8"));
    }

    public static String getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF-8");
        byte[] kDate = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        byte[] kSigning = HmacSHA256("aws4_request", kService);
        return new BigInteger(1, kSigning).toString(16);
    }

}
