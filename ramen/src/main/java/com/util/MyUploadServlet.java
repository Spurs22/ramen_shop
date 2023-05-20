package com.util;

import com.sun.jdi.ArrayReference;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
	// location = "c:/temp",			// 파일을 임시로 저장할 경로(생략가능. 기본값 ""), 지정된 경로가 없으면 업로드가 안됨
	fileSizeThreshold = 1024 * 1024,	// 업로드된 파일이 임시로 서버에 저장되지 않고 메모리에서 스트림으로 바로 전달되는 크기
	maxFileSize = 1024 * 1024 * 5,		// 업로드된 하나의 파일 크기. 기본 용량 제한 없음
	maxRequestSize = 1024 * 1024 * 10	// 폼 전체 용량
)
public abstract class MyUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		// 포워딩을 위한 메소드
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	/**
	 * 단일 파일 업로드
	 * 
	 * @param p			Part 객체
	 * @param pathname	서버에 파일을 저장할 경로
	 * @return			서버에 저장된 파일명, 클라이언트가 업로드한 파일명
	 */
	protected List<String> doFileUpload(Part p, String pathname) throws ServletException, IOException {
		List<String> list = null;

		try {
			File f = new File(pathname);
			if (!f.exists()) { // 폴더가 존재하지 않으면
				f.mkdirs();
			}

			String originalFilename = getOriginalFilename(p);
			if (originalFilename == null || originalFilename.length() == 0)
				return null;

			String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
			String saveFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
			saveFilename += System.nanoTime();
			saveFilename += fileExt;

			String fullpath = pathname + File.separator + saveFilename;

			System.out.println(fullpath);
			p.write(fullpath);

			list = new ArrayList<>();
			list.add(saveFilename);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 다중 파일 업로드
	 * 
	 * @param parts		클라이언트가 서버로 전송한 모든 Part 객체
	 * @param pathname	서버에 파일을 저장할 경로
	 * @return			서버에 저장된 파일명, 클라이언트가 올린 파일명
	 */
	protected List<String> doFileUpload(Collection<Part> parts, String pathname) throws ServletException, IOException {
		List<String> list = null;
		try {
			File f = new File(pathname);
			if (!f.exists()) { // 폴더가 존재하지 않으면
				f.mkdirs();
			}

			String original, save, ext;
			List<String> listOriginal = new ArrayList<>();
			List<String> listSave = new ArrayList<>();

			for (Part p : parts) {
				String contentType = p.getContentType();

				// contentType 가 null 인 경우는 파일이 아닌 경우이다.(<input type="text"... 등)
				if (contentType != null) { // 파일이면
					original = getOriginalFilename(p);
					if (original == null || original.length() == 0) {
						continue;
					}

					ext = original.substring(original.lastIndexOf("."));
					save = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
					save += System.nanoTime();
					save += ext;

					String fullpath = pathname + File.separator + save;
					p.write(fullpath);

					listOriginal.add(original);
					listSave.add(save);
					// Long size = p.getSize()); // 파일 크기
				}
			}

			if (listOriginal.size() != 0) {
//				String[] originals = listOriginal.toArray(new String[listOriginal.size()]);
//				String[] saves = listSave.toArray(new String[listSave.size()]);

				list = listSave;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private String getOriginalFilename(Part p) {
		try {
			for (String s : p.getHeader("content-disposition").split(";")) {
				if (s.trim().startsWith("filename")) {
					return s.substring(s.indexOf("=") + 1).trim().replace("\"", "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
