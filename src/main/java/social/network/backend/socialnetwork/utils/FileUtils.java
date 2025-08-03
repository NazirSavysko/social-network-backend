package social.network.backend.socialnetwork.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.nio.file.Path;

import static java.lang.System.currentTimeMillis;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.UUID.randomUUID;

public final class FileUtils {
    private static final String THE_SOURCE_DIRECTORY = "D:\\images";
    private static final String SUFFIX = ".png";

    private FileUtils() {

    }
    public static @NotNull String writeToFile(String directoryName, @NotNull String content) {
        try {
            final Path dir = createDirectories(get(THE_SOURCE_DIRECTORY, directoryName));
            final Path tempFile = createTempFile(dir, generateFileName(), SUFFIX);

            final byte[] data = getDecoder().decode(content);

            write(tempFile, data);
            return tempFile.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new ServerErrorException("Error writing to file: " + directoryName, e);
        }
    }

    public static @NotNull String getContentFromFile(String filePath, final String mimeType) {
        try {
            final Path path = get(filePath);
            final byte[] bytes = readAllBytes(path);

            final String base64 = getEncoder().encodeToString(bytes);
            return "data:" + mimeType + ";base64," + base64;
        } catch (IOException e) {
            throw new ServerErrorException("Error reading from file: " + filePath, e);
        }
    }

    public static void deleteFile(final String filePath) {
        try {
            deleteIfExists(get(filePath));
        } catch (IOException e) {
            throw new ServerErrorException("Error deleting file: " + filePath, e);
        }
    }

    private static @NotNull String generateFileName() {
        return currentTimeMillis() + "_" + randomUUID();
    }
}
