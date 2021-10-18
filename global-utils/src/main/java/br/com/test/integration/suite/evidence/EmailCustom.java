package br.com.test.integration.suite.evidence;

import br.com.test.integration.suite.models.Body;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.nio.charset.StandardCharsets;

public class EmailCustom {

	public static String send(Body body,String time, String operadora, String mensagem,boolean status) {
		String idException = body.getIdException();
		String resp = "Cenário: " + idException + " - Valor do CountSuccess: " + body.getCountSuccess() + " - Valor do CountException: " + body.getCountException();
		System.out.println(resp);


		String subject = "TITULO";
		Email to = new Email("E-MAIL");
		String msg = mensagem;

		if(status){
			subject = "TITULO";
			to = new Email("E-MAIL");
			msg = mensagem + time + operadora;
		}

		Email from = new Email("E-MAIL");
		System.out.println(mensagem);

		Content content = new Content("text", msg);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid("xxxxxxx");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println("Response status code: " + response.getStatusCode());

			if (response.getStatusCode() == 202) {
				resp = "E-mail enviado com SUCESSO para o Cenário: " + idException + " - Valor do CountSuccess: " + body.getCountSuccess() + " - Valor do CountException: " + body.getCountException() ;
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return resp;
	}

}