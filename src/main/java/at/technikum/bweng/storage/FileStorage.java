package at.technikum.bweng.storage;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileStorage {

    String upload(MultipartFile file) throws FileUploadException;

    InputStream load(String id) throws FileNotFoundException;
}

