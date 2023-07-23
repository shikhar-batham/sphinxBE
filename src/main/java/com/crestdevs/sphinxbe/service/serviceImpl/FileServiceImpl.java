package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.service.FileService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final List<String> ALLOWED_IMAGE_FORMATS = Arrays.asList("png", "jpeg", "jpg");

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        assert name != null;

        String fileExtension = getFileExtension(name);

        if (!isAllowedImageFormat(fileExtension)) {
            return null;
        }

        if (file.getSize() > 2 * 1024) {

            File compressedImageFile = compressImage(file, fileExtension);
            FileInputStream input = new FileInputStream(compressedImageFile);
            file = new MockMultipartFile("file", file.getName(), "text/plain", new BufferedInputStream(input));
        }

        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String fullPath = path + File.separator + fileName;

        return new FileInputStream(fullPath);
    }

    private String getFileExtension(String fileName) {

        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }

        return "";
    }

    private boolean isAllowedImageFormat(String fileExtension) {

        return ALLOWED_IMAGE_FORMATS.contains(fileExtension);
    }

    private File compressImage(MultipartFile file, String fileExtension) throws IOException {

        File compressedImageFile = File.createTempFile("comp", file.getOriginalFilename());

        Thumbnails.of(file.getInputStream())
                .size(800, 600) // Set the desired dimensions for the compressed image
                .outputFormat(fileExtension) // Set the desired output format
                .outputQuality(0.8) // Set the desired output quality (0.0 - 1.0)
                .toFile(compressedImageFile);

        return compressedImageFile;
    }

}
