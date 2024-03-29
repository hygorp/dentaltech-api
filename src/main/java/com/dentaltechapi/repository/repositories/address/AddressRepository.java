package com.dentaltechapi.repository.repositories.address;

import com.dentaltechapi.model.entities.address.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {

}
