/**
 *
 * @author jiaojiao
 */
package org.cbioportal.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.cbioportal.model.CNASegmentData;
import org.cbioportal.persistence.CNASegmentRepository;
import org.cbioportal.service.CNASegmentService;
import org.mskcc.cbio.portal.dao.DaoGeneOptimized;
import org.mskcc.cbio.portal.model.CanonicalGene;

@Service
public class CNASegmentServiceImpl implements CNASegmentService {

    @Autowired
    private CNASegmentRepository cnaSegmentRepository;

    public List<CNASegmentData> getCNASegmentData(String cancerStudyId, List<String> hugoGeneSymbols, List<String> sampleIds) {
        List<String> chromosomes = new ArrayList<String>();
        DaoGeneOptimized daoGeneOptimized = DaoGeneOptimized.getInstance();
        for(String hugoGeneSymbol : hugoGeneSymbols){
            CanonicalGene gene = daoGeneOptimized.getGene(hugoGeneSymbol);
            chromosomes.add(gene.getChromosome());
        }
        return cnaSegmentRepository.getCNASegmentData(cancerStudyId, chromosomes, sampleIds);
    }

}