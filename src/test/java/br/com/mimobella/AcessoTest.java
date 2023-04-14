package br.com.mimobella;

import br.com.mimobella.controllers.AcessoController;
import br.com.mimobella.models.Acesso;
import br.com.mimobella.repositories.AcessoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest(classes = LojaMimoBellaApplication.class)
class AcessoTest extends TestCase {

    @Autowired
    private AcessoController acessoController;

    @Autowired
    private AcessoRepository acessoRepository;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testRestApiCadastraAcesso() throws JsonProcessingException {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_COMPRADOR");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResultActions retornoApi = mockMvc.
                    perform(MockMvcRequestBuilders.post("/salvarAcesso")
                            .content(objectMapper.writeValueAsString(acesso))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON));

            System.out.println("Retorno Api: " + retornoApi.andReturn().getResponse().getContentAsString());

            /*Coverte retorno da api em um objeto*/
            Acesso objetoRetorno = objectMapper
                    .readValue(retornoApi.andReturn()
                            .getResponse()
                            .getContentAsString(), Acesso.class);

            assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test /*Teste end-point Delete Mockito*/
    public void testRestApiDeleteAcesso() throws JsonProcessingException {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_DELETE");
        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResultActions retornoApi = mockMvc.
                    perform(MockMvcRequestBuilders.post("/deleteAcesso")
                            .content(objectMapper.writeValueAsString(acesso))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON));

            System.out.println("Retorno Api: " + retornoApi.andReturn().getResponse().getContentAsString());
            System.out.println("Status: " + retornoApi.andReturn().getResponse().getStatus());

            assertEquals("Acesso Removido ", retornoApi.andReturn().getResponse().getContentAsString());
            assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

            /*Coverte retorno da api em um objeto*/
//            Acesso objetoRetorno = objectMapper
//                    .readValue(retornoApi.andReturn()
//                            .getResponse()
//                            .getContentAsString(), Acesso.class);
//
//            assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test /*Teste end-point Delete por id Mockito*/
    public void testRestApiDeletePorId() throws JsonProcessingException {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_DELETE_POR_ID");
        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResultActions retornoApi = mockMvc.
                    perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
                            .content(objectMapper.writeValueAsString(acesso))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON));

            System.out.println("Retorno Api: " + retornoApi.andReturn().getResponse().getContentAsString());
            System.out.println("Status: " + retornoApi.andReturn().getResponse().getStatus());

            assertEquals("Acesso Removido ", retornoApi.andReturn().getResponse().getContentAsString());
            assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test /*Teste end-point Listar por id Mockito*/
    public void testRestApiListarPorId() throws JsonProcessingException {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_OBTER_POR_ID");
        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResultActions retornoApi = mockMvc.
                    perform(MockMvcRequestBuilders.get("/obterAcessoPorId/" + acesso.getId())
                            .content(objectMapper.writeValueAsString(acesso))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON));

//            System.out.println("Retorno Api: " + retornoApi.andReturn().getResponse().getContentAsString());
//            System.out.println("Status: " + retornoApi.andReturn().getResponse().getStatus());

//            assertEquals("Acesso Removido", retornoApi.andReturn().getResponse().getContentAsString());
            assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

//           Coverte retorno da api em um objeto
            Acesso objetoRetorno = objectMapper
                    .readValue(retornoApi.andReturn()
                            .getResponse()
                            .getContentAsString(), Acesso.class);
//
            assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test /*Teste end-point Listar por Desc Mockito*/
    public void testRestApiListarPorDesc() throws JsonProcessingException {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Acesso acesso = new Acesso();
        acesso.setDescricao("ROLE_TESTE_DESC");
        acesso = acessoRepository.save(acesso);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ResultActions retornoApi = mockMvc.
                    perform(MockMvcRequestBuilders.get("/obterAcessoPorDesc/TESTE_DESC")
                            .content(objectMapper.writeValueAsString(acesso))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON));

            assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

            List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse()
                    .getContentAsString(), new TypeReference<List<Acesso>>(){});

            assertEquals(1, retornoApiList.size());
            assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());


            acessoRepository.deleteById(acesso.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
