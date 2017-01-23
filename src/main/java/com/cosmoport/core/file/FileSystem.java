package com.cosmoport.core.file;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileSystem {
    private final Path root;
    private final Logger logger;

    /**
     * Default constructor.
     *
     * @param context An absolute root path
     */
    @Inject
    public FileSystem(Logger logger, @Named("fileSystemRoot") String context) {
        this.logger = logger;

        root = Paths.get(context).normalize();
        logger.info("root: " + root);
    }

    public String resolve(String... paths) {
        String resolvedPath = resolvePath(paths).normalize().toString();
        logger.info("resolve: " + resolvedPath);

        return resolvedPath + File.separator;
    }

    public Path resolveAsPath(String... paths) {
        return resolvePath(paths).normalize();
    }

    private Path resolvePath(String... paths) {
        return Paths.get(root.toString(), paths);
    }

    public void move(Path source, Path destination) throws IOException {
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        logger.info("file moved from [" + source + "] to [" + destination + ']');
    }

    /**
     * Calculates the SHA-1 checksum of the file.
     *
     * @param file The file to read.
     * @return The hex value of the SHA-1 in uppercase.
     * @throws IOException              If an I/O error occurs.
     * @throws NoSuchAlgorithmException If Java is broken.
     */
    public static String getSHA1(final File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");

        try (InputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int len = input.read(buffer);

            while (len != -1) {
                sha1.update(buffer, 0, len);
                len = input.read(buffer);
            }

            return new HexBinaryAdapter().marshal(sha1.digest());
        }
    }
}
