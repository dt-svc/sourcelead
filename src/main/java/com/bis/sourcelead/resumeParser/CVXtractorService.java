package com.bis.sourcelead.resumeParser;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.lang.String;

public class CVXtractorService {
	private static String service_url = "";
	private static String rest_api_version = "/cvx/rest/api/v1/";
	private static String account = "";
	private static boolean json_out = false;
	private static String error_buf = "";
	private static String phase2_token = "";

	public CVXtractorService(String surl, String service_account, boolean jo) {
		service_url = surl;
		account = service_account;
		json_out = jo;
	}

	public boolean isReadyToUse() {
		return (account != "") ? true : false;
	}

	public String getLastError() {
		return error_buf;
	}

	public String getPhase2Token() {
		return phase2_token;
	}

	public String ProcessCV(String file_to_proces) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return "";
		}
		return ProcessCV(b, file_to_proces);
	}

	public String ProcessCV(byte[] b) {
		return ProcessCV(b, "unknown_name");
	}

	public String ProcessCV(byte[] b, String file_name) {
		error_buf = "";
		String hrxml_profile;
		try {
			hrxml_profile = executeStringPostCall("profile/full/" + ((json_out) ? "json" : "xml"), b, file_name);
		} catch (Exception e) {
			hrxml_profile = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}
		return hrxml_profile;
	}

	public String ProcessJobOrder(String file_to_proces) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return "";
		}
		return ProcessJobOrder(b, file_to_proces);
	}

	public String ProcessJobOrder(byte[] b) {
		return ProcessJobOrder(b, "unknown_name");
	}

	public String ProcessJobOrder(byte[] b, String file_name) {
		error_buf = "";
		String hrxml_profile;
		try {
			hrxml_profile = executeStringPostCall("joborder/" + ((json_out) ? "json" : "xml"), b, file_name);
		} catch (Exception e) {
			hrxml_profile = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}
		return hrxml_profile;
	}

	public String Convert(String file_to_proces, String to) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return "";
		}
		return Convert(b, file_to_proces, to);
	}

	public String Convert(byte[] b, String to) {
		return Convert(b, "unknown_name", to);
	}

	public String Convert(byte[] b, String file_name, String to) {
		error_buf = "";
		String hrxml_profile;
		try {
			hrxml_profile = executeStringPostCall("convert2" + to, b, file_name);
		} catch (Exception e) {
			hrxml_profile = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}
		return hrxml_profile;
	}

	public String ProcessCVphase1(String file_to_proces) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return "";
		}
		return ProcessCVphase1(b, file_to_proces);
	}

	public String ProcessCVphase1(byte[] b) {
		return ProcessCVphase1(b, "unknown_name");
	}

	public String ProcessCVphase1(byte[] b, String file_name) {
		error_buf = "";
		String hrxml_profile = "";
		/*
		 * for not to include JSON processing classes instead of calling
		 * "profile/personal/json" we will call "profile/personal/xml" which wil return
		 * XML outer structure but we will specify actual JSON output for profile
		 * through adding ";-OUT_SCHEMA json" instruction to account
		 */
		String the_call = "profile/personal/xml";
		// String the_call = "profile/full/xml";
		String account_saved = account;
		if (json_out) {
			if (account.matches(".*;.*") == false) {
				account += ";";
			}
			account += " -OUT_SCHEMA json";
		}
		try {
			// System.err.println("ZZZ:" + the_call);
			String multi_hrxml_profile = executeStringPostCall(the_call, b, file_name);
			// System.err.println("RET:" + multi_hrxml_profile);
			javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
			javax.xml.parsers.DocumentBuilder docBuilder = factory.newDocumentBuilder();
			final InputStream stream = new ByteArrayInputStream(multi_hrxml_profile.getBytes("UTF-8"));
			org.w3c.dom.Document doc = docBuilder.parse(stream);
			org.w3c.dom.NodeList list_tok = doc.getElementsByTagName("full_profile_token");
			if (list_tok.getLength() > 0) {
				phase2_token = list_tok.item(0).getTextContent();
				// System.err.println("Token=" + phase2_token);
			} else {
				System.err.println("No phase2 token returned");
			}
			org.w3c.dom.NodeList list_hrxml = doc.getElementsByTagName("hrxml");
			if (list_hrxml.getLength() > 0) {
				hrxml_profile = list_hrxml.item(0).getTextContent();
			}
		} catch (Exception e) {
			hrxml_profile = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
			account = account_saved;
		}
		return hrxml_profile;
	}

	public String ProcessCVphase2() {
		if (phase2_token == "") {
			System.err.println("No phase2 token");
			error_buf = "No succesfull ProcessCVphase1 call registerd so cannot do ProcessCVphase2";
			return "";
		}
		return executeStringGetCall("data?token=" + phase2_token);
	}

	public String ProcessCVbatch(String file_to_proces) {
		return ProcessCVbatch(file_to_proces, null);
	}

	public String ProcessCVbatch(String file_to_proces, String result_pattern) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return "";
		}
		return ProcessCVbatch(b, file_to_proces, result_pattern);
	}

	public String ProcessCVbatch(byte[] b, String result_pattern) {
		return ProcessCVbatch(b, "unknown_name", result_pattern);
	}

	public String ProcessCVbatch(byte[] b, String file_name, String result_pattern) {
		error_buf = "";
		String hrxml_profile = "";

		if (result_pattern == null) {
			result_pattern = file_name;
		}

		/*
		 * for not to include JSON processing classes instead of calling
		 * "profile/personal/json" we will call "profile/personal/xml" which wil return
		 * XML outer structure but we will specify actual JSON output for profile
		 * through adding ";-OUT_SCHEMA json" instruction to account
		 */
		String account_saved = account;
		String the_call = "profile/batch/";
		the_call += (json_out) ? "json" : "xml";
		try {
			hrxml_profile = executeStringPostCall(the_call, b, file_name);
			if (hrxml_profile == "") {
				error_buf = "Exception :: " + "no token returned";
				return hrxml_profile;
			}
			String main_tok = hrxml_profile;
			System.err.println("TOKEN: " + hrxml_profile);
			int bn = 0;
			for (int it = 0; it < 100; it++) {
				Thread.sleep(3000);
				hrxml_profile = executeStringGetCall("data?token=" + main_tok);
				// System.err.println("RET=" + hrxml_profile + "~");
				if (hrxml_profile == "") {
					System.err.println("No return " + error_buf);
					error_buf = "No return";
					break;
				}
				if (hrxml_profile == main_tok) {
					continue;
				} // -- go on
				if (hrxml_profile.indexOf("PROCESS_FINISHED") != -1) // -- end
				{
					System.err.println(hrxml_profile);
					break;
				}

				if (hrxml_profile.indexOf("PROCESS_NOT_FOUND") != -1) // -- end
				{
					System.err.println(hrxml_profile);
					break;
				}

				if (hrxml_profile.indexOf("CSERROR") != -1) {
					System.err.println(hrxml_profile);
					error_buf = hrxml_profile;
					hrxml_profile = "";
					break;
				}

				// -- save results - we actually need to base64 decode
				String filename = result_pattern + bn + ".zip";
				System.err.println("Saving to " + filename);
				String serr = saveToFiie(hrxml_profile, filename);
				if (serr != null) {
					error_buf = serr;
					hrxml_profile = "";
					break;
				}
				bn++;
			}
		} catch (Exception e) {
			hrxml_profile = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
			account = account_saved;
		}
		return hrxml_profile;
	}

	public byte[] ReformatCV(String file_to_proces, String template) {
		return ReformatCV(file_to_proces, template, null, null);
	}

	public byte[] ReformatCV(String file_to_proces, String template, String options, String hrxml) {
		error_buf = "";
		byte[] b = readFile(file_to_proces);
		if (b == null) {
			return null;
		}
		return ReformatCV(b, file_to_proces, template, options, hrxml);
	}

	public byte[] ReformatCV(byte[] b, String file_name, String template, String options, String hrxml) {
		error_buf = "";
		byte[] reformatted_document = null;
		try {
			reformatted_document = executePostCall("styler", b, file_name, template, options, hrxml);
		} catch (Exception e) {
			reformatted_document = null;
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}

		return reformatted_document;
	}

	public String InstallStylerTemplate(String new_template_file, String template) {
		error_buf = "";
		byte[] b = readFile(new_template_file);
		if (b == null) {
			return "";
		}
		return InstallStylerTemplate(b, new_template_file, template);
	}

	public String InstallStylerTemplate(byte[] b, String template) {
		return InstallStylerTemplate(b, "unknown_name", template);
	}

	public String InstallStylerTemplate(byte[] b, String file_name, String template) {
		error_buf = "";
		String response = "";
		try {
			response = executeStringPostCall("styler/template", b, file_name, template, null, null);
		} catch (Exception e) {
			response = "";
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}

		return response;
	}

	public byte[] RetrieveStylerTemplate(String template) {
		error_buf = "";
		byte[] response = null;
		try {
			response = executeGetCall("styler/template", template);
		} catch (Exception e) {
			response = null;
			error_buf = "Exception :: " + e.getMessage();
		} finally {
		}
		return response;
	}

	private String executeStringPostCall(String rest_call, byte[] b, String file_name) {
		byte[] post_response = executePostCall(rest_call, b, file_name);
		return (post_response != null) ? new String(post_response) : "";
	}

	private byte[] executePostCall(String rest_call, byte[] b, String file_name) {
		return executePostCall(rest_call, b, file_name, null, null, null);
	}

	private String executeStringPostCall(String rest_call, byte[] b, String file_name, String template, String options,
			String hrxml) {
		byte[] post_response = executePostCall(rest_call, b, file_name, template, options, hrxml);
		return (post_response != null) ? new String(post_response) : "";
	}

	private byte[] executePostCall(String rest_call, byte[] b, String file_name, String template, String options,
			String hrxml) {
		if (file_name == "") {
			file_name = "some_file";
		}

		String full_call = service_url + rest_api_version + rest_call;

		/*
		 * The POST request may be compressed using the gzip algorithm, in which case
		 * the HTTP request header Content?Encoding must be present and have "gzip" as
		 * value. A POST request compressed with gzip must be compressed by the client
		 * in its entirety (i.e. the whole message must be compressed, not the single
		 * parts of the multipart/form?data content). It is a client responsibility to
		 * check whether the server supports content compression, this is done by
		 * checking the value of the HTTP response header
		 * X?AlpineBits?Server?Accept?Encoding which is set to "gzip" by servers who
		 * support this feature. The so called "Housekeeping" actions must not be
		 * compressed.
		 */
		boolean use_gzip = false; // - we will zip the file itself instead
		// -- actually we will use "Content-Transfer-Encoding: gzip" on file part
		byte[] b_zip = (!use_gzip) ? compressByteArray(b, "gzip", file_name) : null;

		String boundary = "************123456789**********";
		HttpURLConnection urlc = null;
		try {
			URL url = new URL(full_call);
			urlc = (HttpURLConnection) url.openConnection();
			urlc.setDoOutput(true);
			urlc.setAllowUserInteraction(false);
			urlc.setUseCaches(false);
			urlc.setRequestProperty("charset", "utf-8");
			if (use_gzip) {
				urlc.setRequestProperty("Content-Encoding", "gzip");
			}
			urlc.setRequestProperty("Accept-Encoding", "gzip"); // "deflate"//- ask CVX server to compress
			urlc.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			urlc.setRequestMethod("POST");
			urlc.connect();

			// ------ send request data ------------
			DataOutputStream requestStream;
			if (use_gzip) {
				requestStream = new DataOutputStream(new java.util.zip.GZIPOutputStream(urlc.getOutputStream()));
			} else {
				requestStream = new DataOutputStream(urlc.getOutputStream());
			}

			String twoHyphens = "--";
			String crlf = "\r\n";
			// -- file part
			requestStream.writeBytes(twoHyphens + boundary + crlf);
			requestStream
					.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file_name + "\"" + crlf);
			requestStream.writeBytes("Content-Type: application/octet-stream" + crlf);
			if (b_zip != null)
				requestStream.writeBytes("Content-Transfer-Encoding: gzip" + crlf);
			requestStream.writeBytes(crlf);
			requestStream.write((b_zip != null) ? b_zip : b);
			requestStream.writeBytes(crlf);
			// -- account part
			requestStream.writeBytes(twoHyphens + boundary + crlf);
			requestStream.writeBytes("Content-Disposition: form-data; name=\"account\"" + crlf);
			requestStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
			requestStream.writeBytes(crlf);
			requestStream.writeBytes(account);
			requestStream.writeBytes(crlf);
			// -- reformatter template part
			if (template != null) {
				requestStream.writeBytes(twoHyphens + boundary + crlf);
				requestStream.writeBytes("Content-Disposition: form-data; name=\"template\"" + crlf);
				requestStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
				requestStream.writeBytes(crlf);
				requestStream.writeBytes(template);
				requestStream.writeBytes(crlf);
			}
			// -- reformatter options part
			if (options != null) {
				requestStream.writeBytes(twoHyphens + boundary + crlf);
				requestStream.writeBytes("Content-Disposition: form-data; name=\"options\"" + crlf);
				requestStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
				requestStream.writeBytes(crlf);
				requestStream.writeBytes(options);
				requestStream.writeBytes(crlf);
			}
			// -- reformatter hrxml part
			if (hrxml != null) {
				requestStream.writeBytes(twoHyphens + boundary + crlf);
				requestStream.writeBytes("Content-Disposition: form-data; name=\"hrxml\"" + crlf);
				requestStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
				requestStream.writeBytes(crlf);
				requestStream.writeBytes(hrxml);
				requestStream.writeBytes(crlf);
			}
			// -- closing boundary (with -- at the end) to signal end of form
			requestStream.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
			// -- finish with thre request
			requestStream.flush();
			requestStream.close();
		} catch (MalformedURLException e) {
			error_buf = "MalformedURLException :: " + e.getMessage();
		} catch (IOException e) {
			error_buf = "IOException :: " + e.getMessage();
		} catch (Exception e) {
			error_buf = "Exception :: " + e.getMessage();
		} finally {

		}

		return (urlc != null) ? readResponse(urlc) : null;
	}

	private static String executeStringGetCall(String rest_call) {
		byte[] get_response = executeGetCall(rest_call);
		return (get_response != null) ? new String(get_response) : "";
	}

	private static byte[] executeGetCall(String rest_call) {
		return executeGetCall(rest_call, null);
	}

	private static byte[] executeGetCall(String rest_call, String template) {
		String full_call = service_url + rest_api_version + rest_call;
		if (template != null) {
			full_call += "&account=" + account + "&template=" + template;
		}

		// System.err.println("FC:"+full_call);
		HttpURLConnection urlc = null;
		try {
			URL url = new URL(full_call);
			urlc = (HttpURLConnection) url.openConnection();
			urlc.setDoOutput(true);
			urlc.setAllowUserInteraction(false);
			urlc.setUseCaches(false);
			urlc.setRequestProperty("charset", "utf-8");
			urlc.setRequestProperty("Accept-Encoding", "gzip"); // - ask CVX server to compress
			urlc.setRequestMethod("GET");
			urlc.connect();
		} catch (MalformedURLException e) {
			error_buf = "MalformedURLException :: " + e.getMessage();
		} catch (IOException e) {
			error_buf = "IOException :: " + e.getMessage();
		} catch (Exception e) {
			error_buf = "Exception :: " + e.getMessage();
		}

		return (urlc != null) ? readResponse(urlc) : null;
	}

	private static byte[] readResponse(HttpURLConnection urlc) {
		byte[] output = null;
		try {
			// -- check response ------
			InputStream responseStream;
			if (urlc.getResponseCode() >= 200 && urlc.getResponseCode() < 400) {
				responseStream = urlc.getInputStream();
			} else {
				responseStream = urlc.getErrorStream();
			}

			// ----------- read result
			BufferedInputStream responseInputStream;
			if ("gzip".equalsIgnoreCase(urlc.getContentEncoding())) {
				// System.err.println("decompressing response gzip");
				responseInputStream = new BufferedInputStream(new java.util.zip.GZIPInputStream(responseStream));
			} else if ("deflate".equalsIgnoreCase(urlc.getContentEncoding())) {
				// System.err.println("decompressing response deflate");
				responseInputStream = new BufferedInputStream(new java.util.zip.InflaterInputStream(responseStream));
			} else {
				responseInputStream = new BufferedInputStream(responseStream);
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] chunk = new byte[4096];
			int bytesRead;
			while ((bytesRead = responseInputStream.read(chunk)) > 0) {
				outputStream.write(chunk, 0, bytesRead);
			}

			output = outputStream.toByteArray();
			outputStream.close();
			responseStream.close();
		} catch (IOException e) {
			error_buf = "IOException :: " + e.getMessage();
			output = null;
		} catch (Exception e) {
			error_buf = "Exception :: " + e.getMessage();
			output = null;
		} finally {
			urlc.disconnect();
		}

		return output;
	}

	private static byte[] readFile(String aInputFileName) {
		byte[] b;
		try {
			b = readFile_internal(aInputFileName);
		} /*
			 * catch (FileNotFoundException ex) {
			 * 
			 * } catch (IOException ex) {
			 * 
			 * }
			 */catch (Exception e) {
			error_buf = "Exception :: Cannot read file ::" + e.getMessage();
			return null;
		}
		return b;
	}

	private static byte[] readFile_internal(String aInputFileName) throws Exception {
		File file = new File(aInputFileName);
		byte[] result = new byte[(int) file.length()];

		InputStream input = null;
		try {
			int totalBytesRead = 0;
			input = new BufferedInputStream(new FileInputStream(file));
			while (totalBytesRead < result.length) {
				int bytesRemaining = result.length - totalBytesRead;
				// input.read() returns -1, 0, or more :
				int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
				if (bytesRead > 0) {
					totalBytesRead = totalBytesRead + bytesRead;
				}
			}
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return result;
	}

	private static byte[] compressByteArray(byte[] b, String ctype, String file_name) {
		byte[] b_zip;
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(b.length);

			if (ctype == null || ctype == "" || ctype == "zip") { // -- proper zip file with headers
				java.util.zip.ZipOutputStream zipStream = new java.util.zip.ZipOutputStream(outputStream);
				java.util.zip.ZipEntry ze = new java.util.zip.ZipEntry(file_name);
				zipStream.putNextEntry(ze);
				zipStream.write(b);
				zipStream.closeEntry();
				zipStream.close();
			} else if (ctype == "gzip") { // - no headers
				java.util.zip.GZIPOutputStream zipStream = new java.util.zip.GZIPOutputStream(outputStream);
				zipStream.write(b);
				zipStream.close();
			} else if (ctype == "deflate") {
				java.util.zip.Deflater deflater = new java.util.zip.Deflater();
				deflater.setInput(b);
				deflater.finish();
				byte[] buffer = new byte[1024];
				while (!deflater.finished()) {
					int count = deflater.deflate(buffer); // returns the generated code... index
					outputStream.write(buffer, 0, count);
				}
				deflater.end();
			}

			else {
				System.err.println("Unknown compression type " + ctype);
				return null;
			}

			outputStream.close();
			b_zip = outputStream.toByteArray();
			// System.err.println("Original: " + b.length / 1024 + " Kb");
			// System.err.println("Compressed: " + b_zip.length / 1024 + " Kb");
		} catch (Exception e) {
			System.err.println("Compress error:" + e.getMessage());
			// e.printStackTrace();
			return null;
		}

		return b_zip;
	}

	public static String saveToFiie(String content, String fileName) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			return e.getMessage();
		}
		return null;
	}

	public static void main(String args[]) {
		String usage = " -a process/process2/vacancy/batch/convert2html/convert2html_q/convert2pdf/convert2pdf_q -id ACCOUNT -host IP [-json] file_to_process\n";
		String host = null;
		String account = null;
		String file = null;
		boolean json_out = false;
		String action = "process";

		for (int j = 0; j < args.length; j++) {
			if (args[j].equals("-help")) {
				System.err.println(usage);
				System.exit(1);
			}
			if (args[j].equals("-host"))
				host = args[++j];
			else if (args[j].equals("-id"))
				account = args[++j];
			else if (args[j].equals("-a"))
				action = args[++j];
			else if (args[j].equals("-json"))
				json_out = true;
			else if (file == null)
				file = args[j];
			else {
				System.err.println("Unknown argument " + args[j]);
				System.err.println(usage);
				System.exit(1);
			}
		}
		if (file == null || host == null || account == null) {
			System.err.println(usage);
			System.exit(1);
		}
		try {
			CVXtractorService cvx = new CVXtractorService(host, account, json_out);

			String hrxml = "";
			if (action.equals("process")) {
				hrxml = cvx.ProcessCV(file);
			}
			if (action.equals("vacancy")) {
				hrxml = cvx.ProcessJobOrder(file);
			}
			if (action.equals("convert2html")) {
				hrxml = cvx.Convert(file, "html");
			}
			if (action.equals("convert2html_q")) {
				hrxml = cvx.Convert(file, "html_q"); // - extra quality
			}
			if (action.equals("convert2pdf")) {
				hrxml = cvx.Convert(file, "pdf");
			}
			if (action.equals("convert2pdf_q")) {
				hrxml = cvx.Convert(file, "pdf_q"); // - extra quality
			}
			if (action.equals("process2")) {
				System.err.println("phase 1");
				hrxml = cvx.ProcessCVphase1(file);
				System.out.println(hrxml);
				System.err.println("phase 2");
				if (cvx.getPhase2Token() != "" && cvx.getLastError() == "") {
					System.out.println(hrxml);
					hrxml = cvx.ProcessCVphase2();
				} else {
					System.err.println("PHASE2 error T=" + cvx.getPhase2Token());

				}
			}
			if (action.equals("batch")) {
				hrxml = cvx.ProcessCVbatch(file);
			}

			if (hrxml == "") {
				System.err.println("Error at processing :: " + cvx.getLastError());
			} else {
				System.out.println(hrxml);
			}

		} catch (Exception Exception) {
			System.err.println("Caught an Exception!");
			Exception.printStackTrace();
		}
	}
}
