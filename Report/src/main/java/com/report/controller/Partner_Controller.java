package com.report.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.report.miscallaneous.Condition;
import com.report.miscallaneous.DataInfo;
import com.report.miscallaneous.Mail;
import com.report.miscallaneous.Message;
import com.report.miscallaneous.Path;
import com.report.miscallaneous.QuantativeData;
import com.report.model.Admin;
import com.report.model.CandidateInfo;
import com.report.model.Credentials;
import com.report.model.Partner;
import com.report.model.Qualitative;
import com.report.model.Quantitative;
import com.report.service.Admin_Service;
import com.report.service.Service_Login;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
@RequestMapping("/report")
public class Partner_Controller {
	
	@Autowired private Service_Login service_login; 
	@Autowired private Admin_Service admin_service;
	@Autowired private PasswordEncoder bcryptEncoder;
	@Autowired private ServletContext context;
	
	
	@PostMapping(value="/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody String[] credentials) {
		String password=Path.randomPassword();
		if(credentials.length<1) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Credentials");
		}
		Credentials cred=service_login.getCredentialsByUsername(credentials[0]);
		if(cred==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Username");
		}
		cred.setPassword(bcryptEncoder.encode(password));
		boolean st=service_login.updateCredetials(cred);
		if(st) {
			Mail.sendResetPasswordmail(cred.getUsername(),password);
			   return ResponseEntity.status(HttpStatus.OK).body(new Message("Kindly Check Your Mail"));
		 }
			   return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something Went Wrong");

	}
	
	@PostMapping(value="/update-password")
	public ResponseEntity<?> updatePassword(@RequestBody String[] cred) {
		Optional.ofNullable(cred).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		Credentials credentials=service_login.getCredentialsByUsername(cred[0]);
		if(bcryptEncoder.matches(cred[1],credentials.getPassword())) {
			credentials.setPassword(bcryptEncoder.encode(cred[2]));
			boolean st=service_login.updateCredetials(credentials);
			if(st)
				return ResponseEntity.status(HttpStatus.OK).body(new Message("Password Updated"));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something Went Wrong");
		}
		else 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong Current Password");
	}

	
	@PostMapping(value="/save-partner")
	public ResponseEntity<?> savePartner(@RequestBody Partner partner) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(partner).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(service_login.savePartnerByCredentials(partner));
	}
	
	@PostMapping(value="/update-partner")
	public ResponseEntity<?> saveClient(@RequestBody Partner partner) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(partner).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.updatePartner(partner));
	}
	
	@GetMapping(value="/partner/{partner_id}")
	public ResponseEntity<?> partnerById(@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(partner_id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getPartnerById(partner_id));
	}
	
	@GetMapping(value="/admin/{username}")
	public ResponseEntity<?> adminByUsername(@PathVariable("username") String username) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(username).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getAdminByEmail(username));
	}
	
	@PostMapping(value="/partner-list")
	public ResponseEntity<?> partnerById(@RequestBody Condition condition) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(condition).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(admin_service.getPartnerList(condition),
									admin_service.getPartnerListCount(condition)));
	}
	
	@PostMapping(value="/update-admin")
	public ResponseEntity<?> updateAdmin(@RequestBody Admin admin) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(admin).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.updateAdmin(admin));
	}
	
	@GetMapping(value="/get-profile-pic/{picture}", produces = MediaType.IMAGE_JPEG_VALUE)
	public  byte[] getImageMaster(@PathVariable("picture") String picture) throws IOException{
		InputStream in = context.getResourceAsStream("/profile_picture/"+picture);
		return IOUtils.toByteArray(in);
	}
	
	@PostMapping(value="/save-profile-pic")
	public boolean saveClientProfilePic(@RequestParam("pic") MultipartFile pic,
			@RequestParam("file_name") String file_name)throws Exception{
		return Path.savePic(pic, file_name);
	}
	
	@GetMapping(value="/companies")
	public ResponseEntity<?> companyList() throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getCompanies());
	}
	
	@GetMapping(value="/get-user-info/{username}")
	public ResponseEntity<?> adminInfo(@PathVariable("username") String username) throws DuplicateKeyException,NoSuchElementException{
		Credentials credentials=service_login.getCredentialsByUsername(username);
		credentials.setPassword(null);
		return ResponseEntity.status(HttpStatus.OK).body(credentials);
	}
	
	@GetMapping(value="/partner-list-only/{partner_id}")
	public ResponseEntity<?> onlyPartnerList(@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getPartnerListOnly(partner_id));
	}
	
	@PostMapping(value="/save-qualitative")
	public ResponseEntity<?> savequalitative(@RequestBody Qualitative qualitative) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(qualitative).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.saveQualitative(qualitative));
	}
	
	@PostMapping(value="/save-quantitative")
	public ResponseEntity<?> savequantative(@RequestBody Quantitative quantitative) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(quantitative).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.saveQuantative(quantitative));
	}
	
	@PostMapping(value="/update-qualitative")
	public ResponseEntity<?> saveQualitative(@RequestBody Qualitative qualitative) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(qualitative).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.updateQualitative(qualitative));
	}
	
	@PostMapping(value="/update-quantitative")
	public ResponseEntity<?> savequantitative(@RequestBody Quantitative quantitative) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(quantitative).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.updateQuantative(quantitative));
	}
	
	@GetMapping(value="/qualitative/{qualitative_id}")
	public ResponseEntity<?> qualitativeById(@PathVariable("qualitative_id") long qualitative_id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(qualitative_id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getQualitativeById(qualitative_id));
	}
	
	@GetMapping(value="/quantitative/{id}")
	public ResponseEntity<?> quantitativeById(@PathVariable("id") long id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getQuantativeById(id));
	}
	
	@PostMapping(value="/qualitative-list/{partner_id}")
	public ResponseEntity<?> qualitativeById(@RequestBody Condition condition,
			@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(partner_id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(admin_service.getQualitativeInfo(partner_id, condition),
									admin_service.getQualitativeCount(partner_id, condition)));
	}
	
	@PostMapping(value="/quantitative-list/{partner_id}")
	public ResponseEntity<?> quantitativeById(@RequestBody Condition condition,
			@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(partner_id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(admin_service.getQuantativeList(condition, partner_id),
									admin_service.getQuantativeCount(condition, partner_id)));
	}
	
	@GetMapping(value="/stages")
	public ResponseEntity<?> getStages() throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getStages());
	}
	
	@GetMapping(value="/stage-distribution/{id}")
	public ResponseEntity<?> stageDistribution(@PathVariable("id") long id) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getStageDistribution(id));
	}
	
	@PostMapping(value="/save-candidate")
	public ResponseEntity<?> saveCandidate(@RequestBody CandidateInfo candidateInfo) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(candidateInfo).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.saveCandidate(candidateInfo));
	}

	@PostMapping(value="/candidate-list/{id}")
	public ResponseEntity<?> candidateList(@RequestBody Condition condition,
			@PathVariable("id") long id) throws DuplicateKeyException,NoSuchElementException{
		Optional.ofNullable(id).orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(admin_service.getCandidateList(condition, id),
									admin_service.getCandidateCount(condition, id)));
	}
	
	@GetMapping(value="/dashboard-info")
	public ResponseEntity<?> dashBoardInfo() throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getDashBoardInfo());
	}
	
	@GetMapping(value="/get-related-partner/{partner_id}")
	public ResponseEntity<?> getRelatedPartner(@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(admin_service.getRelatedPartnerList(partner_id));
	}
	
	@PostMapping(value="/graph-info")
	public ResponseEntity<?> grapghInfo(@RequestBody QuantativeData quantativeData) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(Path.getChartData(admin_service.getGraphData(quantativeData)));
	}
	
	@PostMapping(value="/qualitative-report")
	public ResponseEntity<?> qualitativeReport(@RequestBody QuantativeData quantativeData) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(
				admin_service.getQualitativeInfoReport(quantativeData, quantativeData.getCondition()), 
				admin_service.getQualitativeCountReport(quantativeData, quantativeData.getCondition())));
	}
	
	@PostMapping(value="/quantitative-report")
	public ResponseEntity<?> quantitativeReport(@RequestBody QuantativeData quantativeData) throws DuplicateKeyException,NoSuchElementException{
		return ResponseEntity.status(HttpStatus.OK).body(new DataInfo(
				admin_service.getQuantativeListReport(quantativeData, quantativeData.getCondition()), 
				admin_service.getQuantativeCountReport(quantativeData, quantativeData.getCondition())));
	}
	
	@GetMapping(value="/get-related-partner-for-dashboard/{partner_id}")
	public ResponseEntity<?> getRelatedPartnerForDashboard(@PathVariable("partner_id") long partner_id) throws DuplicateKeyException,NoSuchElementException{
		List<Object[]> list=admin_service.getRelatedPartnerList(partner_id);
		Partner partner=admin_service.getPartnerById(partner_id);
		Object[] obj=new Object[2];
		obj[0]=partner.getPartner_id();
		obj[1]=partner.getPartner_name();
		list.add(obj);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	

}
