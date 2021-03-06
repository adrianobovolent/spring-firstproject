package com.springfirstproject.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springfirstproject.domain.Request;
import com.springfirstproject.domain.RequestStage;
import com.springfirstproject.domain.User;
import com.springfirstproject.model.PageModel;
import com.springfirstproject.model.PageRequestModel;
import com.springfirstproject.service.RequestService;
import com.springfirstproject.service.RequestStageService;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {
	@Autowired private RequestService requestService;
	@Autowired private RequestStageService stageService;

	@PostMapping
	public ResponseEntity<Request> save(@RequestBody Request request) {
		Request createdRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable(name = "id") Long id, @RequestBody Request request) {
	  request.setId(id);
      Request updatedRequest = requestService.update(request);
      return ResponseEntity.ok(updatedRequest);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable(name = "id") Long id) {
		Request request = requestService.getById(id);
		return ResponseEntity.ok(request);
		
	}
	
	@GetMapping
	public ResponseEntity<PageModel<Request>> listAll(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "size") int size) {
	
	PageRequestModel pr = new PageRequestModel(page, size);
	PageModel<Request> pm = requestService.listAllOnLazyMode(pr);
	
	return ResponseEntity.ok(pm);
	}
	
	@GetMapping("/{id}/requests-stages")
	public ResponseEntity<PageModel<RequestStage>> listAllStagesById(
			@PathVariable(name = "id") Long id,
			@RequestParam(value = "page") int page,
			@RequestParam(value = "size") int size) {
		
		PageRequestModel pr = new PageRequestModel(page, size);
		PageModel<RequestStage> pm = stageService.listAllByRequestIdOnLazyModel(id, pr);
		
		
		return ResponseEntity.ok(pm);
	}
	
	
		
}
