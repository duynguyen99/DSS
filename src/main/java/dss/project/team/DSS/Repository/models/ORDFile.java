package dss.project.team.DSS.Repository.models;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ORDFile extends File {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
