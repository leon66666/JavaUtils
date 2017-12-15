package zhongqiu.javautils;

import net.sf.jmimemagic.*;
import zhongqiu.javautils.entity.Gender;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


public class UtilTools {
    /**
     * 获得一个文件的后缀
     *
     * @param fileName
     * @return
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos < 0) {
            return "";
        }
        return fileName.substring(pos);
    }

    /**
     * 缩放图片
     *
     * @param Bi
     * @param desc
     * @param fileName
     * @throws Exception
     */
    public static void zoom(BufferedImage Bi, File desc, String fileName) throws Exception {
        try {

            File sImg = new File(desc, fileName);

            // 假设图片宽 高 最大为57 91,使用默认缩略算法
            Image Itemp = Bi.getScaledInstance(192, 192, BufferedImage.TYPE_INT_RGB);

            double Ratio = 0.0;
            if ((Bi.getHeight() > 192) || (Bi.getWidth() > 192)) {
                if (Bi.getHeight() > Bi.getWidth()) {
                    Ratio = 192.0 / Bi.getHeight();
                } else {
                    Ratio = 192.0 / Bi.getWidth();
                }

                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null);
                Itemp = op.filter(Bi, null);
            }
            ImageIO.write((BufferedImage) Itemp, getExtention(fileName).substring(1), sImg);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 获取上传的目录，通过判断URL
     *
     * @param path
     * @return
     */
    public static String getUploadPath(String path) {
        if (path.contains("profile")) {
            path = "/upload/user/profile";
        } else if (path.contains("loan")) {
            path = "/upload/user/loan";
        } else if (path.contains("tryLoan")) {
            path = "/upload/user/tryLoan";
        }
        return path;
    }

    public static Gender getSexFromID(String val) {
        Gender sex = Gender.MALE;
        // 15位身份证号码
        if (15 == val.length()) {
            if (Integer.valueOf(val.charAt(14) / 2) * 2 != val.charAt(14)) {
                sex = Gender.MALE;
            } else {
                sex = Gender.FEMALE;
            }
        } // 18位身份证号码
        else if (18 == val.length()) {
            if (Integer.valueOf(val.charAt(16) / 2) * 2 != val.charAt(16)) {
                sex = Gender.MALE;
            } else {
                sex = Gender.FEMALE;
            }
        }
        return sex;
    }

    /**
     * 从18位身份证获得生日
     * @param idNo
     * @return
     */
    public static Date getBirthdayFromID(String idNo) {
        return StringUtil.getDate(idNo.substring(6, 14), StringUtil.sdf_time_digit);
    }

    /**
     * 判断图片格式是否正确
     *
     * @param file
     * @return
     */
    public static boolean isVaildPictureFormat(File file) {
        try {
            if (file == null) {
                return false;
            }
            long fileLength = 3145728;
            if (file.length() > fileLength) {
                return false;
            }
            Magic m = new Magic();
            MagicMatch mm = m.getMagicMatch(file, false);
            String type = mm.getMimeType();
            if (type != null && type.startsWith("image/")) {
                return true;
            }
            return false;
        } catch (MagicParseException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicMatchNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 判断图片格式是否正确
     *
     * @return
     */
    public static boolean isVaildPictureFormatForMobile(byte[] bytes) {
        try {
            Magic m = new Magic();
            MagicMatch mm = m.getMagicMatch(bytes,false);
            String type = mm.getMimeType();
            if (type != null && type.startsWith("image/")) {
                return true;
            }
            return false;
        } catch (MagicParseException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicMatchNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //  Updated file check code, add the file type pdf ,,,start
    //  Wang 2013.05.08
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 判断当前上传类型是否为pdf
     */
    private static final String PdfMimeType = "application/pdf";
    private static final long PdfFileLength = 3145728l;
    public static boolean isVaildPdfFormat(File file) {
        try {
            if (file == null) {
                return false;
            }
            if (file.length() > PdfFileLength) {
                return false;
            }
            Magic m = new Magic();
            MagicMatch mm = m.getMagicMatch(file, false);
            String type = mm.getMimeType();

            if (type != null && PdfMimeType.equals(type.toLowerCase())) {
                return true;
            }
            //""
            return false;
        } catch (MagicParseException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicMatchNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (MagicException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //  Updated file check code, add the file type pdf ,,,end
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 判断视频格式是否正确
     *
     * @return
     */
    public static boolean isVaildVideoFormat(String fileName) {
        if (fileName == null || fileName.equals("")) {
            return false;
        }
        int index = fileName.lastIndexOf(".");
        if (index <= 0) {
            return false;
        }
        String str = fileName.substring(index + 1);
        if ("avi".equals(str) || "wmv".equals(str) || "mpg".equals(str) || "mpeg".equals(str)) {
            return true;
        }
        return false;
    }

    public static double roundScale(BigDecimal decimal, int scale) {
        return decimal.setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    public static double roundScaleDown(BigDecimal decimal, int scale) {
        return decimal.setScale(scale, RoundingMode.DOWN).doubleValue();
    }

    public static boolean isLinux() {
        if (System.getProperty("os.name").contains("linux") || System.getProperty("os.name").contains("Linux")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isMacOSX() {
        return System.getProperty("os.name").startsWith("Mac OS X");
    }

    /**
     * 创建不带日期格式的文件夹
     *
     * @param context
     * @param savePath
     * @param fileName
     * @return
     */
    public static File createDir(ServletContext context, String savePath, String fileName) {
        File uploadDir = new File(context.getRealPath(savePath));
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File file = new File(uploadDir + "/" + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        return file;
    }

    /**
     * 创建合同模板文件
     *
     * @param file
     * @param content
     * @throws IOException
     */
    public static void writeContractTemplate(File file, String content) throws IOException {
        StringBuilder header = new StringBuilder(
                "<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>\n"
                        + "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
                        + "<%@ taglib prefix=\"s\" uri=\"/struts-tags\"%>\n");
        header.append(content);
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            output.write(header.toString());
            output.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * @param dateStr 转化为日期的字符串
     * @return 转化后的日期
     */
    public static Date String2Date(String dateStr){
        return String2Date(dateStr,"");
    }
    /**
     * @param date 转化为日期的字符串
     * @return 转化后的日期
     */
    public static String Date2String(Date date){
        return Date2String(date,"");
    }
    /**
     * @param dateStr 转化为日期的字符串
     * @param format 转化格式
     * @return 转化后的日期
     */
    public static Date String2Date(String dateStr,String format){
        format= isNullOrEmpty(format)?"yyyy-MM-dd HH:mm:ss":format;
        try {
            if(isNullOrEmpty(dateStr)){
                throw new ParseException("空值不能转化为时间", 0);
            }
            SimpleDateFormat sim=new SimpleDateFormat(format);
            return sim.parse(dateStr);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param date 转化为字符串的日期
     * @param format 转化格式
     * @return 转化后的日期
     */
    public static String Date2String(Date date,String format){
        format= isNullOrEmpty(format)?"yyyy-MM-dd HH:mm:ss":format;
        try {
            if(isNullOrEmpty(date)){
                throw new ParseException("时间为null值", 0);
            }
            SimpleDateFormat sim=new SimpleDateFormat(format);
            return sim.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断不定个数的 Map,Collection,String,Array是否为非空
     * @param o
     * @return
     * 
     */
    @SuppressWarnings("all")
    public static boolean isAllNotNullOrEmpty(Object ... o) {
        // 遍历参数数组是使用
        boolean flag=true;
        for(int i=0;i<o.length;i++ ){
           if(isNullOrEmpty(o[i])){
               flag=false;
               break;
           }
        }
        return flag;
    }
    /**
     * 判断 Map,Collection,String,Array是否为空
     * @param o
     * @return
     * 
     */
    @SuppressWarnings("all")
    public static boolean isNullOrEmpty(Object o)  {
        if(o == null) {
            return true;
        }

        if(o instanceof String) {
            if(((String)o).length() == 0){
                return true;
            }
        } else if(o instanceof Collection) {
            if(((Collection)o).isEmpty()){
                return true;
            }else if("[null]".equals(((Collection)o).toString())){
                return true;  // hibernate 查询含有函数时，查不出数据但是封装的集合类会是[null]
            }
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map) {
            if(((Map)o).isEmpty()){
                return true;
            }
        }else {
            return false;
        }

        return false;
}

    /**
     * 保留最多maxLength个字符(全角占两个),超过的话,最后3位改为...
     * @param s
     * @param maxLength
     * @return
     */
    public static String lengthReserve(String s, int maxLength) {
        try {
            if (s == null || "".equals(StringUtil.trim(s))) {
                return s;
            }
            byte[] bytes = s.getBytes("Unicode");
            int n = 0; // 表示当前的字节数
            int i = 2; // 要截取的字节数，从第3个字节开始
            for (; i < bytes.length && n < maxLength; i++) {
                // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
                if (i % 2 == 1) {
                    n++; // 在UCS2第二个字节时n加1
                } else {
                    // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                    if (bytes[i] != 0) {
                        n++;
                    }
                }
            }
            // 如果i为奇数时，处理成偶数
            if (i % 2 == 1) {
                // 该UCS2字符是汉字时，去掉这个截一半的汉字
                if (bytes[i - 1] != 0) {
                    i = i - 1;
                }// 该UCS2字符是字母或数字，则保留该字符
                else {
                    i = i + 1;
                }
            }
            if (i < bytes.length - 1 - 3) {
                return UtilTools.lengthReserve(new String(bytes, 0, i, "Unicode"), maxLength - 3) + "...";
            }
            return new String(bytes, 0, i, "Unicode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    /**
     * 10203转化成1.2.3存储类型
     *
     * @param versionNumber
     * @return
     * @throws Exception
     */
    public static String versionInteger2Str(Integer versionNumber) {
        if (null == versionNumber) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(Integer.toString(versionNumber / 10000));
            versionNumber = versionNumber % 10000;
            buffer.append(".");
            buffer.append(versionNumber / 100);
            versionNumber = versionNumber % 100;
            buffer.append(".");
            buffer.append(versionNumber);
            return buffer.toString();
        }
    }

    public static int getOverDueDays(Date d1, Date d2) {
        return DateUtil.getBetweenDayNumberForNormal(d1, d2);
    }
}