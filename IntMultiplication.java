import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class IntMultiplication {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String num1 = reader.readLine();
        String num2 = reader.readLine();
        System.out.print(multiplication(num1, num2));
    }

    public static String multiplication(String num1, String num2) {
        int num1Lenth = num1.length();
        int num2Length = num2.length();
        String paddedString = addLeadingZeros(num1, num2);

        if(num1Lenth > num2Length) {
            num2 = paddedString;
        } else if (num1Lenth < num2Length) {
            num1 = paddedString;
        }

        String result = multiplicationHelper(num1, num2);
        return removeLeadingZeros(result);
    }

    public static String multiplicationHelper(String num1, String num2) {

        if(num1.length() == 1 || num2.length() == 1) {
            String max = "", min = "", result = "0";

            if(num1.length() == 1) {
                max = num2;
                min = num1;
            } else {
                max = num1;
                min = num2;
            }

            int count = Integer.parseInt(min);
            for(; count > 0; count--) {
                result = addition(result, max);
            }
            return result;
        }

        int num1MidIndex = num1.length() / 2;
        String aLower = num1.substring(0, num1MidIndex);
        String aHigher = num1.substring(num1MidIndex);
        int aLowerLength = aLower.length();
        int aHigherLength = aHigher.length();

        int num2MidIndex = num2.length() / 2;
        String bLower = num2.substring(0, num2MidIndex);
        String bHigher = num2.substring(num2MidIndex);
        int bLowerLength = bLower.length();
        int bHigherLength = bHigher.length();

        if(aLowerLength != bLowerLength) {
            String paddedString = addLeadingZeros(aLower, bLower);
            if(aLowerLength < bLowerLength) {
                aLower = paddedString;
            } else {
                bLower = paddedString;
            }
        }

        if(aHigherLength != bHigherLength) {
            String paddedString = addLeadingZeros(aHigher, bHigher);
            if (aHigherLength < bHigherLength) {
                aHigher = paddedString;
            } else {
                bHigher = paddedString;
            }
        }

        String cLower = multiplicationHelper(aLower , bLower);
        String cHigher = multiplicationHelper(aHigher, bHigher);

        String aSum = addition(aLower, aHigher);
        String bSum = addition(bLower, bHigher);
        int aSumLength = aSum.length();
        int bSumLength = bSum.length();
        if(aSumLength != bSumLength) {
            String paddedString = addLeadingZeros(aSum, bSum);
            if(aSumLength < bSumLength) {
                aSum = paddedString;
            } else {
                bSum = paddedString;
            }
        }

        String c = subtraction(multiplicationHelper(aSum , bSum) , addition(cLower, cHigher));

        int n = num1.length();
        if(n%2 == 0) {
            return addition(addTrailingZeros(cLower, n) , addition(addTrailingZeros(c,n/2) , cHigher));
        } else {
            return addition(addTrailingZeros(cLower, 2 * ((n/2) + 1)) , addition(addTrailingZeros(c,((n/2) + 1)) , cHigher));
        }
    }

    public static String addition(String num1, String num2){
        int len1 = num1.length(), len2 = num2.length(), carryOver = 0;
        String result = "";
        String paddedString = addLeadingZeros(num1, num2);

        if(len1 > len2) {
            num2 = paddedString;
        } else if (len1 < len2) {
            num1 = paddedString;
        }

        for(int i = num1.length()-1; i>=0 ;i--){
            int temp = carryOver + number(num1.charAt(i)) + number(num2.charAt(i));
            if(temp >= 10) {
                carryOver = 1;
            } else {
                carryOver = 0;
            }
            result = (temp % 10) + result;
        }

        result = (carryOver == 0) ? result : carryOver + result;
        return result;
    }

    public static String subtraction(String num1, String num2) {

        int len1 = num1.length(), len2 = num2.length(), carryOver = 0;
        String result = "";
        String paddedString = addLeadingZeros(num1, num2);
        boolean swapped = false;

        if(len1 > len2) {
            num2 = paddedString;
        } else if (len1 < len2) {
            num1 = paddedString;
        }

        if(number(num1.charAt(0)) < number(num2.charAt(0))) {
            String temp = num2;
            num2 = num1;
            num1 = temp;
            swapped = true;
        }

        for(int i = num1.length()-1 ; i>=0; i--){

            int val1 = number(num1.charAt(i));
            int val2 = number(num2.charAt(i));
            val1 = carryOver + val1;

            if(val1 >= val2) {
                result = val1 - val2 + result;
                carryOver = 0;
            } else {
                result  = 10 + val1 - val2 + result;
                carryOver = -1;
            }
        }

        result = swapped ? "-" + result : result;
        return result;
    }

    public static String addLeadingZeros(String num1, String num2){

        int len1 = num1.length();
        int len2 = num2.length();
        String result = "";
        int difference = len1 - len2;

        if(difference < 0) {
            difference = difference * -1;
        } else if(difference == 0) {
            return result;
        }

        if(len1 > len2) {
            int index = 0;
            while (index < difference) {
                num2 = "0" + num2;
                index++;
            }
            result = num2;
        } else {
            int index = 0;
            while (index < difference) {
                num1 = "0" + num1;
                index++;
            }
            result = num1;
        }
        return result;
    }

    public static String addTrailingZeros(String num , int count){
        while(count-- > 0) {
            num += "0";
        }
        return num;
    }

    public static String removeLeadingZeros(String num) {
        for (int i = 0; i < num.length(); ++i) {
            if (num.charAt(i) != '0') {
                return num.substring(i);
            }
        }
        return "0";
    }

    private static int number(char ch) {
        return ch - '0';
    }
}