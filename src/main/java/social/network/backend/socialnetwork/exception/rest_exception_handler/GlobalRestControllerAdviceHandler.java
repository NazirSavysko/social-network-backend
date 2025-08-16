package social.network.backend.socialnetwork.exception.rest_exception_handler;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import social.network.backend.socialnetwork.exception.FileStorageException;

import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.status;


@RestControllerAdvice
public final class GlobalRestControllerAdviceHandler {

    private static final Logger LOGGER = getLogger(GlobalRestControllerAdviceHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public @NotNull ResponseEntity<?> handleIllegalArgumentException(final @NotNull IllegalArgumentException e) {
        LOGGER.warn("IllegalArgumentException: {}", e.getMessage(), e);

        return status(BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }

    @ExceptionHandler(SQLException.class)
    public @NotNull ResponseEntity<?> handleSQLException(final @NotNull SQLException e) {
        LOGGER.error("SQLException: {}", e.getMessage(), e);

        return status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }

    @ExceptionHandler(FileStorageException.class)
    public @NotNull ResponseEntity<?> handleFileStorage(final @NotNull FileStorageException e) {
        LOGGER.error("FileStorageException: {}", e.getMessage(), e);

        return status(INTERNAL_SERVER_ERROR)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public @NotNull ResponseEntity<?> handleNoSuchElementException(final @NotNull NoSuchElementException e) {
        LOGGER.info("NoSuchElementException: {}", e.getMessage(), e);

        return status(NOT_FOUND)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }

    @ExceptionHandler(NullPointerException.class)
    public @NotNull ResponseEntity<?> handleNullPointer(final @NotNull NullPointerException e) {
        LOGGER.error("NullPointerException: {}", e.getMessage(), e);

        return status(BAD_REQUEST)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }

    @Contract("_ -> new")
    private @NotNull @Unmodifiable Map<String, String> getErrorAttributes(final @NotNull Throwable throwable) {
        return Map.of("error", throwable.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public @NotNull ResponseEntity<?> handleBadCredentialsException(final @NotNull BadCredentialsException e) {
        LOGGER.error("BadCredentialsException: {}", e.getMessage(), e);

        return status(UNAUTHORIZED)
                .contentType(APPLICATION_JSON)
                .body(getErrorAttributes(e));
    }
}
