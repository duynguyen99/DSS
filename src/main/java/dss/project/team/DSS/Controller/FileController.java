package dss.project.team.DSS.Controller;

import dss.project.team.DSS.Service.IFileService;
import dss.project.team.DSS.Service.Response.FileCompareResponse;
import dss.project.team.DSS.Service.Response.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    IFileService fileService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<FileResponse> singleUpload(@RequestBody MultipartFile file) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        return ResponseEntity.ok().headers(responseHeaders).body(fileService.uploadFile(file));
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> getFiles(@RequestParam(value = "name", required = false) String fileName)
            throws SQLException, IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");
        List<FileResponse> response = fileService.getFileList(fileName);
        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    @GetMapping("/compare")
    public ResponseEntity<FileCompareResponse> compareImage(@RequestParam(value = "first") String first,
                                                            @RequestParam(value = "first") String second) throws SQLException {
        FileCompareResponse fileCompareResponse = fileService.compareFile(first, second);
        return ResponseEntity.ok().body(fileCompareResponse);
    }
}

