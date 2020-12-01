package dss.project.team.DSS.Utils;

import org.springframework.util.StringUtils;

public class StringUtil {
    public static boolean isValidParameter(String param){
        return StringUtils.hasText(param);
    }
}
