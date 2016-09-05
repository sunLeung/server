package common.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LinuxPrinter extends PrintStream implements Runnable {

	private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	private static String lineseparator = null;

	public LinuxPrinter(OutputStream out) throws UnsupportedEncodingException {
		super(out, true, "UTF-8");
//		super(out, true);
		lineseparator = System.getProperty("line.separator");
		if (lineseparator == null)
			lineseparator = "\n";
	}

	@Override
	public void print(String s) {
		if (s == null)
			return;
		checkException(s);
		queue.add(s);
	}

	@Override
	public void println(String s) {
		if (s == null)
			return;
		checkException(s);
		queue.add(s + lineseparator);
	}

	@Override
	public void print(Object s) {
		if (s == null)
			return;
		checkException(s.toString());
		queue.add(s.toString());
	}

	@Override
	public void println(Object s) {
		if (s == null)
			return;
		checkException(s.toString());
		queue.add(s + lineseparator);
	}
	
	private void checkException(String s){
		if(Config.TRY_GET_ERROR_STACK&&s.indexOf("NullPointerException")!=-1){
			for(StackTraceElement e:Thread.currentThread().getStackTrace()){
				queue.add(e.toString()+lineseparator);
			}
		}
	}

	public void print(int i) {
		queue.add(String.valueOf(i));
	}

	public void print(long l) {
		queue.add(String.valueOf(l));
	}

	public void println(int i) {
		queue.add(String.valueOf(i) + lineseparator);
	}

	public void println(long l) {
		queue.add(String.valueOf(l) + lineseparator);
	}

	@Override
	public void run() {
		File f = new File(System.getProperty("user.dir")+File.separator+ "sysout" +File.separator + LocalDate.now().toString() + ".log");
		if (!f.exists())
			f.getParentFile().mkdirs();

		try {
			if (!f.exists())
				f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("[INFO] "+f.getAbsolutePath());
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f, true));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String obj = null;
		while (Config.PRINTER_RUN) {
			try {
				int t = 0;
				ConcurrentLinkedQueue<String> clq = queue;
				while ((obj = clq.poll()) != null) {
					bw.write(obj);
					t++;
					if (t % 10 == 0) {
						bw.flush();
					}
				}
				clq = null;
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(20);
				} catch (Exception e) {
				}
			}
		}
		try {
			if (bw != null)
				bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
