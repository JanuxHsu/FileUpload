package com;

import static com.Constants.UPLOAD_DIRECTORY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.mail.imap.protocol.Item;

@WebServlet(name = "MultiPartServlet", urlPatterns = { "/multiPartServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 1024 * 5, maxRequestSize = 1024 * 1024
		* 1024 * 5)
public class MultipartServlet extends HttpServlet {

	private static final long serialVersionUID = -5347344322577504651L;
	Charset utf8 = Charset.forName("UTF-8");

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {

				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}

		}
		return Constants.DEFAULT_FILENAME;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(utf8.name());

		File default_dest_dir = Paths.get(System.getProperty("jboss.home.dir"), UPLOAD_DIRECTORY).toFile();
		String dest_path_dir = Optional.ofNullable(request.getParameter("fileDestination"))
				.orElse(default_dest_dir.toString());

		File uploadDir = new File(dest_path_dir);

		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			String fileName = "";
			for (Part part : request.getParts()) {

				System.out.println(part.getSize());
				fileName = getFileName(part);

				File uploadFileDest = Paths.get(uploadDir.toString(), fileName).toFile();
				System.out.println(uploadDir.toString());
				if (!uploadFileDest.exists()) {

					System.out.println(uploadFileDest.toString());

					// part.write(uploadFileDest.toString());
				} else {
					request.setAttribute("message", "File " + fileName + " exists!!");
				}

			}
			request.setAttribute("message", "File " + fileName + " has uploaded successfully!");
		} catch (FileNotFoundException fne) {
			request.setAttribute("message", "There was an error: " + fne.getMessage());
		}

		response.getWriter().println((request.getAttribute("message")));
	}
}
