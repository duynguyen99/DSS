package dss.project.team.DSS.Repository;

import dss.project.team.DSS.Repository.models.ORDFile;
import oracle.jdbc.OracleCallableStatement;
import oracle.ord.im.OrdImage;
import oracle.ord.im.OrdImageSignature;
import oracle.ord.im.OrdImageSignatureBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleResultSet;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class FileRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${upload.path}")
    private Path path;

    @Value("${upload.directory}")
    private String directory;

    /**
     *
     * @return  List ORD File
     * @throws SQLException
     * @throws IOException
     */
    public List<ORDFile> findAll(String fileName) throws SQLException, IOException {
        Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/duytest","dss","123456");
        if(conn == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot connect to database");
        }

        String query = "select image from image_table";
        if(fileName != null && fileName != ""){
            query += " WHERE name LIKE '%" + fileName + "%'";
        }
        PreparedStatement pstmt = conn.prepareStatement(query);
        OracleResultSet resultSet = (OracleResultSet)pstmt.executeQuery();

        List<ORDFile> files = new ArrayList<>();
        while (resultSet.next()) {
            OrdImage imgProxy = (OrdImage)resultSet.getORAData("image", OrdImage.getORADataFactory());
            ORDFile file = new ORDFile();
            file.setData(imgProxy.getDataInByteArray());
            file.setName(imgProxy.getSourceName());
            files.add(file);
        }
        conn.close();
        return files;
    }

    /**
     *
     * @param file
     * @return ORD File
     * @throws SQLException
     * @throws IOException
     */
    public ORDFile uploadFile(MultipartFile file) throws SQLException, IOException {
        Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/duytest","dss","123456");
        if(conn == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot connect to database");
        }
        String fileName = file.getOriginalFilename();
        Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING);
        CallableStatement cStmt = conn.prepareCall("{call insert_image(?, ?)}");
        cStmt.setString(1, directory);
        cStmt.setString(2, fileName);
        cStmt.execute();
        cStmt.close();
        conn.close();

        ORDFile ordFile = new ORDFile();
        ordFile.setName(file.getOriginalFilename());
        ordFile.setData(file.getBytes());
        return ordFile;
    }

    /**
     * compare two image
     * @param first
     * @param second
     * @return
     * @throws SQLException
     */
    public boolean compareImages(String first, String second) throws SQLException {
        int SIG_COL=1;
        double similarity_threshold= 20.0;
        Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/duytest","dss","123456");
        if(conn == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cannot connect to database");
        }
        String queryFirstImg = toQuerySearchFile(first);
        PreparedStatement pstmFirstImg = conn.prepareStatement(queryFirstImg);
        OracleResultSet resultSetFirstImg = (OracleResultSet)pstmFirstImg.executeQuery();
        OrdImageSignature imgSigFirst = null;
        if(resultSetFirstImg.next()){
            imgSigFirst = (OrdImageSignature)
                    resultSetFirstImg.getCustomDatum(
                            SIG_COL,OrdImageSignature.getFactory());
        }

        String querySecondImg = toQuerySearchFile(second);
        PreparedStatement pstmSecondImg = conn.prepareStatement(querySecondImg);
        OracleResultSet resultSetSecondImg = (OracleResultSet)pstmSecondImg.executeQuery();
        OrdImageSignature imgSigSecond = null;
        if(resultSetSecondImg.next()){
            imgSigSecond = (OrdImageSignature)
                    resultSetSecondImg.getCustomDatum(
                            SIG_COL,OrdImageSignature.getFactory());
        }
        if(imgSigFirst == null || imgSigSecond == null){
            return false;
        }
        int result = OrdImageSignature.isSimilar(imgSigFirst, imgSigSecond,
                "color=1 texture=1 shape=1 location=1",
                (float)similarity_threshold);
       return result == 1;
    }

    String toQuerySearchFile(String fileName){
        return "SELECT image from image_table WHERE name LIKE '" + fileName + "' AND ROWNUM = 1";
    }
}
