package ru.mfti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;


//REST API
@RestController
class SummatorController {
    
    @GetMapping("/make")
    public String arithmeticExpression(String expression) {
        return fun(expression);
    }
	
	//логику писать сюда
    public static String fun(String str){
        // проверяем само наличие строки
        if (str == null) {
            return "0";
        }

        // удаляем все пробелы из введенной Пользователем строки
        str = str.replaceAll("\\s", "");

        // проверяем не является ли строка пустой
        if (str.isBlank()) {
            return "0";
        }

        // список целых чисел из введенной строки
        ArrayList<Integer> numbers = new ArrayList<>();
        // список математических оперций применяемых к числам
        ArrayList<String> operands = new ArrayList<>();

        // выбираем из строки все числа
        String[] tempStr = str.split("[+-/*]");
        for (String elem: tempStr) {
            numbers.add(Integer.valueOf(elem));
        }

        // выбираем из строки все математические
        String tempSym;
        for (int i = 0; i < str.length(); i++) {
            tempSym = str.substring(i, i + 1);
            if ("+-/*".contains(tempSym)) {
                operands.add(tempSym);
            }
        }

        // в цикле сначала выполняем математические операции 1-го приоритета, т.е умножение и деление
        int indx, indxDiv, indxMulti;
        while ( operands.contains("*")  || operands.contains("/") ) {
            indxDiv = operands.indexOf("/");
            indxMulti = operands.indexOf("*");
            if (indxDiv != -1  && indxMulti != -1) {
                indx = Math.min(indxMulti, indxDiv);
            } else {
                indx = Math.max(indxMulti, indxDiv);
            }
            if (indxDiv == indx) {
                numbers.set(indx, numbers.get(indx) / numbers.get(indx + 1));
            } else {
                numbers.set(indx, numbers.get(indx) * numbers.get(indx + 1));
            }
            operands.remove(indx);
            numbers.remove(indx +1 );
        }

        // в цикле сначала выполняем математические операции 2-го приоритета, т.е сложение и вычитание
        int indxPlus, indxMinus;
        while ( operands.contains("+")  || operands.contains("-") ) {
            indxPlus = operands.indexOf("+");
            indxMinus = operands.indexOf("-");
            if (indxPlus != -1  && indxMinus != -1) {
                indx = Math.min(indxPlus, indxMinus);
            } else {
                indx = Math.max(indxPlus, indxMinus);
            }
            if (indxPlus == indx) {
                numbers.set(indx, numbers.get(indx) + numbers.get(indx + 1));
            } else {
                numbers.set(indx, numbers.get(indx) - numbers.get(indx + 1));
            }
            operands.remove(indx);
            numbers.remove(indx + 1);
        }
      return ""+numbers.get(0);
    }
}
