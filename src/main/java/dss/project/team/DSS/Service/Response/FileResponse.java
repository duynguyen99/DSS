package dss.project.team.DSS.Service.Response;
import dss.project.team.DSS.Repository.models.ORDFile;

public class FileResponse {
    public String name;
    public byte[] data;
    public FileResponse(ORDFile file){
        if(file == null){
            return;
        }
        this.name = file.getName();
        this.data = file.getData();
    }
}
