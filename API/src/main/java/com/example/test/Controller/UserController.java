package com.example.test.Controller;

import com.example.test.Form.HttpRex;
import com.example.test.Model.UserFiles;
import com.example.test.Service.UserService;
import com.example.test.Util.AuthUtils;
import com.example.test.View.UserFilesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController
{
    @Autowired
    UserService userService;
    @PostMapping("file")
    public ResponseEntity<HttpRex> uploadFile(@RequestParam("file") MultipartFile file)
    {
        // Handle the uploaded file (e.g., save it to a directory or process it)
        // Return a response indicating success or failure
        if(file==null)
        {
            final HttpRex rex = new HttpRex("400","File should not be empty");
            return ResponseEntity.badRequest().body(rex);
        }
        else
        {
            final UserDetails details = AuthUtils.getUserDetails();
            final String userName = details.getUsername();
            if(userService.uploadFile(file,userName))
            {
                final HttpRex rex = new HttpRex("200","File upload complete");
                return ResponseEntity.ok().body(rex);
            }
            else
            {
                final HttpRex rex = new HttpRex("500","Error uploading the file");
                return ResponseEntity.internalServerError().body(rex);
            }
        }
    }

    @GetMapping("file")
    public ResponseEntity<HttpRex> getFileList()
    {
        final UserDetails details = AuthUtils.getUserDetails();
        final String userName = details.getUsername();

        UserFilesView data = userService.listUserFiles(userName);
        final HttpRex rex = new HttpRex("200",data);
        return ResponseEntity.ok().body(rex);
    }

    @GetMapping("file/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer id)
    {
        Resource file = userService.downloadFile(id);

        final String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("file/{id}")
    private ResponseEntity<HttpRex> deleteFile(@PathVariable Integer id)
    {
        userService.deleteFile(id);
        final HttpRex rex = new HttpRex("200","File deleted");
        return ResponseEntity.ok().body(rex);
    }


}
