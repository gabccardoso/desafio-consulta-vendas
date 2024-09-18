package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.ReportMinDTO(s.id, s.amount, s.date, s.seller.name) FROM Sale s " +
            "WHERE s.date BETWEEN :dataInicio AND :dataFim AND LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<ReportMinDTO> generateReport(@Param("dataInicio") LocalDate dataInicio,
                                      @Param("dataFim") LocalDate dataFim,
                                      @Param("nome") String nome,
                                      Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryMinDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY s.seller.name")
    List<SummaryMinDTO> gerenateSummary(@Param("dataInicio") LocalDate dataInicio,
                                        @Param("dataFim") LocalDate dataFim);
}
