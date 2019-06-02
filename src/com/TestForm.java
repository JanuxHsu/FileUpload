package com;

import static com.Constants.UPLOAD_DIRECTORY;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

@WebServlet("/TestForm")
public class TestForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Charset utf8 = Charset.forName("UTF-8");
		request.setCharacterEncoding(utf8.name());
		Map<String, String[]> params = request.getParameterMap();

		params.entrySet().stream().forEach(item -> {
			System.out.println(item.getKey() + " : " + String.join(", ", item.getValue()));
		});

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		ProgressListener progressListener = new ProgressListener() {
			private long lastReadByte = -1;
			long mBytes = 1024 * 1000 * 100;

			public void update(long pBytesRead, long pContentLength, int pItems) {
				if (pBytesRead == pContentLength) {
					System.out.println("100%");
				}
				if (mBytes > pBytesRead - lastReadByte) {
					return;
				}

				lastReadByte = pBytesRead;
				if (pContentLength == -1) {
					System.out.println("So far, " + pBytesRead + " bytes have been read.");
				} else {
					String read = FileUtils.byteCountToDisplaySize(pBytesRead);
					String total = FileUtils.byteCountToDisplaySize(pContentLength);
					Double readVal = new Long(pBytesRead).doubleValue();
					Double totalVal = new Long(pContentLength).doubleValue();

					Double percent = readVal / totalVal * 100;

					System.out.println("We are currently reading item " + pItems);

					System.out.println(read + "/" + total + " | " + String.format("%.2f", percent) + "%");
				}
			}

		};

		upload.setProgressListener(progressListener);

		try {

			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream item = iter.next();

				System.out.println(item.getFieldName() + ", " + item.isFormField());
				if (!item.isFormField()) {

					streamProcessFormField(item);
				}
			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void streamProcessFormField(FileItemStream item) {
		String fieldName = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();
		Map<String, Object> list = new HashMap<>();
		list.put("fieldName", fieldName);
		list.put("fileName", fileName);
		list.put("contentType", contentType);

		list.entrySet().stream().forEach(System.out::println);

		try (InputStream stream = item.openStream()) {
			File targetFile = Paths.get(System.getProperty("jboss.server.temp.dir"), UPLOAD_DIRECTORY, fileName)
					.toFile();
			FileUtils.copyInputStreamToFile(stream, targetFile);

			System.out.println("Copy done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
