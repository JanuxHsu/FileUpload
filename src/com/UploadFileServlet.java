package com;

import static com.Constants.UPLOAD_DIRECTORY;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.jms.MapMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

/**
 * Servlet implementation class UploadFileServlet
 */
@WebServlet("/UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			return;
		}

		int chunkCount = 0;

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 4);

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		ProgressListener progressListener = new ProgressListener() {

			@Override
			public void update(long pBytesRead, long pContentLength, int pItems) {
				System.out.println(pBytesRead);
			}

		};

		upload.setProgressListener(progressListener);

		// Parse the request
		try {
			// List<FileItem> items = upload.parseRequest(request);

			FileItemIterator iter = upload.getItemIterator(request);

			// Iterator<FileItem> iter = items.iterator();

			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();

				InputStream stream = item.openStream();
				if (item.isFormField()) {
					System.out.println("Form field " + name + " with value " + Streams.asString(stream) + " detected.");

				} else {
					System.out.println("File field " + name + " with file name " + item.getName() + " detected.");
					streamProcessFormField(item);

				}
			}

//			while (iter.hasNext()) {
//				FileItem item = iter.next();
//
//				if (item.isFormField()) {
//					processFormField(item);
//				} else {
//
//					processUploadedFile(item);
//				}
//			}
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

	}

	private void processUploadedFile(FileItem item) {
		String fieldName = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();

		boolean isInMemory = item.isInMemory();
		long sizeInBytes = item.getSize();

		// String name = item.getFieldName();
		String value = item.getString();

		Map<String, Object> list = new HashMap<>();

		// list.put("name", name);
		list.put("fieldName", fieldName);
		list.put("fileName", fileName);
		list.put("contentType", contentType);
		list.put("isInMemory", isInMemory);
		list.put("sizeInBytes", sizeInBytes);

		list.entrySet().stream().forEach(System.out::println);

		File default_dest_dir = Paths.get(System.getProperty("jboss.server.temp.dir"), UPLOAD_DIRECTORY).toFile();

		File uploadDir = default_dest_dir;

		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		File uploadedFile = Paths.get(uploadDir.toString(), fileName).toFile();
		try {
			item.write(uploadedFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processFormField(FileItem item) {
		String fieldName = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();
		boolean isInMemory = item.isInMemory();
		long sizeInBytes = item.getSize();

		Map<String, Object> list = new HashMap<>();

		list.put("fieldName", fieldName);
		list.put("fileName", fileName);
		list.put("contentType", contentType);
		list.put("isInMemory", isInMemory);
		list.put("sizeInBytes", sizeInBytes);

		list.entrySet().stream().forEach(System.out::println);

	}

}
