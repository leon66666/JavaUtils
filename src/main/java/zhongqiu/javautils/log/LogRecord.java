package zhongqiu.javautils.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogRecord {
	public static void writeLog(LogPath logPath, String logName, String logContent) {
		if (!logName.endsWith(".txt")) {
			logName = logName + ".txt";
		}
		if (!logContent.endsWith("\r\n")) {
			logContent = logContent + "\r\n";
		}
		String logPathTemp = logPath.getLogPath() + new SimpleDateFormat("yyyy-MM-dd HH ").format(new Date()) + logName;
		try {
			File fileDir = new File(logPath.getLogPath());
			fileDir.mkdirs();
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(logPathTemp, true), "UTF-8"));
			bw.write(logContent);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
