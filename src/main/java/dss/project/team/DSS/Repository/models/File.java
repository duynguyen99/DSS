package dss.project.team.DSS.Repository.models;

import org.springframework.stereotype.Component;

@Component
public class File {
    byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
