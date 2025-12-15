package br.com.dcasimulator.repository;


import br.com.dcasimulator.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    Asset findBySymbol(String symbol);
}
