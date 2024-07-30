package com.turkcell.customer_service.adapters.mernis;

import com.turkcell.customer_service.out_services.MernisClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MernisAdapter implements CheckNationalityService {
    private final MernisClient mernisClient;

    @Override
    public boolean validate(CheckNationalityDTO checkNationalityDTO) {
        return mernisClient.TCKimlikNoDogrula(checkNationalityDTO.getCitizenNumber(),
                checkNationalityDTO.getName().toUpperCase(),
                checkNationalityDTO.getSurname().toUpperCase(),
                checkNationalityDTO.getBirthDate().getYear());
    }
}
