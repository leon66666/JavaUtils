package zhongqiu.javautils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by wangzhongqiu on 2017/6/6.
 * 计算工具类
 */
public class NumberUtil {
    private static DecimalFormat df = new DecimalFormat("#####,##0.00");
    private static DecimalFormat ddf = new DecimalFormat("###,###,##0");
    private static NumberFormat nf = new DecimalFormat("0000");
    private static NumberFormat snf = new DecimalFormat("0000000");
    private static NumberFormat mnf = new DecimalFormat("0.00");
    private static NumberFormat mbnf = new DecimalFormat("0000");

    public static double round(double n, int scale) {
        BigDecimal decimal = new BigDecimal(n + "");
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public static double round(double n) {
        return round(n, 2);
    }

    public static double doubleAdd(double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal("" + n));
        }

        return result.doubleValue();
    }

    public static double doubleAddScaled(double... dd) {
        BigDecimal result = BigDecimal.ZERO;
        for (double n : dd) {
            result = result.add(new BigDecimal("" + n));
        }

        return scale(result);
    }

    public static double doubleSubtract(double d1, double d2) {
        return new BigDecimal("" + d1).subtract(new BigDecimal("" + d2)).doubleValue();
    }

    public static double doubleSubtractScaled(double d1, double d2) {
        return scale(new BigDecimal("" + d1).subtract(new BigDecimal("" + d2)));
    }

    private static double scale(BigDecimal decimal) {
        return decimal.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static double formatDouble2(double d1, double d2) {
        return doubleAdd(d1, d2);
    }

    public static double formatDouble3(double d1, double d2, double d3) {
        return doubleAdd(d1, d2, d3);
    }

    public static double formatDouble3Scaled(double d1, double d2, double d3) {
        return doubleAddScaled(d1, d2, d3);
    }

    public static double formatNubmer(double d) {
        BigDecimal bd = new BigDecimal(d + "");
        return scale(bd);
    }

    public static String formatNubmerString(double d) {
        BigDecimal bd = new BigDecimal(d + "");
        double dd = scale(bd);
        return df.format(dd);
    }

    public static String formatNubmerStringNoScaled(double d) {
        BigDecimal bd = new BigDecimal(d + "");
        double dd = scale(bd);
        return ddf.format(dd);
    }

    public static String format(double d, String fmt) {
        DecimalFormat df = new DecimalFormat(fmt);
        return df.format(d);
    }

    public static String format(double d, String fmt,RoundingMode roundingMode) {
        DecimalFormat df = new DecimalFormat(fmt);
        df.setRoundingMode(roundingMode );
        return df.format(d);
    }


    public static String numberStringScaled2(double d) {
        BigDecimal bd = new BigDecimal(d + "");
        double dd = scale(bd);
        return mnf.format(dd);
    }

    public static String numberStringMbnf(double d) {
        BigDecimal bd = new BigDecimal(d + "");
        double dd = scale(bd);
        return mbnf.format(dd);
    }

    public static double formatDouble6Scaled(double d1, double d2, double d3, double d4, double d5, double d6) {
        return doubleAddScaled(doubleSubtract(d1, d2), d3, d4, d5, d6);
    }

    /**
     * 货币形式格式字符串
     *
     * @param ####,### 0.00
     * @param value
     */
    public static String formatNumberString(String format, String value) {
        if (StringUtil.isEmpty(format) || StringUtil.isEmpty(value)) {
            return "";
        }
        BigDecimal bd = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat(format);
        return df.format(bd);
    }

    /**
     * 相加 取位 d1+d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleAddScaled(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();

    }

    public static double doubleAddScaledRoundingMode(double d1, double d2, int scale,RoundingMode roundingMode) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).setScale(scale, roundingMode).doubleValue();

    }

    /**
     * 相减，取位 d1-d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleSubtractScaled(double d1, double d2, int scale) {
        BigDecimal decimal = new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2)));
        return decimal.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 相除 取位 d1/d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleDivide(double d1, double d2, int scale) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        BigDecimal bd3 = bd1.divide(bd2, 10, RoundingMode.HALF_EVEN);
        return bd3.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * 相乘 取位 d1*d2 n.scale
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double doubleMultiply(double d1, double d2, int scale) {
        return doubleMultiplyScaleN(scale,d1,d2);
    }

    public static double doubleMultiplyScale2(double... dd) {
        return doubleMultiplyScaleN(2,dd);
    }

    private static double doubleMultiplyScaleN(int scale, double... dd) {
        if(dd.length==0){
            throw new RuntimeException("multiply parameters is empty!");
        }
        BigDecimal result = BigDecimal.ONE;
        for (double n : dd) {
            result = result.multiply(new BigDecimal(Double.toString(n)));
        }
        return result.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }
    public static double doubleMultiplyScaleN(int scale, RoundingMode roundingMode,double... dd) {
        if(dd.length==0){
            throw new RuntimeException("multiply parameters is empty!");
        }
        BigDecimal result = BigDecimal.ONE;
        for (double n : dd) {
            result = result.multiply(new BigDecimal(Double.toString(n)));
        }
        return result.setScale(scale, roundingMode).doubleValue();
    }


    public static double doubleMultiplyScaleDown2(double... dd) {
        return doubleMultiplyScaleDown(2,dd);
    }

    private static double doubleMultiplyScaleDown(int scale, double... dd) {
        if(dd.length==0){
            throw new RuntimeException("multiply parameters is empty!");
        }
        BigDecimal result = BigDecimal.ONE;
        for (double n : dd) {
            result = result.multiply(new BigDecimal(Double.toString(n)));
        }
        return result.setScale(scale, RoundingMode.DOWN).doubleValue();
    }

    public static void main(String[] args) {
        // double b = formatDouble6Scaled(8, 2, 5, 4, 7, 9);
        System.out.println(doubleMultiplyScale2(0.560, 15.30, 0.050));
        System.out.println(NumberUtil.format(123456789001.0, "0"));
        System.out.println(NumberUtil.format(0, "0"));
        System.out.println(doubleMultiplyScaleDown2(0.560, 15.30, 0.050));
        String num="22";
        System.out.println("\n"+formatNumberString("##.###",num));
        System.out.println("\n"+formatNumberString("##.0##",num));
        System.out.println("\n"+formatNumberString("#0000.00#",num));
        System.out.println(BigDecimalMathUtil.div("1230","10000",3));
        System.out.println(numberFormatConvert(new BigDecimal(2110300)));
        System.out.println(numberFormatConvert(new BigDecimal(1200)));
        System.out.println(numberFormatConvert(new BigDecimal(220)));
    }

    /**
     * 将整数转化为四位数字
     *
     * @param number
     * @return
     */
    public static String formatFourNumber(Integer number) {
        return nf.format(number);
    }

    /**
     * 将整数转化为六位数字
     *
     * @param number
     * @return
     */
    public static String formatSixNumber(Integer number) {
        return snf.format(number);
    }

    /**
     * 将整数转化为八位数字
     *
     * @param number
     * @return
     */
    public static String formatEightNumber(Integer number) {
        return snf.format(number);
    }

    /**
     * 将字符串转换为decimal
     * @param number
     * @return
     */
    public static BigDecimal toDecimal(String number){
        if(StringUtil.isEmpty(number)){
            return BigDecimal.ZERO;
        }else{
            try {
                return new BigDecimal(number);
            } catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
    }

    /**百千万
     * 1000-1千
     */
    public static String numberFormatConvert(BigDecimal number)
    {
        String result=String.valueOf(number);
        if (number!=null)
        {
            String numParam=String.valueOf(number);
            double resultNum1=BigDecimalMathUtil.div(numParam,"10000",2); //产品定义，不处理不能被100整除的数据
            double resultNum2=BigDecimalMathUtil.div(numParam,"1000",1);
            double resultNum3=BigDecimalMathUtil.div(numParam,"100",0);
            if (resultNum1>=1)
            {
                result=format(resultNum1,"##.###")+"万";
            }else if (resultNum2>=1)
            {
                result=format(resultNum2,"#.##")+"千";
            }else if (resultNum3>=1)
            {
                result=format(resultNum3,"#.##")+"百";
            }
        }
        return result;
    }
}
