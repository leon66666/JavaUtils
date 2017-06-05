package zhongqiu.javautils.log;

public enum LogPath {

	Test("D:\\javalog\\test\\"),Utils("D:\\javalog\\utils\\");

	private String logPath;

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	private LogPath(String logPath) {
		setLogPath(logPath);
	}
}
