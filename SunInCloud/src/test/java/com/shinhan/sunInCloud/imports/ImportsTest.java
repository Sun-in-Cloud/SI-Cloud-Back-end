package com.shinhan.sunInCloud.imports;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.shinhan.sunInCloud.entity.Imports;
import com.shinhan.sunInCloud.service.SellerImportService;

@SpringBootTest
public class ImportsTest {
	
	@Autowired
	SellerImportService importService;
	
	
	@Test
	void seePreList() {
		List<Imports> imports = new ArrayList<>();
		Timestamp timeStamp = new Timestamp(new Date().getTime());
		Imports im = Imports.builder().requestDate(timeStamp)
				.importDate(timeStamp).build();
		
	}
}
