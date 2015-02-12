package br.gov.ms.defensoria.intranet.sapdp.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final Calendar   calendar = Calendar.getInstance();

    @SuppressWarnings("unused")
    private static final BigDecimal x        = new BigDecimal(
                                                     24 * 60 * 60 * 1000);

    public static Date createDate(int year, int month, int date, int hourOfDay,
            int minute, int second) {
        calendar.set(year, month, date, hourOfDay, minute, second);
        return calendar.getTime();
    }

    public static int getYear(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static Date getInitialDayOfMonth(int month, int year) {
        calendar.set(year, month, 1, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getFinalDayOfMonth(int month, int year) {
        calendar.set(year, month, 1, 23, 59, 59);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static BigDecimal numberOfDaysOnMonth(Date date1, Date date2,
            int month, int year) {
        Date initialDayOfMonth = getInitialDayOfMonth(month, year);
        Date finalDayOfMonth = getFinalDayOfMonth(month, year);
        return numberOfDaysBetweenDates(date1, date2, initialDayOfMonth,
                finalDayOfMonth);
    }

    @SuppressWarnings("unused")
    public static BigDecimal numberOfDaysBetweenDates(Date date1, Date date2,
            Date initialDate, Date finalDate) {
        if ((date1.before(finalDate) || (date1.equals(finalDate)))
                && (date2.after(initialDate) || date2.equals(initialDate))) {
            Date inicial;
            // Date i = date1.before(initialDate) ? initialDate : date1;
            if ((date1.before(initialDate)) || date1.equals(initialDate)) {
                inicial = initialDate;
            } else {
                inicial = date1;
            }
            Date termino;
            // Esta variavel divideMes serve para validação da qtd utilizada de
            // diarias por mes
            BigDecimal divideMes = BigDecimal.ZERO;
            // termino = date2.after(finalDate) ? finalDate : ;
            if (date2.after(finalDate) || date2.equals(finalDate)) {
                termino = finalDate;
                divideMes = BigDecimal.valueOf(0.5);
            } else {
                termino = date2;
            }

        }
        return BigDecimal.ZERO;
    }

    public static int diffInDays(Date d1, Date d2) {
        int MILLIS_IN_DAY = 86400000;

        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        c1.set(Calendar.MILLISECOND, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.HOUR_OF_DAY, 0);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        c2.set(Calendar.MILLISECOND, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.HOUR_OF_DAY, 0);
        return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / MILLIS_IN_DAY);
    }

    /**
     * Verifica se um período contém um determinado mês dentro de si.
     * 
     * @param date1
     *            início do período.
     * @param date2
     *            fim do período.
     * @param month
     *            mês a ser analisado.
     * @param year
     *            ano do mês a ser analisado.
     * @return <code>true</code> caso o período contenha o mês especificado.
     *         <code>false</code> caso contrário.
     * @O campo no método numberOfDaysOnMonth "", faz referencia ao tipo de
     *    pedido de diária quando utiliza esta função.
     */
    public static boolean containsMonth(Date date1, Date date2, int month,
            int year) {
        BigDecimal numberOfDaysOnMonth = numberOfDaysOnMonth(date1, date2,
                month, year);
        return numberOfDaysOnMonth.equals(BigDecimal.ZERO) ? false : true;
    }

    public static String retornaMes(Integer mes) {
        String mesString = "";
        if (mes == 1)
            mesString = "Janeiro";
        else if (mes == 2)
            mesString = "Fevereiro";
        else if (mes == 3)
            mesString = "Março";
        else if (mes == 4)
            mesString = "Abril";
        else if (mes == 5)
            mesString = "Maio";
        else if (mes == 6)
            mesString = "Junho";
        else if (mes == 7)
            mesString = "Julho";
        else if (mes == 8)
            mesString = "Agosto";
        else if (mes == 9)
            mesString = "Setembro";
        else if (mes == 10)
            mesString = "Outubro";
        else if (mes == 11)
            mesString = "Novembro";
        else if (mes == 12)
            mesString = "Dezembro";

        return mesString;

    }
    
    public String getDataPorExtenso(Date data){  
        DateFormat dfmt = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");  
        return dfmt.format(data);  
   }  
}
