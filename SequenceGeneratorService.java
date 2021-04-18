package in.bushansirgur.restapi.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import in.bushansirgur.restapi.model.BankModel;
import in.bushansirgur.restapi.model.DatabaseSequence;
import in.bushansirgur.restapi.model.ValidationModel;

@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongo;

	private Map<String, List<ValidationModel>> patternMatchingTable = new HashMap<String, List<ValidationModel>>();

	public long generateSequence(String seqName) {
		DatabaseSequence counter = mongo.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
				options().returnNew(true).upsert(true), DatabaseSequence.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}

	public Map<String, List<ValidationModel>> processRegexList(List<BankModel> seqNames) {
		patternMatchingTable = seqNames.stream().collect(Collectors.groupingBy(BankModel::getCountry
				, Collectors.mapping(d -> createValidationModel(d), Collectors.toList())));
		createMatchValueAccountAndBic("a","ABDDFDF","ASDFASDA");
		return patternMatchingTable;

	}

	private void createMatchValueAccountAndBic(String string, String string2, String string3) {
		List<ValidationModel> validationModel =  patternMatchingTable.get(string);
		for (ValidationModel validationModel2 : validationModel) {
			if(validationModel2.getBicCode().matcher(string2).matches() && validationModel2.getBicCode().matcher(string2).matches())
				System.out.println(validationModel2.getDrCode());
			
		}
		
		
	}

	private ValidationModel createValidationModel(BankModel bankModel) {
		ValidationModel validationModel = new ValidationModel();
		validationModel.setAccountValidationRegex(Pattern.compile(bankModel.getAccountValidationRegex(),Pattern.CASE_INSENSITIVE));
		validationModel.setBicCode(Pattern.compile(bankModel.getBicCode(),Pattern.CASE_INSENSITIVE));
		validationModel.setBankName(bankModel.getBankName());
		validationModel.setDrCode(bankModel.getDrCode());

		return validationModel;

	}

}
