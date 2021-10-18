package br.com.test.integration.suite.evidence;

import br.com.test.integration.suite.models.Body;

public class EmailLogic {

    public static void addSuccess(String idTest,String time, String operadora) {
        Body body;
        body = new Body();
        body = body.findByIdException(idTest, operadora);
        if (body !=null) {
            body.addCountSuccess(idTest, operadora);
            if (body.getCountSuccess() == 1) {
                body.resetCountException(idTest, operadora);
                if (body.getCountException() > 2) {
                    //TODO: Enviar email de sucesso Necessário Padronizar a mensagem
                    System.out.println(EmailCustom.send(body, time, operadora, "",true));
                }
            }
        }
    }

    public static void addError(String idTest,String time, String operadora, String mensagem)  {
        Body body;
        body = new Body();
        body = body.findByIdException(idTest, operadora);
        if (body !=null) {
            body.addCountException(idTest, operadora);
            body.resetCountSuccess(idTest, operadora);
            if (body.getCountException() == 2 && body.getCountSuccess() == 1) {
                body.resetCountException(idTest, operadora);
                body.addCountException(idTest, operadora);
            }
            if (body.getCountException() == 2 && body.getCountSuccess() == 0) {
                System.out.println(EmailCustom.send(body, time, operadora, mensagem,false));
            }
        }
    }

    public static void testSend(String idTest,String time, String operadora, String mensagem)  {
        Body body;
        body = new Body();
        body = body.findByIdException(idTest, operadora);
        System.out.println(EmailCustom.send(body, time, operadora, mensagem,false));
    }

}
