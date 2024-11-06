package uz.mohirdev.lesson.service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mohirdev.lesson.entity.FileStorage;
import uz.mohirdev.lesson.entity.enummration.FileStorageStatus;
import uz.mohirdev.lesson.ripository.FileStorageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    private final Hashids hashids;

    @Value("${upload.server.folder}")
    private String serverFolderPath;

    public FileStorageService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids=new Hashids(getClass().getName(), 6);
    }

    public FileStorage save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getName());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorage=fileStorageRepository.save(fileStorage);

        Date now=new Date();
        String path=String.format("%s/upload_files/%d/%d/ %d", this.serverFolderPath,
                1990+now.getYear(),1+now.getMonth(),now.getDate());
        File uploadFolder=new File(path);

        if(!uploadFolder.exists() && uploadFolder.mkdirs()){
            System.out.println("papka yasaldi");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        String pathLocal=String.format("/upload_files/%d/%d/%d/%s.%s",
                1990+now.getYear(),
                1+now.getMonth(),
                now.getDate(),
                fileStorage.getHashId(),
                fileStorage.getExtension());
        fileStorage.setUploadFolder(pathLocal);
        fileStorageRepository.save(fileStorage);

        uploadFolder=uploadFolder.getAbsoluteFile();
        File file=new File(uploadFolder,String.format("%s.%s",fileStorage.getHashId(),fileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileStorage;
    }

    public void delete(String hashId){

        FileStorage fileStorage=fileStorageRepository.findByHashId(hashId);
        File file1 = new File(String.format("%s/%s",this.serverFolderPath, fileStorage.getUploadFolder()));
        try {
            // Faylni ochish urinishi
            FileInputStream fis = new FileInputStream(file1);
            // Faylni yopish
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            // Fayl topilmadi - Fayl ishlatilmoqda bo'lishi mumkin
            System.out.println("Fayl tizim tomonidan ishlatilmoqda!");
            // Faylni o'chirish yoki boshqa kerakli harakatlarni bajaring
        }


        File file=new File(String.format("%s/%s",this.serverFolderPath, fileStorage.getUploadFolder()));



        if(file.delete()){
            fileStorageRepository.delete(fileStorage);
        }
    }

    public FileStorage findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    public String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }


}
