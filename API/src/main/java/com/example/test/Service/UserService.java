package com.example.test.Service;

import com.example.test.Model.User;
import com.example.test.Model.UserFiles;
import com.example.test.Repository.UserFileRepositoy;
import com.example.test.Repository.UserRepository;
import com.example.test.Util.AuthUtils;
import com.example.test.View.FileListDTO;
import com.example.test.View.UserFilesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    UserFileRepositoy fileRepo;

    @Value("${file.path}")
    private String direcotry;

    public boolean uploadFile(MultipartFile file, String userName)
    {
        try
        {
            final String uploadDirectory = direcotry + "/" + userName ;

            // Create the directory if it doesn't exist
            Path directoryPath = Path.of(uploadDirectory);
            Files.createDirectories(directoryPath);

            final String originalFileName = (AuthUtils.GetDateTime() + "_+_" + file.getOriginalFilename()).replaceAll("\\s","");
            final Path filePath = directoryPath.resolve(originalFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            final User user = userRepo.findByUserName(userName);
            UserFiles newFile = new UserFiles();
            newFile.setUser(user);
            newFile.setFileName(String.valueOf(filePath));
            newFile.setFileSize((float) (file.getSize() * 0.000001));

            fileRepo.save(newFile);

            return true;
        }
        catch (IOException e)
        {
            // Handle exceptions (e.g., file I/O errors)
            return false;
        }
    }

    public Resource downloadFile(Integer id)
    {
        final UserFiles file = fileRepo.findById(id).get();
        return loadFileAsResource(file.getFileName());
    }

    public UserFilesView listUserFiles(String userName)
    {
//        User user = userRepo.findByUserName(userName);
        User user = userRepo.findByUserNameOrderByFilesDesc(userName);
        List<FileListDTO> fileList = user.getFiles().stream().map(userFile ->
                new FileListDTO(userFile.getId(), userFile.getFileName(),userFile.getFileSize())).toList();

        return new UserFilesView(user.getId(),user.getUserName(),fileList);
    }

    public void deleteFile(Integer id)
    {
        fileRepo.deleteById(id);
    }

    private Resource loadFileAsResource(String file)
    {
        try
        {
            Path filePath = Path.of(file);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
            {
                return resource;
            }
            else
            {
                throw new RuntimeException("File not found: " + file);
            }
        }
        catch (MalformedURLException ex)
        {
            throw new RuntimeException("File not found: " + file, ex);
        }
    }
}
