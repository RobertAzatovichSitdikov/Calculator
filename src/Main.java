import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String betaexp = scn.nextLine();
        String exp = betaexp.replaceAll(" ","");
        String result = calc(exp);

        if (result != null) System.out.println(result);

    }

    public static String calc(String exp) {
        Converter converter = new Converter();
        char[] actions = {'+', '-', '/', '*'};
        String[] regexActions = {"\\+", "-", "/","\\*"};

        try {

            int s = 0;
            int actionIndex=-1;
            for (int i = 0; i < exp.length(); i++) {
                for (int j = 0; j < actions.length; j++) {
                    if (exp.charAt(i) == (actions[j])) {
                        actionIndex = j;
                        s += 1;
                    }
                }
            }
            if(actionIndex==-1){
                throw new IOException("Некорректное выражение, нет арифмитических операций");
            }
            if(s>1){
                throw new IOException("В выражении должна быть одна арифмитическая операция");
            }



            String[] data = exp.split(regexActions[actionIndex]);

            if(converter.isRoman(data[0]) == converter.isRoman(data[1])){
                int a,b;

                boolean isRoman = converter.isRoman(data[0]);
                if(isRoman){

                    a = converter.romanToInt(data[0]);
                    b = converter.romanToInt(data[1]);

                } else {

                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                }

                // Здесь условие с ислкючением от 1 до 10
                if (1 > a || a > 10 || 1 > b || b > 10 ) {
                    throw new IOException("Числа на вход должны быть от 1 до 10");
                }

                int result;
                switch (actions[actionIndex]){
                    case '+':
                        result = a+b;
                        break;
                    case '-':
                        result = a-b;
                        break;
                    case '*':
                        result = a*b;
                        break;
                    default:
                        result = a/b;
                        break;
                }

                if(isRoman){
                    if (result<1) throw new IOException("В римской системе счисления результат не может быть меньше единицы");
                    return converter.intToRoman(result);
                }
                else{

                    return String.valueOf(result);
                }
            } else {
                throw new IOException("Числа должны быть в одном формате");
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}