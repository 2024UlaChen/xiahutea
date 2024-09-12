package idv.tia201.g2.core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import idv.tia201.g2.core.vo.AddressDetailVo;
import idv.tia201.g2.core.vo.AddressVo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class AddrService {
    private static final RestTemplate ADDR_CLIENT = new RestTemplate();
    private static final String ADDR_URL = "https://api.opencube.tw/twzipcode";
    private static final Gson GSON = new GsonBuilder().create();
    private static final String addressJson = "/static/util/AddrData.json";

    public List<AddressDetailVo> getAddrFromRestClient() {
        ResponseEntity<AddressVo> responseEntity = ADDR_CLIENT.getForEntity(ADDR_URL, AddressVo.class);
        AddressVo addressVo = responseEntity.getBody();

        return Objects.nonNull(addressVo) ? addressVo.getData() : null;
    }

    public List<AddressDetailVo> getAddrFromJson() throws IOException {
        File file = new ClassPathResource(addressJson).getFile();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            AddressVo addressVo = GSON.fromJson(br, AddressVo.class);
            return addressVo.getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}
