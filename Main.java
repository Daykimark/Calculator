import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws CalcException {
        System.out.print(calc("VII / II"));
    }
    public static String calc(String input) throws CalcException {
        boolean mode = getMode(input);
        StringTokenizer tokenizer = new StringTokenizer(input, "+/*- ");
        if (mode) {
            int first = RimToInt(tokenizer.nextToken());
            int second = RimToInt(tokenizer.nextToken());
            if (compute(first, second, input) <= 0) {
                throw new CalcException("Wrong Rome Expression");
            }
            return IntToRim(compute(first, second, input));
        }
        int first = tokenizer.nextToken().charAt(0) - 48;
        int second = tokenizer.nextToken().charAt(0) - 48;
        return compute(first, second, input).toString();
    }

    public static short RimToInt(String number) throws CalcException {
        short sum = 0;
        for (int i = 0; i < number.length(); i++) {
            if (i >= 1 && getIntDigit(number.charAt(i)) > getIntDigit(number.charAt(i - 1))) {
                sum += getIntDigit(number.charAt(i)) - 2 * getIntDigit(number.charAt(i - 1));
            } else {
                sum += getIntDigit(number.charAt(i));
            }
        }
        if (sum > 9) {
            throw new CalcException("Wrong Rome Expression");
        }
        return sum;
    }

    public static int getIntDigit (char digit) throws CalcException {
        return switch (digit) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new CalcException("Wrong Rome Expression");
        };
    }

    public static String IntToRim (int number) {
        if (number >= 1000) return "M" + IntToRim(number - 1000);
        if (number >= 900) return "CM" + IntToRim(number - 900);
        if (number >= 500) return "D" + IntToRim(number - 500);
        if (number >= 400) return "CD" + IntToRim(number - 400);
        if (number >= 100) return "C" + IntToRim(number - 100);
        if (number >= 90) return "XC" + IntToRim(number - 90);
        if (number >= 50) return "L" + IntToRim(number - 50);
        if (number >= 40) return "XL" + IntToRim(number - 40);
        if (number >= 10) return "X" + IntToRim(number - 10);
        if (number >= 9) return "IX" + IntToRim(number - 9);
        if (number >= 5) return "V" + IntToRim(number - 5);
        if (number >= 4) return "IV" + IntToRim(number - 4);
        if (number >= 1) return "I" + IntToRim(number - 1);
        return "";
    }

    public static boolean getMode(String input) throws CalcException {
        if (input.matches("^[XIV]{1,3} [+/\\*-] [XIV]{1,3}$")) return true;
        else if (input.matches("^\\d{1,3} [+/\\*-] \\d{1,3}$")) return false;
        throw new CalcException("Wrong Expression");
    }

    public static Integer compute(int first, int second, String input) throws CalcException {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+') {
                return first + second;
            } else if (input.charAt(i) == '-') {
                return first - second;
            } else if (input.charAt(i) == '*') {
                return first * second;
            }
        }
        if (second == 0) throw new CalcException("Wrong Decimal Expression");
        return first / second;
    }
}
class CalcException extends Exception {
    public CalcException(String message) { super(message); }
}
