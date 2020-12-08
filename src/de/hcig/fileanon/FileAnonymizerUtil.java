package de.hcig.fileanon;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Vector;

/**
 * @author Dipl. Ing. Clemens Schartmüller, Technische Hochschule Ingolstadt
 * @version 24.06.2019
 * @since 24.06.2019
 **/
public class FileAnonymizerUtil {

    public static final String DATE_REGEX_DEFAULT = "(2[0-9][0-9][0-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-3][0-9])_(0[0-9]|1[0-9]|2[0-3])-([0-5][0-9])-([0-5][0-9])";

    public static List<File> anonymizeFiles(final List<File> filesToBeAnonymized, LocalDate newFileDate, String dateRegex) {
        List<File> filesSuccessFullyAnonymized = new Vector<>();

        FileTime fileTime = FileTime.from(newFileDate.atStartOfDay().toInstant(ZoneOffset.UTC));

        for (final File f : filesToBeAnonymized) {
            String fileName = f.getName();
            fileName = fileName.replaceAll(dateRegex, "");


            boolean success = false;

            if (!f.exists()) continue;

            File resultFileName = new File(f.getParent(), fileName);

            int duplIdx = 1;
            while (resultFileName.exists()) {
                int fileEndingIdx = fileName.lastIndexOf(".");
                resultFileName = new File(f.getParent(), fileName.substring(0, fileEndingIdx) + "__" + (duplIdx++) + fileName.substring(fileEndingIdx));
            }

            boolean renameSuccess = !f.renameTo(resultFileName);
            boolean modifySucceeeded = resultFileName.setLastModified(fileTime.toMillis());

            if (renameSuccess && modifySucceeeded) filesSuccessFullyAnonymized.add(resultFileName);

        }
        return filesSuccessFullyAnonymized;
    }

}
