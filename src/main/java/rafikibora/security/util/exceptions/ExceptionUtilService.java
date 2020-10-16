package rafikibora.security.util.exceptions;

import org.springframework.orm.jpa.JpaSystemException;

public class ExceptionUtilService {
    public static boolean isSqlDuplicatedModelType(JpaSystemException e) {
        return false;
    }
}
