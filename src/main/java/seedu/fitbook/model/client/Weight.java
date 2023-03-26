package seedu.fitbook.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.fitbook.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Client's Weight in FitBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {


    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain positive numbers and cannot be 0";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";
    public final String value;
    public final Date date;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        value = weight;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
        date = new Date(LocalDateTime.now().format(dateTimeFormatter));
    }

    /**
     * Constructs a {@code Weight}.
     *
     * @param newWeight A valid weight.
     */
    public Weight(Date newDate, String newWeight) {
        requireNonNull(newWeight);
        requireNonNull(newDate);
        checkArgument(isValidWeight(newWeight), MESSAGE_CONSTRAINTS);
        value = newWeight;
        date = newDate;
    }

    /**
     * Returns true if a given string is a weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Date getDate() {
        return date;
    }

    public String getWeight() {
        return value;
    }

    @Override
    public String toString() {
        return date + ":" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && value.equals(((Weight) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
