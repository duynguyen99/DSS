package dss.project.team.DSS.Service.Response;

public class FileCompareResponse {
    private boolean result;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public FileCompareResponse(boolean result) {
        this.result = result;
    }
}
