package fr.dawan.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Equivalent pour Getter, Setter, ToString, HashCode
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private double solde;
    private String maskedCode;
    private int historyCount;

    public void setMaskedCode(String maskedCode) {
        this.maskedCode = maskedCode == null ? "Code non généré" : "******";
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "solde=" + solde +
                ", maskedCode='" + maskedCode + '\'' +
                ", historyCount=" + historyCount +
                '}';
    }
}
