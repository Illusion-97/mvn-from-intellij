package fr.dawan.bank.mappers;

import fr.dawan.bank.dto.AccountDto;
import fr.dawan.bank.entities.Account;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Mapping(target = "maskedCode", source = "code") // Précise une relation
    @Mapping(target = "historyCount", source = "history", qualifiedByName = "count")
    AccountDto toDto(Account entity);
    @InheritInverseConfiguration // Inverse les sources et target des mappings de la méthode inverse
    @Mapping(target = "history", source = "historyCount", ignore = true) // Demande explicite d'ignorer une relation
    Account toEntity(AccountDto dto);

    @Named("count")
    static int getHistoryCount(List<String> history) {
        return history.size();
    }
}
