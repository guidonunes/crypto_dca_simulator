package br.com.dcasimulator.repository;

import br.com.dcasimulator.model.PriceRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<PriceRecord, Long> {

    @Query(value = "SELECT * FROM prices WHERE asset_symbol = :symbol", nativeQuery = true)
    List<PriceRecord> findByAssetSymbol(String symbol);
}
