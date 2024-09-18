package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<ReportMinDTO>> getReport(@RequestParam(defaultValue = "") String minDate, @RequestParam(defaultValue = "")
		String maxDate, @RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") int page,
														@RequestParam(defaultValue = "10") int size ) {
		Pageable pageable = PageRequest.of(page, size);
		return ResponseEntity.ok(service.findReport(name, minDate, maxDate, pageable));
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SummaryMinDTO>> getSummary(@RequestParam(defaultValue = "") String minDate, @RequestParam(defaultValue = "")
		String maxDate) {
		return ResponseEntity.ok(service.findSummary(minDate, maxDate));
	}
}
