package com.qudini.ws;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.qudini.ws.response.WebServiceResponse;

@RestController
public class DownloadController {

	private final Logger log = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	private WebServiceEndPointSetting setting;

	// CALLED BY UI: 02.24.2017
	@RequestMapping(value = "/attachment/upload", produces = "application/json")
	public WebServiceResponse attachmentUpload(HttpSession session, @Context HttpServletRequest request,
			String settings) throws IOException, ServletException {
		String charset = "UTF-8";
		String requestURL = setting.getUrl() + "/dummy/upload";
		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			multipart.addHeaderField("Content-Type", "application/json");
			multipart.addHeaderField("Accept", "application/json");

			Map<String, String> fieldMap = new HashMap<>();
			StringBuilder responseStrBuilder = new StringBuilder();
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();
						fieldMap.put(fieldName, fieldValue);
					} else {
						InputStream input = item.getInputStream();
						BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
						String inputStr;
						while ((inputStr = streamReader.readLine()) != null)
							responseStrBuilder.append(inputStr);
					}
					;
					ObjectMapper mapper = new ObjectMapper();
					try {
						// CONVERT JSON String to JAVA object:
						// List<Customer> list =
						// mapper.readValue(responseStrBuilder.toString(),
						// TypeFactory.defaultInstance().constructCollectionType(List.class,
						// Customer.class));

						// System.out.println(list);
						// List<Customer> cus = sortJsonObject(list);
						List<Customer> sortedCustomers = sortJsonString(responseStrBuilder.toString());
						// CONVERT JAVA object to JSON String file:
						String pth = setting.getSortedFile();
						System.out.println("SORTED FILE=" + pth);
						File sortedFile = new File(pth);
						if (sortedFile.exists()) {
							sortedFile.delete();
						}
						sortedFile.createNewFile();
						mapper.writeValue(sortedFile, sortedCustomers);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				WebServiceResponse res = new WebServiceResponse();
				res.setStatus("ok");
				return res;
			} catch (FileUploadException e) {
				WebServiceResponse response = new WebServiceResponse();
				response.setStatus("error");
				response.setErrMsg("Cannot parse multipart request.");
				return response;// .toString();
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
		WebServiceResponse response = new WebServiceResponse();
		response.setStatus("ok");
		return response;
	}

	@RequestMapping(value = "/dummy/upload", produces = "application/json")
	public WebServiceResponse dummyUpload(HttpSession session, @Context HttpServletRequest request// ,@RequestBody
	// NomineeAddRequest
	// info
	) throws IOException, ServletException {
		WebServiceResponse res = new WebServiceResponse();
		res.setStatus("OK");
		return res;
	}

	private <T> HttpEntity<T> prepareRequest(T request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(request, headers);
	}

	/***
	 * send json by object
	 * 
	 * @param customers
	 * @return
	 */
	private List<Customer> sortJsonObject(List<Customer> customers) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = setting.wsMethod;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		HttpEntity<List<Customer>> requestEntity = prepareRequest(customers);

		System.out.println("BEFORE: calling " + uri);
		Object response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity,
				List.class);
		System.out.println("AFTER: calling " + response);
		try {
			ResponseEntity<List<Customer>> responseEntity = (ResponseEntity<List<Customer>>) response;
			System.out.println("AFTER: calling responseEntity=" + responseEntity);
			return responseEntity.getBody();
			// responseEntity.getBody().setStatus("ok");
			// return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * send json by string
	 * 
	 * @param customersJson
	 * @return
	 */
	private List<Customer> sortJsonString(String customersJson) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = setting.getWsMethod();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		HttpEntity<String> requestEntity = prepareRequest(customersJson);

		System.out.println("BEFORE: calling " + uri);
		Object response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity,
				List.class);
		// System.out.println("AFTER: calling " + response);
		try {
			ResponseEntity<List<Customer>> responseEntity = (ResponseEntity<List<Customer>>) response;
			System.out.println("AFTER: calling responseEntity=");
			return responseEntity.getBody();
			// responseEntity.getBody().setStatus("ok");
			// return responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
