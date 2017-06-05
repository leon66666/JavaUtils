package zhongqiu.javautils.test;

import zhongqiu.javautils.log.LogPath;
import zhongqiu.javautils.log.LogRecord;

public class LogTest {
	public static void main(String[] args)
	{
		LogRecord.writeLog(LogPath.Test, "test", "这是记录的日志1");
		LogRecord.writeLog(LogPath.Utils, "test", "这是记录的日志1");
		System.out.println("ok");
	}
}
