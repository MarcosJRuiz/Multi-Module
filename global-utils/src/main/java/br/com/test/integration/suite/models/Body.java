package br.com.test.integration.suite.models;

import com.google.gson.*;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Body{

	private String IdException;
    private String NmAlerta;
    private String NmImpacto;
    private int countException;
	private int countSuccess;

	public String getIdException() {
		return IdException;
	}
	public void setIdException(String idException) {
		IdException = idException;
	}
	public String getNmAlerta() {
		return NmAlerta;
	}

	public String getNmImpacto() {
		return NmImpacto;
	}

	public int getCountException() {
		return countException;
	}
	public void setCountException(int countException) {
		this.countException = countException;
	}

	public int getCountSuccess() {
		return countSuccess;
	}
	public void setCountSuccess(int countSuccess) {
		this.countSuccess = countSuccess;
	}

	/**
	 * This method write to the json file
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @param addCountException *true: add countException key. - *false: reset countException key.
	 * @param idTest reference idExption
	 *
	 */
	@SuppressWarnings("deprecation")
	private static void editJson(String operadora,String idTest, String addCountSuccess, String addCountException) {
		//String jsonFile = "../src/main/resources/body.json";
		String jsonFile = "../../../workspace_data/cenarios_itau_unificado/body/"+idTest.substring(0,3)+operadora+".json";
		Body body;
		Gson gson = new Gson();
		JsonBody data = new JsonBody();
		JsonObject jsonObject = new JsonParser().parse(Objects.requireNonNull(readJsonFile(idTest, operadora))).getAsJsonObject();
		JsonArray jsonArrays = jsonObject.getAsJsonArray("body").getAsJsonArray();

		for (final JsonElement jElement : jsonArrays) {
			body = gson.fromJson(jElement, Body.class);
			String idException = jElement.getAsJsonObject().get("IdException").getAsString();

			if (Objects.equals(addCountException, "true") && idTest.equalsIgnoreCase(idException)) {
				body.setCountException(body.getCountException() + 1);
			}else if (Objects.equals(addCountException, "false") && idTest.equalsIgnoreCase(idException)) {
				body.setCountException(0);
			}

			if (Objects.equals(addCountSuccess, "true") && idTest.equalsIgnoreCase(idException)) {
				body.setCountSuccess(body.getCountSuccess() + 1);
			}else if (Objects.equals(addCountSuccess, "false") && idTest.equalsIgnoreCase(idException)) {
				body.setCountSuccess(0);
			}
			data.body.add(body);
		}

		try (FileWriter writer = new FileWriter(jsonFile)) {
			gson.toJson(data, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method read json file
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 */
	private static String readJsonFile(String idException, String operadora) {
		try {
			//String jsonFile = "../src/main/resources/body.json";
			String jsonFile = "../../../workspace_data/cenarios_itau_unificado/body/"+idException.substring(0,3)+operadora+".json";
			return new String(Files.readAllBytes(Paths.get(jsonFile)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Body findByIdException(String idException, String operadora) {
		Body body = findAll(idException,operadora).stream().filter(f->idException.equalsIgnoreCase(f.IdException)).findFirst().orElse(null);
		if (body == null) {
			Logger logger = Logger.getLogger(Body.class);
			logger.error("Parameter;Log;Erro;O Codigo IdException [ ".concat(idException).concat(" ] não foi declada no arquivo body.json."));
		}
		return body;
	}

	private static List<Body> findAll(String idException,String operadora){
		List<Body> listBody = new ArrayList<>();
		Gson gson = new Gson();
		JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(readJsonFile(idException,operadora))).getAsJsonObject();
		JsonArray jsonArrays = jsonObject.getAsJsonArray("body").getAsJsonArray();

		for (final JsonElement jElement : jsonArrays) {
			listBody.add(gson.fromJson(jElement, Body.class));
		}
		return listBody;
	}

	/**
	 * This method resets the countException key
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @param idTest reference idExption
	 */
	public void resetCountException(String idTest, String operadora) {
		editJson( operadora, idTest,null,"false");
	}

	/**
	 * This method adds 1 more error to countException
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @param idTest reference idExption
	 */
	public void addCountException(String idTest, String operadora) {
		editJson( operadora, idTest,null,"true");
	}


	/**
	 * This method resets the countSuccess key
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @param idTest reference idExption
	 */
	public void resetCountSuccess(String idTest, String operadora) {
		editJson( operadora, idTest,"false",null);
	}

	/**
	 * This method adds 1 more error to countSuccess
	 *
	 * @author <a href="mailto:marcosruiz.jt@gmail.com">Marcos Ruiz</a>
	 *
	 * @param idTest reference idExption
	 */
	public void addCountSuccess(String idTest, String operadora) {
		editJson( operadora, idTest,"true",null);
	}
}
