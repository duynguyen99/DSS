package dss.project.team.DSS.Service;

import dss.project.team.DSS.Service.Response.FileCompareResponse;
import dss.project.team.DSS.Service.Response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public interface IFileService {
    /**
     * get list file from database
     * @return List<FileResponse>
     * @param name: name of file
     * @throws SQLException
     * @throws IOException
     */
   List<FileResponse> getFileList(String name) throws SQLException, IOException;

    /**
     * store single file
     * @param file
     * @throws IOException
     */
    FileResponse uploadFile(MultipartFile file) throws IOException;

    /**
     * compare two image
     * @param first
     * @param second
     * @return
     * @throws SQLException
     */
    FileCompareResponse compareFile(String first, String second) throws SQLException;
}
