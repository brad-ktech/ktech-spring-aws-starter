package com.ktech.starter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ktech.starter.annotations.ApiFields;
import com.ktech.starter.annotations.ApiPath;
import com.ktech.starter.clio.messages.Result;
import com.ktech.starter.clio.models.Matter;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

//@SpringBootTest
class KtechSpringAwsStarterApplicationTests {

	//@Test
	void contextLoads() {
	}


	public static void main(String[] args) throws IOException {

		File f = new File("C:\\Brad\\KTech\\aperture\\matter.txt");
		String json = FileUtils.readFileToString(f, "UTF-8");
		Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		//Result<Matter> result = gson.fromJson(json, new TypeToken<Result<Matter>>() {}.getType());
		//Matter m = result.getData();

		Result<Matter> result = gson.fromJson(json.toString(), new TypeToken<Result<Matter>>() {}.getType());
		Matter m = result.getData();
System.out.println(m.getId());
System.out.println(m.getUpdatedAt());
	}

}
