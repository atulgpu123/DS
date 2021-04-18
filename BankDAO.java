package in.bushansirgur.restapi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.bushansirgur.restapi.model.BankModel;

@Repository
public interface BankDAO extends MongoRepository<BankModel, Long> {

}
