package org.shenefelt.Helpers;

import org.shenefelt.Controller.TableMangers.UserTableManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class InputValidator
{
    private static ArrayList<Integer> valid_user_ids = UserTableManager.getUserIDs();
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]\\.[a-z0-9]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static ArrayList<Integer> validMainMenuChoices = new ArrayList<>();
    private static ArrayList<Integer> validUserMenuChoices = new ArrayList<>();



    // Default no-args constructor
    public InputValidator()
    {
    }

    // All-args constructor
    public InputValidator(ArrayList<Integer> valid_user_ids)
    {
        InputValidator.valid_user_ids = valid_user_ids;
    }

    /**
     * Validate that the user ID supplied by the user is valid
     * @param id the id of the user we have selected
     * @return true if the user ID is valid, false if the ID is not valid.
     */
    public static boolean validateUserSelectionByID(int id)
    {
        if (valid_user_ids.isEmpty())
            valid_user_ids = UserTableManager.getUserIDs();

        if (valid_user_ids.contains(id))
            return true;

        out.println("Invalid User ID. Try again");
        return false;
    }

    /**
     * Validate user input when creating new users
     * @param username the new username
     * @param email the new email
     * @param password the new password
     * @return true if all pass the check, false if else.
     */
    public static boolean validateNewUserInput(String username, String email, String password)
    {
        return (validateUsername(username) &&
                validateEmail(email) &&
                validatePassword(password));
    }

    /**
     * Ensure that a username was entered
     * @param username the new username
     * @return true if the new username passes the check, false if else.
     */
    private static boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            out.println("Username cannot be empty.");
            return false;
        }

        // only assign values after the first check passes (save mem)
        Pattern p = Pattern.compile(USERNAME_REGEX);
        Matcher m = p.matcher(username);
        boolean match = m.matches();

        if (!match)
            out.println("Invalid username format.");

        return match;
    }

    /**
     * Ensure the custom email provided for the user is in the proper format
     * @param email
     * @return
     */
    private static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            out.println("Email cannot be empty.");
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            out.println("Invalid email format.");
            return false;
        }
        return true;
    }

    /**
     * Validate the user's new password before hashing and storing in the database
     * @param password the password we will validate.
     * @return true if it passes the regex, false if not.
     */
    private static boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            out.println("Password must be at least 8 characters long.");
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        // loop through each character in the word to check for proper combinations.
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            if (hasLetter && hasDigit) {
                return true;
            }
        }

        out.println("Password must contain both letters and numbers.");
        return false;
    }


    public static boolean validateFullName(String fullName)
    {
        boolean is_valid = fullName != null && !fullName.trim().isEmpty();

        if(!is_valid)
        {
            out.println("Full name cannot be empty..");
        }

        return is_valid;
    }


    public static boolean nonEmptyString(String S)
    {
        // make sure the String isn't null or blank.
        return !S.trim().isBlank() && !S.isEmpty();
    }


    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBoolean(String input) {
        return "true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input);
    }


    public static boolean isValidMainMenuChoice(int choice)
    {
        // make sure the array list has the choices in it.
        if(validMainMenuChoices.isEmpty())
            setupMainMenuChoicesList();

        return validMainMenuChoices.contains(choice);
    }


    private static void setupMainMenuChoicesList()
    {
        for(int i = 0; i < 5; i++)
            validMainMenuChoices.add(i + 1);

    }


    public static boolean validUserMenuChoice(int choice)
    {
        if(validUserMenuChoices.isEmpty())
            setupUserMenuChoicesList();

        return validUserMenuChoices.contains(choice);
    }

    private static void setupUserMenuChoicesList()
    {
        for(int i = 0; i < 5; i++)
            validUserMenuChoices.add(i + 1);
    }


}