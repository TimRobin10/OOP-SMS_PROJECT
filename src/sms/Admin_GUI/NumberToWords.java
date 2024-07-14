package sms.Admin_GUI;

import java.text.DecimalFormat;

public class NumberToWords {

    public static void main(String[] args) {
        System.out.println(convert(1000.56));
    }

    public static String convert(double number) {
        if (number == 0) {
            return "zero pesos";
        }

        DecimalFormat df = new DecimalFormat("#.00");
        String formattedNumber = df.format(number);

        String[] parts = formattedNumber.split("\\.");

        long wholePart = Long.parseLong(parts[0]);
        int decimalPart = Integer.parseInt(parts[1]);

        long billions = wholePart / 1_000_000_000;
        long millions = (wholePart / 1_000_000) % 1_000;
        long thousand = (wholePart / 1_000) % 1_000;
        long units = wholePart % 1_000;

        String result = "";
        if (billions > 0) {
            result += convertLessThanOneThousand((int) billions) + " billion ";
        }
        if (millions > 0) {
            result += convertLessThanOneThousand((int) millions) + " million ";
        }
        if (thousand > 0) {
            result += convertLessThanOneThousand((int) thousand) + " thousand ";
        }
        if (units > 0) {
            result += convertLessThanOneThousand((int) units);
        }

        result = result.trim() + " pesos";

        if (decimalPart > 0) {
            result += " and " + convertLessThanOneThousand(decimalPart) + " centavos";
        }

        return result.trim();
    }

    private static String convertLessThanOneThousand(int number) {
        String[] tensNames = {
                "", " ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety"
        };

        String[] numNames = {
                "", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"
        };

        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }
}
