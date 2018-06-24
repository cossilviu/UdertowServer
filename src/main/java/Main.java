import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

import java.io.IOException;

import static io.undertow.util.Methods.POST;


public class Main {
    public static void main(String[] args) {
        HttpHandler signupPage = new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                if(exchange.getRequestMethod().equals(POST)){
                    FormDataParser parser = FormParserFactory.builder().build()
                            .createParser(exchange);
                    FormData data = parser.parseBlocking();

                    String firstName = data.getFirst("firstName").getValue();
                    String lastName = data.getFirst("lastName").getValue();

                    exchange.getResponseSender().send(firstName + " " + " " + lastName + " processed");
                } else {
                    exchange.getResponseSender().send(
                            "<!DOCTYPE html>\n" +
                                    "\n" +
                                    "<html lang=\"en\">\n" +
                                    "    <head>\n" +
                                    "        <meta charset=\"utf-8\"/>\n" +
                                    "        <title>Signup</title>\n" +
                                    "    </head>\n" +
                                    "    <body>\n" +
                                    "        <form method=\"post\" action=\"http://localhost:8080/index.html\">\n" +
                                    "            <label for=\"firstName\">First Name</label>\n" +
                                    "            <input type=\"text\" name=\"firstName\" id=\"firstName\"/>\n" +
                                    "            \n" +
                                    "            <label for=\"lastName\">Last Name</label>\n" +
                                    "            <input type=\"text\" name=\"lastName\" id=\"lastName\"/>\n" +
                                    "            \n" +
                                    "            <input type=\"submit\"/>\n" +
                                    "        </form>\n" +
                                    "        \n" +
                                    "        \n" +
                                    "    </body>\n" +
                                    "</html>");
                }

            }
        };

        Undertow server = Undertow.builder()
                .addHttpListener(8080,"0.0.0.0")
                .setHandler(Handlers.path().addExactPath("/signup.html",signupPage))
                .build();

        server.start();
    }
}
