package dss.project.team.DSS.Service.impl;
import dss.project.team.DSS.Repository.FileRepository;
import dss.project.team.DSS.Repository.models.File;
import dss.project.team.DSS.Repository.models.ORDFile;
import dss.project.team.DSS.Service.IFileService;
import dss.project.team.DSS.Service.Response.FileCompareResponse;
import dss.project.team.DSS.Service.Response.FileResponse;
import dss.project.team.DSS.Utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    FileRepository fileRepository;

    @Override
    public List<FileResponse> getFileList(String fileName) throws SQLException, IOException {
        List<ORDFile> files = fileRepository.findAll(fileName);
        List<FileResponse> fileResponses = files.stream().map(file -> new FileResponse(file)).collect(Collectors.toList());
        return fileResponses;
    }

    @Override
    public FileResponse uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Failed to store empty file");
        }
        FileResponse fileResponse;
        try {
            ORDFile fileUploaded = fileRepository.uploadFile(file);
            fileResponse = new FileResponse(fileUploaded);
        } catch (SQLException | IOException e) {
            String msg = String.format("Failed to store file %f", file.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    msg);
        }
        return fileResponse;
    }

    @Override
    public FileCompareResponse compareFile(String first, String second) throws SQLException {
        if (!StringUtil.isValidParameter(first) || !StringUtil.isValidParameter(second)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "missing request params, required two file name");
        }
        return new FileCompareResponse(fileRepository.compareImages(first,second));
    }
}
