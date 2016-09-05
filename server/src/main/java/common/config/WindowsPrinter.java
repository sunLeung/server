package common.config;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class WindowsPrinter extends PrintStream {
	
	public WindowsPrinter(OutputStream out) throws UnsupportedEncodingException{
		super(out, true, "UTF-8");
//		super(out, true);
	}

	@Override
	public void print(String s) {
		if (s == null)
			return;
		super.print(s);
	}

	@Override
	public void println(String s) {
		if (s == null)
			return;
		super.println(s);
	}

}
