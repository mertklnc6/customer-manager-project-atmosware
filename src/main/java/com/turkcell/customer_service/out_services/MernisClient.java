package com.turkcell.customer_service.out_services;

import com.turkcell.customer_service.common.exceptions.types.BusinessException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class MernisClient {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MernisClient.class);

    public boolean TCKimlikNoDogrula(String identityNumber, String firstName, String lastName, int birthYear) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml; charset=utf-8");
        String requestBody = createRequestBody(identityNumber, firstName, lastName, birthYear);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx", request, String.class);

        String response = responseEntity.getBody();
        if (response == null) {
            logger.error("Mernis servisine bağlanılamadı.");
            throw new BusinessException("Mernis servisine bağlanılamadı.");
        }
        return response.contains("<TCKimlikNoDogrulaResult>true</TCKimlikNoDogrulaResult>");
    }

    public String createRequestBody(String identityNumber, String firstName, String lastName, int birthYear) {
        return String.format(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\"><TCKimlikNo>%s</TCKimlikNo><Ad>%s</Ad><Soyad>%s</Soyad><DogumYili>%s</DogumYili></TCKimlikNoDogrula></soap:Body></soap:Envelope>",
                identityNumber, firstName, lastName, birthYear);
    }
}
