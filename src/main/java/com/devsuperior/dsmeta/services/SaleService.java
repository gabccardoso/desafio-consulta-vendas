package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<ReportMinDTO> findReport (String sellerName, String dataStringInicial, String dataStringFinal, Pageable pageable){
		LocalDate dataInicial = dataStringInicial.isEmpty() ? LocalDate.now().minusYears(1L) :
				formataData(dataStringInicial);
		LocalDate dataFinal = dataStringFinal.isEmpty() ? LocalDate.now() : formataData(dataStringFinal);
		return repository.generateReport(dataInicial, dataFinal, sellerName, pageable);
	}

	public List<SummaryMinDTO> findSummary (String dataStringInicial, String dataStringFinal){
		LocalDate dataInicial = dataStringInicial.isEmpty() ? LocalDate.now().minusYears(1L) :
				formataData(dataStringInicial);
		LocalDate dataFinal = dataStringFinal.isEmpty() ? LocalDate.now() : formataData(dataStringFinal);
		return repository.gerenateSummary(dataInicial, dataFinal);
	}

	private LocalDate formataData(String dataString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dataString, formatter);
	}
}
