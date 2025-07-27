package social.network.backend.socialnetwork.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.currentTimeMillis;
import static java.nio.file.Files.*;
import static java.nio.file.Paths.get;
import static java.util.UUID.randomUUID;

public final class FileUtils {


    private static final String THE_SOURCE_DIRECTORY = "D:\\images";
    private static final String SUFFIX = ".txt";

    private FileUtils() {
    }

    public static @NotNull String writeToFile(final String directoryName,final String content) {
        try {

            final Path directoryPath = createDirectories(get(THE_SOURCE_DIRECTORY, directoryName));
            final Path file = Files.createTempFile(directoryPath,generateFileName(), SUFFIX);

            final Path path = writeString(file, content);

            return path.toFile().getAbsolutePath();
        } catch (final IOException e) {
            throw new ServerErrorException("Error writing to file", e);
        }
    }

    public static void deleteFile(final String filePath) {
        try {
            deleteIfExists(get(filePath));
        } catch (final IOException e) {
            throw new ServerErrorException("Error deleting file", e);
        }
    }

    public static @NotNull String getContentFromFile(final String filePath) {
        try {
            return readString(get(filePath));
        } catch (final IOException e) {
            throw new ServerErrorException("Error reading from file", e);
        }
    }

    private static @NotNull String generateFileName() {
        return currentTimeMillis() + "_" + randomUUID() + ".txt";
    }

}
