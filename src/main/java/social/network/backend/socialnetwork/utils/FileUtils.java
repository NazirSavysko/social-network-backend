package social.network.backend.socialnetwork.utils;

import org.jetbrains.annotations.NotNull;
import social.network.backend.socialnetwork.exception.FileStorageException;

import java.io.IOException;
import java.nio.file.Path;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static java.util.UUID.randomUUID;

public final class FileUtils {
    private static final String THE_SOURCE_DIRECTORY = "D:\\images";
    private static final String FORMAT_IMAGE_IN_BASE64 = "data:image/%s;base64,%s";


    private FileUtils() {}
    public static @NotNull String writeToFile(String directoryName, @NotNull String content,final String suffix) {
        try {
            final Path dir = createDirectories(get(THE_SOURCE_DIRECTORY, directoryName));
            final Path tempFile = createTempFile(dir, generateFileName(), suffix);

            final byte[] data = getDecoder().decode(content);

            write(tempFile, data);
            return tempFile.toAbsolutePath().toString();
        } catch (final IOException e) {
            throw new FileStorageException("Error writing to file: " + directoryName, e);
        }
    }

    public static @NotNull String getContentFromFile(String filePath) {
        try {
            final Path path = get(filePath);
            final byte[] bytes = readAllBytes(path);
            final String suffix = path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf(".") + 1);

            final String base64 = getEncoder().encodeToString(bytes);

            return format(FORMAT_IMAGE_IN_BASE64, suffix, base64);
        } catch (final IOException e) {
            throw new FileStorageException("Error reading from file: " + filePath, e);
        }
    }

    public static void deleteFile(final String filePath) {
        try {
            deleteIfExists(get(filePath));
        } catch (final IOException e) {
            throw new FileStorageException("Error deleting file: " + filePath, e);
        }
    }

    private static @NotNull String generateFileName() {
        return currentTimeMillis() + "_" + randomUUID();
    }
}
