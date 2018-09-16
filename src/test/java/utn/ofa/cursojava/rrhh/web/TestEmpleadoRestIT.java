package utn.ofa.cursojava.rrhh.web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author martin
 */
public class TestEmpleadoRestIT {

    public TestEmpleadoRestIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetEmpleado() throws IOException {
        String MY_URL = "http://localhost:8080/rrhh-web/api/empleado";
        HttpGet httpget = new HttpGet(MY_URL);
        CloseableHttpClient empleado = HttpClients.createDefault();
        CloseableHttpResponse response1 = empleado.execute(httpget);
        HttpEntity entity1 = response1.getEntity();
        String resultado = entidadToString(entity1.getContent());
        assertEquals("LISTO", resultado.toUpperCase());
        EntityUtils.consume(entity1);
        response1.close();
    }

    @Ignore
    public void testPostCliente() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode clienteJson = mapper.createObjectNode();
        clienteJson.put("id", 9);
        clienteJson.put("nombre", "Martin Maglianesi");
        clienteJson.put("correo", "mmaglianesi@justiciasantafe.gov.ar");
        clienteJson.put("cuit", "20-22070433-6");
        StringEntity postingString = new StringEntity(clienteJson.toString());
        HttpPost httpPost = new HttpPost("http://localhost:8080/rrhh-web/api/cliente");
        httpPost.setEntity(postingString);
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpClient cliente = HttpClients.createDefault();
        CloseableHttpResponse response1 = cliente.execute(httpPost);
        HttpEntity entity1 = response1.getEntity();
        String resultado = entidadToString(entity1.getContent());
        System.out.println(resultado);
        assertEquals("POST MARTIN MAGLIANESI", resultado.toUpperCase());
        EntityUtils.consume(entity1);
        response1.close();
    }

    @Ignore
    public void testCrearLuegoBuscarPorNombre() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode clienteJson = mapper.createObjectNode();
        clienteJson.put("nombre", "ClienteLaboratorio13");
        clienteJson.put("correo", "cl013@mail.com");
        clienteJson.put("cuit", "203040");
        StringEntity postingString = new StringEntity(clienteJson.toString());
        HttpPost httpPost = new HttpPost("http://localhost:8080/rrhh-web/api/cliente/");
        httpPost.setEntity(postingString);
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpClient cliente = HttpClients.createDefault();
        CloseableHttpResponse response1 = cliente.execute(httpPost);
        HttpEntity entity1 = response1.getEntity();
        assertEquals(200, response1.getStatusLine().getStatusCode());
        EntityUtils.consume(entity1);
        response1.close();

        HttpGet httpget = new HttpGet("http://localhost:8080/rrhh-web/api/cliente/?nombre=ClienteLaboratorio13");
        CloseableHttpResponse respBuscarPorNombre = cliente.execute(httpget);
        HttpEntity clientePorNombre = respBuscarPorNombre.getEntity();
        String resultado = entidadToString(clientePorNombre.getContent());
        // transformar el JSON en un objeto mapper
        JsonNode clienteJsonRespuesta = mapper.readTree(resultado);
        assertEquals(clienteJsonRespuesta.get(0).get("nombre").asText().toLowerCase(),
                "clientelaboratorio13");
        EntityUtils.consume(respBuscarPorNombre.getEntity());
        response1.close();
    }

    private String entidadToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
