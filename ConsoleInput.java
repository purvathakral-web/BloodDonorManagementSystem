package utility;

import java.util.Scanner;

/**
 * ============================================================================
 * CLASS: ConsoleInput
 * SYLLABUS TOPIC: Unit 1B - Wrapper Classes (Integer.parseInt, Double.parseDouble)
 *                 Unit 1C - try, catch, finally & NumberFormatException handling
 * ============================================================================
 *
 * Defensive console input helper wrapping java.util.Scanner(System.in).
 * Prevents crashes when a user enters text instead of numeric input.
 */
public class ConsoleInput
{
    private final Scanner scanner;

    public ConsoleInput()
    {
        this.scanner = new Scanner(System.in);
    }

    public String readLine(String prompt)
    {
        System.out.print(prompt);
        if (!scanner.hasNextLine())
        {
            return "";
        }
        return scanner.nextLine().trim();
    }

    public String readNonEmptyLine(String prompt)
    {
        while (true)
        {
            String value = readLine(prompt);
            if (!value.isEmpty())
            {
                return value;
            }
            System.out.println("  [Error] Input cannot be empty. Please try again.");
        }
    }

    public int readInt(String prompt)
    {
        while (true)
        {
            String raw = readLine(prompt);
            try
            {
                return Integer.parseInt(raw);
            }
            catch (NumberFormatException ex)
            {
                System.out.println("  [Error] '" + raw + "' is not a valid whole number. Please try again.");
            }
        }
    }

    public int readIntInRange(String prompt, int min, int max)
    {
        while (true)
        {
            int number = readInt(prompt);
            if (number >= min && number <= max)
            {
                return number;
            }
            System.out.println("  [Error] Please enter a number between " + min + " and " + max + ".");
        }
    }

    public double readDouble(String prompt)
    {
        while (true)
        {
            String raw = readLine(prompt);
            try
            {
                return Double.parseDouble(raw);
            }
            catch (NumberFormatException ex)
            {
                System.out.println("  [Error] '" + raw + "' is not a valid decimal number. Please try again.");
            }
        }
    }

    public boolean readYesNo(String prompt)
    {
        while (true)
        {
            String raw = readLine(prompt + " (Y/N): ");
            if ("Y".equalsIgnoreCase(raw) || "YES".equalsIgnoreCase(raw))
            {
                return true;
            }
            if ("N".equalsIgnoreCase(raw) || "NO".equalsIgnoreCase(raw))
            {
                return false;
            }
            System.out.println("  [Error] Please enter Y or N.");
        }
    }

    public void close()
    {
        scanner.close();
    }
}
