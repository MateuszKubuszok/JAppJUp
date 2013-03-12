package net.jsdpu.logger;

import static net.jsdpu.logger.Level.parse;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class PrettyFormatter extends Formatter {
	private final DateFormat formatter;
	private String lastClass;
	private final Map<String,String> lastMethodForClass;
	
	public PrettyFormatter() {
		formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS z");
		lastClass = null;
		lastMethodForClass= new HashMap<String,String>();
	}
	
	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();
		
		Level level = parse(record.getLevel());
		String className = record.getSourceClassName();
		String methodName = record.getSourceMethodName();
		Date date = new Date(record.getMillis());
		String message = record.getMessage();
		Throwable throwable = record.getThrown();
		
		appendClass(builder, className);
		appendMethod(builder, className, methodName);
		appendMessage(builder, level, date, message);
		appendException(builder, throwable);
		
		lastClass = className;
		lastMethodForClass.put(className, methodName);
		
		return builder.toString();
	}
	
	private void appendClass(StringBuilder builder, String className) {
		if (lastClass == null
				|| !lastClass.equals(className))
			builder.append(className).append(":\n");
	}
	
	private void appendMethod(StringBuilder builder, String className, String methodName) {
		if (lastClass == null
				|| !lastClass.equals(className)
				|| !lastMethodForClass.containsKey(className)
				|| !lastMethodForClass.get(className).equals(methodName)) {
				builder.append('\t').append(methodName).append('\n');
		}
	}
	
	private void appendMessage(StringBuilder builder, Level level, Date date, String message) {
		builder.append("\t\t[").append(level)
				.append("] ").append(formatter.format(date))
				.append(":\n");
		builder.append("\t\t").append(message).append('\n');
	}
	
	private void appendException(StringBuilder builder, Throwable throwable) {
		if (throwable != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(bos);
			throwable.printStackTrace(pw);
			
			for (String s : new String (bos.toByteArray()).split("\n"))
				builder.append("\t\t").append(s).append('\n');
		}
	}
}
