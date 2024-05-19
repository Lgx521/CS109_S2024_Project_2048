package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dataTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        long scecond = 70000;
        Date date = new Date(scecond);
        String sdfed = sdf.format(date);
        System.out.println(sdfed);
    }
}
