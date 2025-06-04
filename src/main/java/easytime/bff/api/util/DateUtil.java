package easytime.bff.api.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate convertUserDateToDBDate(String dateBr) {
        try{
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateBr, inputFormatter);
        } catch (DateTimeException e){
            throw new DateTimeException("Data inválida: " + dateBr + ", Por favor, insira uma data válida no formato DD/MM/AAAA.");
        }
    }
}