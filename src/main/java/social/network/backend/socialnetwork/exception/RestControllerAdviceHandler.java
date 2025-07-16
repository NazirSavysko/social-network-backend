package social.network.backend.socialnetwork.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public final class RestControllerAdviceHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public @NotNull ResponseEntity<?> handleIllegalArgumentException(@NotNull IllegalArgumentException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public @NotNull ResponseEntity<?> handleSQLException(@NotNull SQLException e) {

        return ResponseEntity
                .internalServerError()
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public @NotNull ResponseEntity<?> handleNoSuchElementException() {

        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    public @NotNull ResponseEntity<?> handleNullPointer(@NotNull NullPointerException e) {

        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
