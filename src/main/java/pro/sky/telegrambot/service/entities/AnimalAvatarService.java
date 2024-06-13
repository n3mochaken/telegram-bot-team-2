package pro.sky.telegrambot.service.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.entity.Animal;
import pro.sky.telegrambot.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class AnimalAvatarService {

    @Value("${animal.avatars.path}")
    String avatarPath;

    private final AnimalService animalService;
    private final AvatarRepository avatarRepository;

    public AnimalAvatarService(AnimalService animalService, AvatarRepository avatarRepository) {
        this.animalService = animalService;
        this.avatarRepository = avatarRepository;
    }

//    public void uploadAvatar(Long id, MultipartFile avatar) throws IOException {
//
//        Animal animal =.getById(id);
//
//        Path filePath = Path.of(avatarPath, id + "." + getExtension(avatar.getOriginalFilename()));
//        Files.deleteIfExists(filePath);
//        try (InputStream is = avatar.getInputStream();
//             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//             BufferedInputStream bis = new BufferedInputStream(is, 1024);
//             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//
//        ) {
//            bis.transferTo(bos);
//        }
//
//        animal.setPhotoPass(filePath.toString());
//        animalRepository.save(animal);
//
//
//    }
}
