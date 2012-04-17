/*
 * Check.java
 *
 * Created on April 5, 2012, 7:03 PM
 *
 
 */

package Check;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author 
 */
public class Check {
    
    /** Creates a new instance of Check */
    public Check() {
    }
    /**
     * Method to check Input-Name
     * @return boolean true or false
     * @param n 
     */
    public static boolean checkName(String n) {

        if (n == null || n.length() == 0) {
            return false;
        } else {
            String strPattern = "[^a-z ]";

            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return !m.find();
        }
    }
    /**
     * Method  checkSpace(),to check value is space.
     * @return boolean true or false .
     * @param n 
     */
    public static boolean checkSpace(String n) {
        String strPattern = "[^ ]";
        Pattern p;
        Matcher m;
        int flag = Pattern.CASE_INSENSITIVE;
        p = Pattern.compile(strPattern, flag);
        m = p.matcher(n);
        return m.find();

    }
    public static boolean checkNhay(String n) {
        String strPattern = "[^']";
        Pattern p;
        Matcher m;
        int flag = Pattern.CASE_INSENSITIVE;
        p = Pattern.compile(strPattern, flag);
        m = p.matcher(n);
        return m.find();
    }
    /**
     * Method to check Email
     * @return boolean true or false
     * @param n 
     */
    public static boolean checkEmail(String n) {
        if (n == null) {
            return false;
        } else {
            String strPattern = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return m.find();
        }
    }
    
    public static boolean checkWebsite(String n) {
        if (n == null) {
            return false;
        } else {
            String strPattern = "[a-z]\\.[a-z]";
            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return m.find();
        }
    }
    /**
     * Method to check Phone
     * @return boolean true or false
     * @param n 
     */
    public static boolean checkPhone(String n) {
        if (n == null || n.length() > 15 || n.length() < 7) {
            return false;
        } else {
            String strPattern = "[^0-9]";
            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return !m.find();
        }
    }
        /**
     * Method checkNumber(), to check input number.
     * @return boolean true or false.
     * @param n 
     */
    public static boolean checkNumber(String n) {
        if (n == null || n.length() > 10  || n.length() < 1) {
            return false;
        } else {
            String strPattern = "[^0-9]";
            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return !m.find();
        }
    }
    /**
     * Method check(), to check are not null.
     * @return boolean true or false.
     * @param n 
     */
    public static boolean check(String n) {
        if (n == null || n.length() == 0) {
            return false;
        }
        return true;
    }
     /**
     * Method  checkID(),to  check input ID.
     * @return boolean true or false .
     * @param n 
     */
    public static boolean checkID(String n) {
        if (n == null || n.length() > 10 || n.length() < 2) {
            return false;
        } else {
            String strPattern = "[^a-z0-9]";
            Pattern p;
            Matcher m;
            int flag = Pattern.CASE_INSENSITIVE;
            p = Pattern.compile(strPattern, flag);
            m = p.matcher(n);
            return !m.find();

        }
    }
    public static boolean checkDateBirthDay(String n) {
        if (n == null || n.length() < 10) {
            return false;
        } else {
            boolean flag = true;
            String dd, mm, yyyy;
            int d, m, y;
            yyyy = n.substring(0, 4);
            dd = n.substring(8, 10);
            mm = n.substring(5, 7);
            try {
                Date dat = new Date();
                Calendar date = Calendar.getInstance();
                date.setTime(dat);
                int y1 = date.get(Calendar.YEAR);
                d = Integer.parseInt(dd);
                m = Integer.parseInt(mm);
                y = Integer.parseInt(yyyy);
                if ((d < 1) && (d > 31)) {
                    flag = false;
                }
                if ((m < 1) && (m > 12)) {
                    flag = false;
                }
                if (y < 1) {
                    flag = false;
                }
                if (y1 - y < 18) {
                    flag = false;
                }
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }
    }
    /**
     * Method  checkDateIn(),to check limit to date.
     * @return boolean true or false.
     * @param n 
     */
    public static boolean checkDateIn(String n) {
        if (n == null || n.length() < 10) {
            return false;
        } else {
            boolean flag = true;
            String dd, mm, yyyy;
            int d, m, y;
            yyyy = n.substring(0, 4);
            mm = n.substring(5, 7);
            dd = n.substring(8, 10);
            try {
                Date dat = new Date();
                Calendar date = Calendar.getInstance();
                date.setTime(dat);
                int y1 = date.get(Calendar.YEAR);
                int m1 = date.get(Calendar.MONTH);
                int d1 = date.get(Calendar.DAY_OF_MONTH);
                m = Integer.parseInt(mm);
                d = Integer.parseInt(dd);
                y = Integer.parseInt(yyyy);
                if ((d < 1) && (d > 31)) {
                    flag = false;
                }
                if ((m < 1) && (m > 12)) {
                    flag = false;
                }
                if (y < 1) {
                    flag = false;
                }
                if (y1 < y) {
                    flag = false;
                }
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }
    }
}
