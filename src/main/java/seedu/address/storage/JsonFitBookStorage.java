package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyFitBook;

/**
 * A class to access FitBook data stored as a json file on the hard disk.
 */
public class JsonFitBookStorage implements FitBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFitBookStorage.class);

    private Path filePath;

    public JsonFitBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFitBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFitBook> readFitBook() throws DataConversionException {
        return readFitBook(filePath);
    }

    /**
     * Similar to {@link #readFitBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFitBook> readFitBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFitBook> jsonFitBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableFitBook.class);
        if (!jsonFitBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFitBook.get().toFitBookModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFitBook(ReadOnlyFitBook addressBook) throws IOException {
        saveFitBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveFitBook(ReadOnlyFitBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFitBook(ReadOnlyFitBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFitBook(addressBook), filePath);
    }

}