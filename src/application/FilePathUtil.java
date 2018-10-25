package application;
/*
 * 根据系统自动转换路径符
 */
public class FilePathUtil {
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static String getFilePath(String path) {
		return path.replace("/", FILE_SEPARATOR).replace("\\", FILE_SEPARATOR);
	}
	public static String getURLPath(String path) {
		return path.replace("\\", "/");
	}
	
}
